package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.Manager;
import by.testbot.repositories.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Transactional
    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    @Transactional
    public void delete(Manager manager) {
        managerRepository.delete(manager);
    }

    @Transactional
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }
}
