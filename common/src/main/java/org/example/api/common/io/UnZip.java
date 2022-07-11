package org.example.api.common.io;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class UnZip {
    public  static  void un(String path,String extractpath){
        ZipFile zipFile = new ZipFile(path);
        zipFile.setCharset(StandardCharsets.UTF_8);
        try {
            zipFile.extractAll(extractpath);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        for (File file : new File("Image").listFiles()) {

            if (file.isDirectory()){

                for (File listFile : file.listFiles()) {

                    listFile.renameTo(new File(listFile.getAbsolutePath().replaceAll("#","")));
                }
            }
        }






    }
}
