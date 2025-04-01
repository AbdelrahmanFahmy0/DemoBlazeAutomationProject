package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    //Retrieving the latest (most recently modified) file from a specified folder.
    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        //Checking if the folder contains files or not.if yes it stores them if not the method finishes
        if (files == null || files.length == 0) {
            LogsUtil.warn("No files found in directory: " + folderPath);
            return null;
        }
        //Finding the latest modified file
        File latestFile = files[0];
        for (File file : files) {
            if (file.lastModified() > latestFile.lastModified()) {
                latestFile = file;
            }
        }
        //Returning the latest modified file
        return latestFile;
    }


    //Calling the method and passing the folder path as file
    public static void deleteFiles(File dirPath) {

        //Checking if the path exists or not if not the method finishes
        if (dirPath == null || !dirPath.exists()) {
            LogsUtil.warn("Directory doesn't exist: " + dirPath);
            return;
        }

        //Checking if the folder contains files or not.if yes it stores them if not the method finishes
        File[] filesList = dirPath.listFiles();
        if (filesList == null) {
            LogsUtil.warn("Failed to list files in: " + dirPath);
            return;
        }

        //Iterate on the files to delete them , if they are folders then calling to the method will occur again
        for (File file : filesList) {
            if (file.isDirectory()) {
                deleteFiles(file);
            } else {
                if (file.getName().equals("environment.properties")) {
                    continue;
                }
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    LogsUtil.error("Failed to delete file: " + file);
                }
            }
        }
    }

    //Cleaning the specified directory by deleting it and all its contents quietly
    public static void cleanDirectory(File file) {
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file);
        } catch (Exception e) {
            LogsUtil.info(e.getMessage());
        }
    }
}
