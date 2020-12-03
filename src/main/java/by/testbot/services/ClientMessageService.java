package by.testbot.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ClientMessage getLastByViberId(String viberId) {
        List<ClientMessage> messages = getAllByViberId(viberId).stream().sorted(Comparator.comparing(ClientMessage::getTimestamp)).collect(Collectors.toList());

        Collections.reverse(messages);

        return messages.get(0);
    }

    @Transactional
    public ClientMessage getBrandMessageByViberId(String viberId) {
        List<ClientMessage> messages = getAllByViberId(viberId).stream().sorted(Comparator.comparing(ClientMessage::getTimestamp)).collect(Collectors.toList());
        
        Collections.reverse(messages);

        messages = messages.stream().filter(m -> m.getBrand() != null).collect(Collectors.toList());

        return messages.get(0);
    }

    @Transactional
    public ClientMessage getModelMessageByViberId(String viberId) {
        List<ClientMessage> messages = getAllByViberId(viberId).stream().sorted(Comparator.comparing(ClientMessage::getTimestamp)).collect(Collectors.toList());
        
        Collections.reverse(messages);

        messages = messages.stream().filter(m -> m.getModel() != null).collect(Collectors.toList());

        return messages.get(0);
    }

    @Transactional
    public ClientMessage getYearsMessageByViberId(String viberId) {
        List<ClientMessage> messages = getAllByViberId(viberId).stream().sorted(Comparator.comparing(ClientMessage::getTimestamp)).collect(Collectors.toList());
        
        Collections.reverse(messages);

        messages = messages.stream().filter(m -> m.getYearOfIssueFrom() != null && m.getYearOfIssueTo() != null).collect(Collectors.toList());

        return messages.get(0);
    }
}
