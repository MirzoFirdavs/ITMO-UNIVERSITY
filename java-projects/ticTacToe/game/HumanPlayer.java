	package ticTacToe;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }
     int getNext(Scanner line) {
        if (line.hasNextInt()) {
            return line.nextInt();
        } else {
            return -1;
        }
    }

    private Move scanMove(Cell turn) {
        int row, column;
        Scanner line = new Scanner(in.nextLine());
        row = getNext(line);
        column = getNext(line);
        // I need this "if", if I want to read only two integers.
        // Delete it, if you want.
        if (line.hasNext()) {
            row = -1;
            column = -1;
        }
        return new Move(row, column, turn);
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            final Move move = new Move(in.nextInt(), in.nextInt(), cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}
