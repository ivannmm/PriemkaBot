package com.fierysoul.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public static String extension = ".json";

    File dataFolder;

    Gson serializer = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public FileManager(File dataDir) {
        if (!dataDir.exists())
            dataDir.mkdirs();
        this.dataFolder = dataDir;
    }

    public File getFolder() {
        return this.dataFolder;
    }

    public FileManager getFolder(String name) {
        return new FileManager(new File(this.dataFolder, name));
    }

    public File getFile(String fileName) {
        return new File(this.dataFolder, String.valueOf(fileName) + extension);
    }

    public File getFile(String directory, String fileName) {
        File dir = new File(this.dataFolder, directory);
        if (!dir.exists())
            dir.mkdirs();
        return new File(dir, String.valueOf(fileName) + extension);
    }

    public void writeFile(File file, Object content) throws IOException {
        FileWriter fw = new FileWriter(file);
        this.serializer.toJson(content, fw);
        fw.flush();
        fw.close();
    }

    public void writeFile(String fileName, Object content) throws IOException {
        writeFile(getFile(fileName), content);
    }

    public void writeFile(String dir, String fileName, Object content) throws IOException {
        writeFile(getFile(dir, fileName), content);
    }

    public <T> T readFile(Class<T> clazz, File file) throws Exception {
        if (file.length() == 0L) {
            file.delete();
            String fileName = file.getName().split("\\.")[0];
            file = getFile(fileName);
        }
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                return this.serializer.fromJson(fr, clazz);
            }
        }
        return clazz.newInstance();
    }

    public <T> T readFile(Class<T> clazz, String fileName) throws Exception {
        return readFile(clazz, getFile(fileName));
    }

    public <T> T readFile(Class<T> clazz, String dir, String fileName) throws Exception {
        return readFile(clazz, getFile(dir, fileName));
    }

    public <T> T readOrWriteDefault(Class<T> clazz, File file) throws Exception {
        T instance = readFile(clazz, file);
        if (!file.exists())
            writeFile(file, instance);
        return instance;
    }

    public <T> T readOrWriteDefault(Class<T> clazz, String fileName) throws Exception {
        return readOrWriteDefault(clazz, getFile(fileName));
    }

    public <T> T readOrWriteDefault(Class<T> clazz, String dir, String fileName) throws Exception {
        return readOrWriteDefault(clazz, getFile(dir, fileName));
    }
}
