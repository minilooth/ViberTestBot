package by.testbot.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.ClientMessage;
import by.testbot.repositories.ClientMessageRepository;

@Service
public class ClientMessageService {
    
    @Autowired
    private ClientMessageRepository clientMessageRepository;

    @Transactional
    public void save(ClientMessage clientMessage) {
        clientMessageRepository.save(clientMessage);
    }

    @Transactional
    public void update(ClientMessage clientMessage) {
        clientMessageRepository.save(clientMessage);
    }

    @Transactional
    public void delete(ClientMessage clientMessage) {
        clientMessageRepository.delete(clientMessage);
    }

    @Transactional
    public List<ClientMessage> getAll() {
        return clientMessageRepository.findAll();
    }

    @Transactional
    public List<ClientMessage> getAllByViberId(String viberId) {
        return clientMessageRepository.getByViberId(viberId);
    }
}
