package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.User;
import by.testbot.models.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findFirstByViberId(String viberId);
    public List<User> findAllByRole(Role role);
}
