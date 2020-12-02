package by.testbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.services.async.AsynchronusService;
import lombok.SneakyThrows;

@Service
public class InitializationService {
    @Autowired
    private AsynchronusService asynchronusService;

    @Autowired
    private ViberService viberService;

    @SneakyThrows
    public void initalize() {
        viberService.setWeebhook();
        asynchronusService.executeAsynchronously();
    }
}
