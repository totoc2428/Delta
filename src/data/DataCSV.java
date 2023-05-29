package src.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import src.exception.data.DataNotFoundExecption;

public class DataCSV implements Data {
    public static ArrayList<ArrayList<String>> totable(File file){
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine()) != null ){
                String[] linelist = line.split(";");
                ArrayList<String> lineArrayList = new ArrayList<String>(); 
                for(String str : linelist){
                    lineArrayList.add(str);
                }
                data.add(lineArrayList);
            }
            br.close();
        } catch (Exception e) {
            new DataNotFoundExecption(e.getMessage());
        }
        return data;
    }
    public static HashMap<String,String> toHashMap(File file){
        HashMap<String,String> data = new HashMap<String,String>();
        ArrayList<ArrayList<String>> tabledata = DataCSV.totable(file);
        for(ArrayList<String> list : tabledata){
            data.put(list.get(0), list.get(1));
        }
        return data;
    }
}
