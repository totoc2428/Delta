package serveur.util.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import serveur.util.security.Block;

public class DataCSV {
    public static void encryptCSV(File file, PublicKey publicKey) {

        try (FileInputStream inputStream = new FileInputStream(file)) {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            FileOutputStream outputStream = new FileOutputStream(file.getName() + ".enc");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            cipherInputStream.close();
            outputStream.close();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> decryptCSV(File file, PrivateKey privateKey) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cipherInputStream));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            cipherInputStream.close();
            reader.close();
            return lines;
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
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

    public static ArrayList<ArrayList<String>> totable(File file) {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] linelist = line.split(";");
                ArrayList<String> lineArrayList = new ArrayList<String>();
                for (String str : linelist) {
                    lineArrayList.add(str);
                }
                data.add(lineArrayList);
            }
            br.close();
        } catch (Exception e) {
            new Exception(e.getMessage());
        }
        return data;
    }

    public static HashMap<String, String> toHashMap(File file) {
        HashMap<String, String> data = new HashMap<String, String>();
        ArrayList<ArrayList<String>> tabledata = DataCSV.totable(file);
        for (ArrayList<String> list : tabledata) {
            data.put(list.get(0), list.get(1));
        }
        return data;
    }
}
