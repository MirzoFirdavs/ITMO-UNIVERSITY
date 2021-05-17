import java.util.*;
import java.io.*;

public class WordStatSortedLineIndex {
    public static void main(String args[]) {
    	Map<String, IntList> curStr = new HashMap<String, IntList>();
        Map<String, IntList> curPoss = new HashMap<String, IntList>();
    
    	try {
    		String inp = args[0];
    		FastScanner in = new FastScanner(new File(inp));
        	try {
        		int curPos = 1;
        		int curLine = 1;
        		String str = "";
        		while (true) { 
        			int i = 0, j = 0;
            		if (in.hasNextLine()) {
                		curPos = 1;
                		curLine++;
            		}
          			String line = in.nextWord();
                    if(line == null) {
                        break;
                    }
            		line += " ";
            		str = "";
            		int x = line.length();
            		while (i < x) {
            			if (!isValid(line.charAt(i))) {
                			if (isValid(line.charAt(j))) {
                    			str = line.substring(j, i).toLowerCase();
                    			IntList lst = curStr.getOrDefault(str, new IntList());
                        		lst.apnd(curLine);
                        		curStr.put(str, lst);
                        		lst = curPoss.getOrDefault(str, new IntList());
                        		lst.apnd(curPos);
                        		curPoss.put(str, lst);
                        		curPos++;
                    		}
                    		j = i + 1;
                		}
                		++i;
            		}
            		if (!in.hasNext()) {
            			break;
            		}
            	}
            } catch (IOException e) {
                System.out.println("There is some problem with input or output");
            } finally {
                try {
                    in.close();
                }  catch (IOException e) {
                	System.out.println("There is some problem with input or output");
                }
            }  
        } catch (IOException e) {
            System.out.println("FileNotFoundException");
        }

        ArrayList<String> idx = new ArrayList<String>(curStr.keySet());
        Collections.sort(idx);
        int b = idx.size();
        try {
            String out = args[1];
            Writer writer = new OutputStreamWriter(new FileOutputStream(out), "utf8");
            try {
            	for (int i = 0 ; i < b ; i++) {
            		String ans = idx.get(i);
                    //System.err.print(ans + " ");
                    IntList lst1 = curStr.getOrDefault(ans, new IntList());
                    IntList lst2 = curPoss.getOrDefault(ans, new IntList());
                    writer.write(ans + " " + lst1.size() + " ");
                    for (int j = 0; j < lst1.size(); ++j) {
                        writer.write(lst1.inp(j) + ":" + lst2.inp(j));
                        if(j + 1 != lst1.size()) {
                        	writer.write(" ");
                        }
                    }
                    writer.write('\n');
            	}
            } catch (IOException e) {
                System.out.println("There is some problem with input or output");
            } finally {
                try {
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

    private static boolean isValid(char a) {
        return (Character.isLetter(a) || Character.DASH_PUNCTUATION == Character.getType(a) || (a == '\''));
    }
}