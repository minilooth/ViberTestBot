package by.autocapital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findFirstByPhoneNumber(String phoneNumber);
}
