package by.autocapital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.User;
import by.autocapital.models.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findFirstByViberId(String viberId);
    public List<User> findAllByRole(Role role);
}
