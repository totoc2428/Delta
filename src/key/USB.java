package src.key;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import src.exception.key.USB.USBPublicKeyNotFoundException;

public class USB {
    private static boolean isWindowUsbKey(File key){
        String path = key.getAbsolutePath();
        return path.startsWith("D:\\") || path.startsWith("E:\\") || path.startsWith("F:\\") || path.startsWith("G:\\") || path.startsWith("H:\\") || path.startsWith("I:\\") || path.startsWith("K:\\") || path.startsWith("L:\\");
    }
    public static String findPublicKey(){
        File[] readers = File.listRoots();
        for (File reader : readers) {
            if (reader.canRead()) {
                String path = reader.getAbsolutePath();
                String nomreader = reader.getName();
                if ((path.startsWith("/Volumes/") || path.startsWith("/media/") || isWindowUsbKey(reader)) && nomreader.equals("DELTA")) {
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(path+File.separator+"publicKey.txt"));
                        String key = "";
                        while(( key += bf.readLine()) != null){}
                        bf.close();
                        return key;
                    } catch (FileNotFoundException e) {
                        new USBPublicKeyNotFoundException(e.getMessage());
                    }catch(IOException e){
                        new USBPublicKeyNotFoundException(e.getMessage());
                    }
                }
            }
            
        }

        return null;
    }
    public static String findPrivateKey(){
        File[] readers = File.listRoots();
        for (File reader : readers) {
            if (reader.canRead()) {
                String path = reader.getAbsolutePath();
                String nomreader = reader.getName();
                if ((path.startsWith("/Volumes/") || path.startsWith("/media/") || isWindowUsbKey(reader)) && nomreader.equals("DELTA")) {
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(path+File.separator+"privateKey.txt"));
                        String key = "";
                        while(( key += bf.readLine()) != null){}
                        bf.close();
                        return key;
                    } catch (FileNotFoundException e) {
                        new USBPublicKeyNotFoundException(e.getMessage());
                    }catch(IOException e){
                        new USBPublicKeyNotFoundException(e.getMessage());
                    }
                }
            }
            
        }

        return null;
    }
}
