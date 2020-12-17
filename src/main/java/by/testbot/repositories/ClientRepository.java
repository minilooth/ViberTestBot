package by.testbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findFirstByPhoneNumber(String phoneNumber);
}
