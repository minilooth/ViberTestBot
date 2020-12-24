package by.autocapital.services.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.autocapital.services.async.BroadcastServiceAsync;
import by.autocapital.services.file.FileService;
import by.autocapital.services.viber.ViberService;
import lombok.SneakyThrows;

@Service
public class InitializationService {

    @Autowired
    private ViberService viberService;

    @Autowired
    private BroadcastServiceAsync broadcastService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ConfigService configService;

    @SneakyThrows
    public void initalize() {
        configService.checkConfig();
        fileService.createDirectories();
        viberService.setWeebhook();
        broadcastService.runAsync();
    }
}