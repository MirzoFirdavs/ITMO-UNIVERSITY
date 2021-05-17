package SustainableSorting;

import java.io.*;
import java.util.*;

public class SustainableSorting {
    private FastScanner myScan;
    private final SortedMap<Integer, ArrayList<String>> map;

    public SustainableSorting(String s) {
        try {
            myScan = new FastScanner(new File(s));
        } catch (Exception e) {
            System.out.println("Wrong name of file - " + s + " " + e);
        }
        map = new TreeMap<>();
    }

    public void read() {
        String word = "";
        int curCase = 0;
        try{
            do {
                switch (curCase) {
                    case 0:
                        try {
                            if(!myScan.hasNext()) break;
                            word = myScan.nextWord();
                            ///System.out.println(word);
                        } catch (IOException e) {
                            System.out.println("IOExpection with input");
                            return;
                        }
                        switch (word) {
                            case "add":
                                curCase = 1;
                                break;
                            case "remove":
                                curCase = 2;
                                break;
                            case "print":
                                curCase = 3;
                                break;
                            case "":
                                break;
                            default:
                                System.out.println("Gave unknown command - " + word);
                                return;
                        }
                        break;
                    case 1:
                        int index;
                        StringBuilder str = new StringBuilder();
                        try{
                            if (myScan.hasNext()) {
                                word = myScan.nextWord();
                                index = Integer.parseInt(word);
                            } else {
                                System.out.println("Single add");
                                return;
                            }
                        } catch (IOException e) {
                            System.out.println("Something wrong with input");
                            return;
                        } catch (NumberFormatException e) {
                            System.out.println("Inputed wrong integer - " + word);
                            return;
                        }
                        curCase = 0;
                        try {
                            while (!myScan.hasNextLine()) {
                                if (myScan.hasNext()) {
                                    word = myScan.nextWord();
                                } else {
                                    break;
                                }
                                str.append(word).append(" ");
                            }
                            ArrayList<String> arr = map.getOrDefault(index, new ArrayList<>());
                            arr.add(str.toString());
                            map.put(index, arr);
                        } catch (IOException e){
                            System.out.println("Some bad thing happend with Input:(");
                            return;
                        }
                        break;
                    case 2:
                        try{
                            if (myScan.hasNext()) {
                                word = myScan.nextWord();
                                index = Integer.parseInt(word);
                            } else {
                                System.out.println("Single remove");
                                return;
                            }
                        } catch (IOException e) {
                            System.out.println("Something wrong with input");
                            return;
                        } catch (NumberFormatException e) {
                            System.out.println("Input wrong integer - " + word);
                            return;
                        }
                        map.put(index, new ArrayList<>());
                        curCase = 0;
                        break;
                    case 3:
                        try{
                            if (myScan.hasNext()) {
                                word = myScan.nextWord();
                            } else {
                                System.out.println("Where the name of file that wa expected??");
                                return;
                            }
                        } catch (IOException e) {
                            System.out.println("Something wrong with input");
                            return;
                        }
                        FileWriter writer;
                        try {
                            writer = new FileWriter(new File(word));
                            try {
                                for (Map.Entry<Integer, ArrayList<String>> m: map.entrySet()) {
                                    ArrayList<String> arr = m.getValue();
                                    for(String x : arr){
                                        writer.write(m.getKey() + " " + x + System.lineSeparator());
                                    }
                                }
                                writer.flush();
                            } catch (IOException e) {
                                System.out.println("Couldn't write to the file - " + word);
                            }

                            try {
                                writer.close();
                            } catch (IOException e){
                                System.out.println("can't close output file - " + word);
                                return;
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("There is no file by name - " + word);
                            return;
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("There is another Encoding");
                            return;
                        }
                        curCase = 0;
                        break;
                }
            } while(myScan.hasNext());
        } catch (IOException e) {
            System.out.println("Something wrong with input");
        }
    }
}
