package by.testbot.services.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.services.async.BroadcastServiceAsync;
import by.testbot.services.file.FileService;
import by.testbot.services.viber.ViberService;
import lombok.SneakyThrows;

@Service
public class InitializationService {

    @Autowired
    private ViberService viberService;

    @Autowired
    private BroadcastServiceAsync broadcastService;

    @Autowired
    private FileService fileService;

    @SneakyThrows
    public void initalize() {
        fileService.createDirectories();
        viberService.setWeebhook();
        broadcastService.runAsync();
    }
}
