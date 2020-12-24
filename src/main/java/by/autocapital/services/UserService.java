package by.autocapital.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.autocapital.models.User;
import by.autocapital.models.enums.Role;
import by.autocapital.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User getByViberId(String viberId) {
        return userRepository.findFirstByViberId(viberId);
    }

    public List<User> getAllClients() {
        return userRepository.findAll().stream().filter(u -> u.getRole() == Role.USER).collect(Collectors.toList());
    }

    
}
