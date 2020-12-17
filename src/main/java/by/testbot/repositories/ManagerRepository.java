package by.testbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    public Manager findFirstByPhoneNumber(String phoneNumber);
}
