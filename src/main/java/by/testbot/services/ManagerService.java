package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.Manager;
import by.testbot.repositories.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    public void update(Manager manager) {
        managerRepository.save(manager);
    }

    public void delete(Manager manager) {
        managerRepository.delete(manager);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Manager getByViberId(String viberId) {
        return managerRepository.findByViberId(viberId);
    }
}
