package by.testbot.services.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.logging.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.SneakyThrows;

@Service
@Getter
public class FileService {
    private final static Logger logger = Logger.getLogger(FileService.class);

    public final static String FILE_FOLDER_PATH = "./files/";
    public final static String EXCEL_FOLDER_PATH = FILE_FOLDER_PATH + "excel/";
    public final static String PICTURE_FOLDER_PATH = FILE_FOLDER_PATH + "pictures/";
    public final static String PICTURE_EMPTY_NAME = "empty.jpg";
    public final static Integer FILENAME_LENGTH = 10;

    @SneakyThrows
    public void createDirectories() {
        if (Files.notExists(Path.of(FILE_FOLDER_PATH), LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(Path.of(FILE_FOLDER_PATH));
            
            logger.info("Created files directory: " + FILE_FOLDER_PATH);
        }

        if (Files.notExists(Path.of(EXCEL_FOLDER_PATH), LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(Path.of(EXCEL_FOLDER_PATH));

            logger.info("Created excel files directory: " + EXCEL_FOLDER_PATH);
        }

        if (Files.notExists(Path.of(PICTURE_FOLDER_PATH), LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(Path.of(PICTURE_FOLDER_PATH));

            logger.info("Created pictures directory: " + EXCEL_FOLDER_PATH);
        }
    }

    public FileSystemResource getFile(String filename) {
        return new FileSystemResource(new File(EXCEL_FOLDER_PATH + filename));
    }

    public FileSystemResource getPicture(String filename) {
        return new FileSystemResource(new File(PICTURE_FOLDER_PATH + filename));
    }

    @SneakyThrows
    public Long getFileSizeInBytes(String filename) {
        return new FileSystemResource(new File(EXCEL_FOLDER_PATH + filename)).contentLength();
    }

    @SneakyThrows
    public String downloadPicture(String url) {
        if (!url.contains(".")) {
            throw new IllegalArgumentException("Unable to download image from: " + url);
        }
    
        String prettyUrl = url.substring(0, url.indexOf("?"));

        String extension = prettyUrl.substring(prettyUrl.lastIndexOf("."));
        String filename = "";

        do {
            filename = generateFilename(extension);
        }
        while(new File(PICTURE_FOLDER_PATH + filename).exists());

        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

        try(FileOutputStream fileOutputStream = new FileOutputStream(PICTURE_FOLDER_PATH + filename)) {
            fileOutputStream.write(in.readAllBytes());
        }

        return filename;
    }

    // @SneakyThrows
    // public String downloadFile(String url, String viberFilename) {
    //     if (!viberFilename.contains(".")) {
    //         throw new IllegalArgumentException("Unable to file from: " + url);
    //     }
    
    //     String extension = viberFilename.substring(viberFilename.lastIndexOf("."));
    //     String filename = "";

    //     do {
    //         filename = generateFilename(extension);
    //     }
    //     while(new File(PICTURE_FOLDER_PATH + filename).exists());

    //     BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

    //     try(FileOutputStream fileOutputStream = new FileOutputStream(PICTURE_FOLDER_PATH + filename)) {
    //         fileOutputStream.write(in.readAllBytes());
    //     }

    //     return filename;
    // }

    private String generateFilename(String extension) {
        return RandomStringUtils.randomAlphanumeric(FILENAME_LENGTH) + extension;
    }
}
