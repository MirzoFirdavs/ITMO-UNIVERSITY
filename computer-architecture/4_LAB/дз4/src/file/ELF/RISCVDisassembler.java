package file.ELF;

import net.fornwall.jelf.*;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class RISCVDisassembler {
    final ElfFile file;

    public RISCVDisassembler(ElfFile file) {
        if (file.objectSize != ElfFile.CLASS_32) {
            throw new InputMismatchException("This ELF-file is not 32 bit.");
        }
        if (file.arch != 0xF3) {
            throw new InputMismatchException("This ELF-file is not for RISC-V.");
        }
        this.file = file;
    }

    public void Launch(OutputStreamWriter output) {
        PrintWriter writer = new PrintWriter(output);
        doDisassemble(writer);
        writer.flush();
    }

    String getRegisterString(int register) {
        if (register == 0) {
            return "zero";
        } else if (register == 1) {
            return "ra";
        } else if (register == 2) {
            return "sp";
        } else if (register == 3) {
            return "gp";
        } else if (register == 4) {
            return "tp";
        } else if (5 <= register && register <= 7) {
            return "t" + (register - 5);
        } else if (register == 8) {
            return "s0";
        } else if (register == 9) {
            return "s1";
        } else if (10 <= register && register <= 17) {
            return "a" + (register - 10);
        } else if (18 <= register && register <= 27) {
            return "s" + (register - 18 + 2);
        } else if (28 <= register && register <= 31) {
            return "t" + (register - 28 + 3);
        } else {
            throw new AssertionError("RISC-V doesn't have this register");
        }
    }

    private String getSymbolForAddr(long cur) {
        ElfSymbol symb = file.getELFSymbol(cur);
        String locS = String.format("0x%08X", cur);
        if (symb != null && symb.st_value == cur && symb.section_type == ElfSymbol.STT_FUNC) {
            locS += " <" + symb.getName() + ">";
        }
        return locS;
    }

    private void doDisassemble(PrintWriter out) {
        ElfSection textSection = file.firstSectionByName(".text");
        if (textSection == null)
            throw new InputMismatchException("No .text found");
        file.getDynamicSymbolTableSection();
        file.getSymbolTableSection();
        int maxSymbolLen = 10;
        file.parser.seek(textSection.header.section_offset);
        for (int cur = 0; cur < textSection.header.size; cur += 4){
            long virtualAddress = cur + textSection.header.address;
            out.print(String.format("%08X:", virtualAddress));
            int instruction = file.parser.readInt();
            ElfSymbol symb = file.getELFSymbol(virtualAddress);
            if (symb != null && symb.st_value == virtualAddress && symb.section_type == ElfSymbol.STT_FUNC) {
                out.printf("<%" + maxSymbolLen + "s> ", symb.getName());
            } else {
                out.print(" ".repeat(maxSymbolLen + 3));
            }
            int opcode = instruction & ((1 << 7) - 1);
            int rd = instruction >> 7 & ((1 << 5) - 1);
            int funct3 = instruction >> 12 & ((1 << 3) - 1);
            int rs1 = instruction >> 15 & ((1 << 5) - 1);
            int rs2 = instruction >> 20 & ((1 << 5) - 1);
            int imm110 = instruction >> 20 & ((1 << 12) - 1);
            int funct7 = instruction >> 25;
            if (instruction == 0b1110011) {
                out.printf("%6s%n", "ecall");
            } else if (opcode == 0b0110111) {
                out.printf("%6s %s, %s%n", "lui", getRegisterString(rd), Integer.toUnsignedString((instruction >>> 12) << 12));
            } else if (opcode == 0b0010111) {
                out.printf("%6s %s, %s%n", "auipc", getRegisterString(rd), Integer.toUnsignedString((instruction >>> 12) << 12));
            } else if (opcode == 0b1101111) {
                int imm = instruction >> 12;
                int offset = (((imm >>> 9) & ((1 << 10) - 1)) << 1) |
                        (((imm >>> 8) & 1) << 11) |
                        ((imm & ((1 << 8) - 1)) << 12) |
                        (((imm >>> 19) & 1) << 20);
                if ((offset & (1 << 20)) != 0) {
                    offset = -offset & ((1 << 20) - 1);
                }
                out.printf("%6s %s, %d #%s%n", "jal", getRegisterString(rd), offset, getSymbolForAddr(virtualAddress + offset));
            } else if (opcode == 0b1100111 && funct3 == 0b000) {
                if ((imm110 & (1 << 11)) != 0) {
                    imm110 = -imm110 & ((1 << 11) - 1);
                }
                out.printf("%6s %s, %s, %d%n", "jalr", getRegisterString(rd), getRegisterString(rs1), imm110);
            } else if (opcode == 0b1100011) {
                int offset = (((instruction >>> 8) & ((1 << 4) - 1)) << 1) |
                        (((instruction >>> 25) & ((1 << 6) - 1)) << 5) |
                        (((instruction >>> 7) & 1) << 11) |
                        (((instruction >>> 31) & 1) << 12);
                if ((offset & (1 << 12)) != 0) {
                    offset = -offset & ((1 << 12) - 1);
                }
                String instr = new String[]{"beq", "bne", "??", "??", "blt", "bge", "bltu", "bgeu"}[funct3];
                out.printf("%6s %s, %s, %d #%s %n", instr, getRegisterString(rs1), getRegisterString(rs2), offset, getSymbolForAddr(virtualAddress + offset));
            } else if (opcode == 0b0000011) {
                String instr = new String[]{"lb", "lh", "lw", "??", "lbu", "lhu", "??", "??"}[funct3];
                out.printf("%6s %s, %s, %d%n", instr, getRegisterString(rd), getRegisterString(rs1), imm110);
            } else if (opcode == 0b0100011) {
                String instr = new String[]{"sb", "sh", "sw", "??", "??", "??", "??", "??"}[funct3];
                int imm = rd | ((imm110 >>> 5) << 5);
                out.printf("%6s %s, %d(%s)%n", instr, getRegisterString(rs2), imm, getRegisterString(rs1));
            } else if (opcode == 0b0010011) {
                if (funct3 == 0b001) {
                    out.printf("%6s %s, %s, %d%n", "slli", getRegisterString(rd), getRegisterString(rs1), imm110);
                } else if (funct3 == 0b101) {
                    if (funct7 == 0b0100000) {
                        out.printf("%6s %s, %s, %d%n", "srai", getRegisterString(rd), getRegisterString(rs1), imm110 & ((1 << 5) - 1));
                    } else {
                        out.printf("%6s %s, %s, %d%n", "srli", getRegisterString(rd), getRegisterString(rs1), imm110);
                    }
                } else {
                    String instr = new String[]{"addi", "??", "slti", "sltiu", "xori", "??", "ori", "andi"}[funct3];
                    out.printf("%6s %s, %s, %d%n", instr, getRegisterString(rd), getRegisterString(rs1), imm110);
                }
            } else if (opcode == 0b110011) {
                if (funct7 == 0b0100000) {
                    String instr = new String[]{"sub", "??", "??", "??", "??", "sra", "??", "??"}[funct3];
                    out.printf("%6s %s, %s, %s%n", instr, getRegisterString(rd), getRegisterString(rs2), getRegisterString(rs1));
                } else if (funct7 == 0) {
                    String instr = new String[]{"add", "sll", "slt", "sltu", "xor", "srl", "or", "and"}[funct3];
                    out.printf("%6s %s, %s, %s%n", instr, getRegisterString(rd), getRegisterString(rs2), getRegisterString(rs1));
                } else if (funct7 == 1) {
                    String instr = new String[]{"mul", "mulh", "mulhsu", "mulhu", "div", "divu", "rem", "remu"}[funct3];
                    out.printf("%6s %s, %s, %s%n", instr, getRegisterString(rd), getRegisterString(rs2), getRegisterString(rs1));
                }
            } else {
                out.printf("????%n");
            }
        }
    }
}