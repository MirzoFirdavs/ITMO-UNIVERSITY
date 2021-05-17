import java.util.*;
import java.io.*;

public class WordStatInputPrefix {
    public static void main(String args[]) {
        try {
            String inp = args[0];
            String out = args[1];
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inp), "utf8"));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(out), "utf8"));
            try {
                String ans = "";
                Map <String, Integer> map = new LinkedHashMap<>();
                while(true) {
                    int i = 0, j = 0;
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }   
                    line += " ";
                    ans = "";
                    while (i < line.length()) {
                        boolean isLetter = Character.isLetter(line.charAt(i));
                        boolean isDash = Character.DASH_PUNCTUATION == Character.getType(line.charAt(i));
                        boolean isApos = (line.charAt(i) == '\'');
                        if (!(isLetter || isDash || isApos)) {
                            j = i + 1;
                        }
                        i++;
                        if (i - j == 3) {
                            boolean isLetter2 = Character.isLetter(line.charAt(j));
                            boolean isDash2 = Character.DASH_PUNCTUATION == Character.getType(line.charAt(j));
                            boolean isApos2 = (line.charAt(j) == '\'');
                            if (isLetter2 || isDash2 || isApos2) {
                                ans = line.substring(j, i);
                                ans = ans.toLowerCase();
                                if (map.containsKey(ans)) {
                                    map.put(ans, map.get(ans) + 1);
                                }
                                else {
                                    map.put(ans, 1);
                                }
                            }
                        }
                    }
                }
                for (Map.Entry<String, Integer> e : map.entrySet()) {
                    writer.println(e.getKey() + " " + e.getValue());
                }
            }
            catch (IOException e) {
            }
            finally {
                try {
                    reader.close();
                    writer.close();
                }
                catch (IOException e) {
                }
                finally{
                }
            }
        }
        catch (FileNotFoundException e) {
        }
        catch (UnsupportedEncodingException e) {
        }
        finally {
        }
    }
}
