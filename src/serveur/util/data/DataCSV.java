package serveur.util.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import serveur.util.ChainObject.ChainObject;
import serveur.util.security.Block;

public class DataCSV {
    public static HashMap<String, String> filetoStringHashMap(File fileName) {
        HashMap<String, String> map = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] lineList = line.split(";");
                map.put(lineList[0], lineList[1]);
            }
            br.close();
            return map;

        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<String> filetoStringArrayList(File fileName) {
        ArrayList<String> list = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                list.add(line);

            }
            br.close();
            return list;

        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<String> stringValueToStringArrayList(String value) {
        ArrayList<String> list = new ArrayList<String>();
        for (String str : value.split(",")) {
            list.add(str);
        }
        return list;

    }

    public static HashMap<String, String> stringValueToStringHashMap(String value) {
        HashMap<String, String> dic = new HashMap<String, String>();
        for (String str : value.split(",")) {
            if (str.split("|").length == 1) {
                dic.put(str.split("|")[0], str.split("|")[1]);
            }
        }
        return dic;
    }

    public static String stringArrayListToString(ArrayList<String> list) {
        String strList = "";
        for (String str : list) {
            strList += str;
        }
        return strList;
    }

    public static String stringHashMaptoString(HashMap<String, String> dic) {
        String strDic = "";
        for (Map.Entry<String, String> m : dic.entrySet()) {
            strDic += m.getKey() + "|" + m.getValue() + ",";
        }
        return strDic.substring(0, strDic.length() - 1);
    }

    public static void writeChainObject(ChainObject chO, String filePath) {
        // chO.toWriteFormat();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.append(chO.toWriteFormat());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Block readBlock(String fileName) {
        String block = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                block += line;
            }
            br.close();
            String lineAddition = "";
            String lastHash = "";
            ArrayList<String> elements = new ArrayList<String>();
            for (int i = 0; i < block.length(); i++) {
                lineAddition += block.charAt(i);
                if (i == 63) {
                    lastHash = lineAddition;
                    lineAddition = "";
                } else if ((i + 1) % 64 == 0) {
                    elements.add(lineAddition);
                    lineAddition = "";
                }
            }
            return new Block(Integer.parseInt(fileName.split(".csv")[0]), lastHash, elements);

        } catch (Exception e) {
            return null;
        }
    }
}
