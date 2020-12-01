package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.ClientChatMessageHistory;
import by.testbot.repositories.ClientChatMessageHistoryRepository;

@Service
public class ClientChatMessageHistoryService {
    
    @Autowired
    private ClientChatMessageHistoryRepository clientChatMessageHistoryRepository;

    public void save(ClientChatMessageHistory clientChatMessageHistory) {
        clientChatMessageHistoryRepository.save(clientChatMessageHistory);
    }

    public void update(ClientChatMessageHistory clientChatMessageHistory) {
        clientChatMessageHistoryRepository.save(clientChatMessageHistory);
    }

    public void delete(ClientChatMessageHistory clientChatMessageHistory) {
        clientChatMessageHistoryRepository.delete(clientChatMessageHistory);
    }

    public List<ClientChatMessageHistory> getAll() {
        return clientChatMessageHistoryRepository.findAll();
    }

    public List<ClientChatMessageHistory> getAllByViberId(String viberId) {
        return clientChatMessageHistoryRepository.getByViberId(viberId);
    }


}
