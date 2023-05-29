package src.data;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import src.exception.data.DataNotFoundExecption;

public class DataText implements Data {
    public static ArrayList<String> totable(File file){
        ArrayList<String> data = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while( line != null ){
                line = br.readLine();
                data.add(line);
            }
            br.close();
        } catch (Exception e) {
            new DataNotFoundExecption(e.getMessage());
        }
        return data;
    }
    public static HashMap<String,String> toHashMap(File file){
        return new HashMap<String,String>();
    }
}
