package by.autocapital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    public Manager findFirstByPhoneNumber(String phoneNumber);
}
