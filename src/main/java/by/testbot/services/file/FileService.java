package by.testbot.services.file;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.SneakyThrows;

@Service
@Getter
public class FileService {
    final static String FILE_FOLDER_PATH = "/files/";

    public FileSystemResource getFile(String filename) {
        return new FileSystemResource(new File("." + FILE_FOLDER_PATH + filename));
    }

    @SneakyThrows
    public Long getFileSizeInBytes(String filename) {
        return new FileSystemResource(new File("." + FILE_FOLDER_PATH + filename)).contentLength();
    }
}
