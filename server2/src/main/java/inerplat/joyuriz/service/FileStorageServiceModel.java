package inerplat.joyuriz.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;

@Service
public class FileStorageServiceModel implements FileStorageService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Path root = Paths.get("uploads");
    private static String hash = null;

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String newFileName = null;
        try {
            newFileName = getHash(file).concat(".".concat(filename.substring(filename.lastIndexOf(".") + 1)));
            Files.copy(file.getInputStream(), this.root.resolve(newFileName));
        } catch (Exception e) {
            //throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            logger.error(e.getMessage());
        }
        return newFileName;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public String getHash(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(file.getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }
}
