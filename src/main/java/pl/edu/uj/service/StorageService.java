package pl.edu.uj.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StorageService {
    private static final Logger log = Logger.getLogger(StorageService.class.getSimpleName());
    private static final Path storage = Paths.get("target/files"); // TODO CHANGE

    public StorageService() {
        try {
            Files.createDirectories(storage);
        } catch (IOException e) {
            log.severe("Unable to create storage directory");
            e.printStackTrace();
        }
    }

    public void save(String appName, File file, String targetFileName) {
        Path appPath = storage.resolve(appName);
        try {
            Files.createDirectories(appPath);
            Path filePath = appPath.resolve(targetFileName);
            Files.copy(Paths.get(file.getAbsolutePath()), filePath);
        } catch (IOException e) {
            log.severe("Unable to save uploaded file for application " + appName);
            e.printStackTrace();
        }
    }

    public List<String> filesFor(String appName) {
        Path appPath = storage.resolve(appName);
        try {
            return Files.list(appPath).map(path -> path.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException e) {
            log.severe("Unable to list files for application " + appName);
            e.printStackTrace();
            throw new RuntimeException("Unable to list files"); //TODO ugly
        }
    }

    public void delete(String appName, String fileName) {
        Path filePath = storage.resolve(appName).resolve(fileName);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            log.severe("Unable to delete file " + fileName + " for application " + appName);
            e.printStackTrace();
        }
    }
}
