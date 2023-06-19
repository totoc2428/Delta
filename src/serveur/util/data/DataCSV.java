package src.serveur.util.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DataCSV {
    static HashMap<String, String> toStringHashMap(File fileName) {
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

    static ArrayList<String> toStringArrayList(File fileName) {
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

    static ArrayList<Double> stringValueToDoubleArrayList(String value) {
        ArrayList<Double> list = new ArrayList<Double>();
        for (String str : value.split(",")) {
            try {
                list.add(Double.parseDouble(str));
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;

    }
}
