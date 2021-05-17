import java.util.*;
import java.io.*;

public class WordStatInputShingles {

    public static boolean isValid(char a) {
        return (Character.isLetter(a) || Character.DASH_PUNCTUATION == Character.getType(a) || (a == '\''));
    }

    public static void main(String args[]) {
        Map<String, Integer> myMap = new LinkedHashMap<>();
        try {
            String inp = args[0];
            FastScanner in = new FastScanner(new File(inp));
            try {
                String str = "";
                while (true) {
                    int i = 0, j = 0;
                    String line = in. nextWord();
                    line += " ";
                    str = "";
                    int x = line.length();
                    for (int k = i; k < x; k++) {
                        if (!isValid(line.charAt(i))) {
                            j = i + 1;
                        }
                        ++i;
                        if (i - j >= 3) {
                            if (isValid(line.charAt(j))) {
                                str = line.substring(i - 3, i).toLowerCase();
                                if (myMap.containsKey(str)) {
                                    myMap.put(str, myMap.get(str) + 1);
                                } else {
                                    myMap.put(str, 1);
                                }
                            }
                        }
                    }
                    if (!in.hasNextLine()) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("There is some problem with input or output");
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("There is some problem with input or output");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
        }
        try {
            String out = args[1];
            Writer writer = new OutputStreamWriter(new FileOutputStream(out), "utf8");
            try {
                for (Map.Entry<String, Integer> m : myMap.entrySet()) {
                    writer.write(m.getKey() + " " + m.getValue() + "\n");
                }
                //writer.close();
            } catch (IOException e) {
                System.out.println("There is some problem with input or output");
            } finally{
                try{
                    writer.close();
                } catch (IOException e) {
                    System.out.println("There is some problem with input or output");
                }
            } 
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
        }
    }
}