package serveur.util.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataCSV {
    static HashMap<String, String> filetoStringHashMap(File fileName) {
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

    static ArrayList<String> filetoStringArrayList(File fileName) {
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

    static ArrayList<String> stringValueToStringArrayList(String value) {
        ArrayList<String> list = new ArrayList<String>();
        for (String str : value.split(",")) {
            list.add(str);
        }
        return list;

    }

    static HashMap<String, String> stringValueToStringHashMap(String value) {
        HashMap<String, String> dic = new HashMap<String, String>();
        for (String str : value.split(",")) {
            if (str.split("|").length == 1) {
                dic.put(str.split("|")[0], str.split("|")[1]);
            }
        }
        return dic;
    }

    static String stringArrayListToString(ArrayList<String> list) {
        String strList = "";
        for (String str : list) {
            strList += str;
        }
        return strList;
    }

    static String StringHashMaptoString(HashMap<String, String> dic) {
        String strDic = "";
        for (Map.Entry<String, String> m : dic.entrySet()) {
            strDic += m.getKey() + "|" + m.getValue() + ",";
        }
        return strDic.substring(0, strDic.length() - 1);
    }

}
