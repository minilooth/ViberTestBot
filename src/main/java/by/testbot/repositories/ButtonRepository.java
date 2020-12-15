package by.testbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.Button;

public interface ButtonRepository extends JpaRepository<Button, Long> {
    public Button findFirstByCallbackData(String callbackData);
}
