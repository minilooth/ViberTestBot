package by.testbot.services.async;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import by.testbot.thread.BroadcastThread;

@Service
public class AsynchronusService {
    @Autowired
    private BroadcastThread broadcastThread;

    @Autowired
    private TaskExecutor taskExecutor;

    public void executeAsynchronously() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                broadcastThread.run();
            }
        });
    }
    
}
