package net.ivanz.staffAccountingSystem.services;

import lombok.extern.log4j.Log4j2;
import net.ivanz.staffAccountingSystem.exceptions.FileStorageException;
import net.ivanz.staffAccountingSystem.exceptions.MyFileNotFoundException;
import net.ivanz.staffAccountingSystem.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Log4j2
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties){
        log.debug("FileStorageService: fileStorageProperties = {}", fileStorageProperties);
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error("Could not create the directory where the uploaded files will be stored.");
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file){
        log.debug("storeFile: file = {}", file);
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                log.error("Sorry! Filename contains invalid path sequence: fileName = {}", fileName);
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            log.error("Could not store file: fileName = {}", fileName);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName){
        log.debug("loadFileAsResource: fileName = {}", fileName);
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            } else {
                log.error("File not found: fileName = {}", fileName);
                throw new MyFileNotFoundException("File not found" + fileName);
            }
        } catch (MalformedURLException ex) {
            log.error("File not found: fileName = {}", fileName);
            throw new MyFileNotFoundException("File not found" + fileName, ex);
        }
    }
}
