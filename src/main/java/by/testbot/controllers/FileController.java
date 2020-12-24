package by.testbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.testbot.services.file.FileService;
import by.testbot.services.viber.ViberService;
import lombok.SneakyThrows;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = ViberService.FILE_URL + "{filename}", method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable("filename") String filename) {
        FileSystemResource fileSystemResource = fileService.getFile(filename);        

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return new ResponseEntity<>(new ByteArrayResource(fileSystemResource.getInputStream().readAllBytes()), headers, HttpStatus.OK);
    }

    @RequestMapping(value = ViberService.PICTURE_URL + "{filename}", method = RequestMethod.GET)
    @SneakyThrows
    public ResponseEntity<ByteArrayResource> getPicture(@PathVariable("filename") String filename) {
        FileSystemResource fileSystemResource = fileService.getPicture(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return new ResponseEntity<>(new ByteArrayResource(fileSystemResource.getInputStream().readAllBytes()), headers, HttpStatus.OK);
    } 

    @RequestMapping(value = ViberService.KEYBOARD_ICON_URL + "{filename}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @SneakyThrows
    public ResponseEntity<ByteArrayResource> getIcon(@PathVariable("filename") String filename) {
        FileSystemResource fileSystemResource = fileService.getIcon(filename);

        return new ResponseEntity<>(new ByteArrayResource(fileSystemResource.getInputStream().readAllBytes()), HttpStatus.OK);
    } 
}
