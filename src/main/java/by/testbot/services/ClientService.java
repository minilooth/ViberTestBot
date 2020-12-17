package by.testbot.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.Client;
import by.testbot.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Transactional
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client getByPhoneNumber(String phoneNumber) {
        return clientRepository.findFirstByPhoneNumber(phoneNumber);
    }

    public String getClientInformation(Client client) {
        Objects.requireNonNull(client, "Client is null");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Viber имя: ")
                     .append(client.getUser().getName())
                     .append("\n")
                     .append("Имя в диалоге: ")
                     .append(client.getName() != null ? client.getName() : "Не указал")
                     .append("\n")
                     .append("Номер телефона: ")
                     .append(client.getPhoneNumber() != null ? client.getPhoneNumber() : "Не указал");

        return stringBuilder.toString();
    }
}
