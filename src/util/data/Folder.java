package util.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Folder {

    /**
     * This method take a {@link String} folder name and {@link File}
     * parentDirectory to create a folder in the parentDirectory with the specified
     * name.
     * 
     * @param folderName      the specified name of your folder. The folder need to
     *                        be uniq.
     *                        If a folder with the same name all ready exist the
     *                        method do nothing.
     * @param parentDirectory the Parent directory of your folder. This need to be a
     *                        directory.
     * @return
     */
    public static boolean createDirectory(String folderName, File parentDirectory) {
        if (parentDirectory.isDirectory()) {
            String fullPath = parentDirectory.getAbsolutePath() + File.separator + folderName;
            File newDirectory = new File(fullPath);
            if (!newDirectory.exists()) {
                return newDirectory.mkdir();
            }
        }

        return false;
    }

    /**
     * This method scann a directory to give you all the directory present in.
     * 
     * @param folderName the folder you want to scan.
     * @return An arrayList of file correponding of a list of folder (directory)
     *         present in the folderName.
     */
    public static ArrayList<File> scanningFolderToFolderArrayList(File folderName) {
        ArrayList<File> folders = scanningFolderToFileArrayList(folderName);

        for (File file : folders) {
            if (!file.isDirectory()) {
                folders.remove(file);
            }
        }

        return folders;
    }

    /**
     * This method scann a directory to give you all the file present in.
     * 
     * @param folderName the folder you want to scan.
     * @return An arrayList of file correponding of a list of file (only)
     *         present in the folderName.
     */
    public static ArrayList<File> scanningFolderToFileOnlyArrayList(File folderName) {
        ArrayList<File> folders = scanningFolderToFileArrayList(folderName);

        for (File file : folders) {
            if (file.isDirectory()) {
                folders.remove(file);
            }
        }

        return folders;
    }

    /**
     * This method scann a directory to give you all the element present in.
     * 
     * @param folderName the folder you want to scan.
     * @return An arrayList of file correponding of a list of file (directory and
     *         file)
     *         present in the folderName.
     */
    public static ArrayList<File> scanningFolderToFileArrayList(File folderName) {
        ArrayList<File> folders = new ArrayList<File>();

        if (folderName.isDirectory()) {
            File[] files = folderName.listFiles();
            if (files != null) {
                folders.addAll(Arrays.asList(files));
            }
        }

        return folders;
    }
}
