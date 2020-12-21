package by.testbot.services;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
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
    public void saveAll(Iterable<Manager> managers) {
        managerRepository.saveAll(managers);
    }

    @Transactional
    public void delete(Manager manager) {
        managerRepository.delete(manager);
    }

    @Transactional
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Transactional
    public Manager getByPhoneNumber(String phoneNumber) {
        return managerRepository.findFirstByPhoneNumber(phoneNumber);
    }

    public Boolean checkPhoneNumber(String phoneNumber) {
        return getByPhoneNumber(phoneNumber) != null;
    }

    public String getManagersStatistics() {
        List<Manager> managers = getAll();
        StringBuilder stringBuilder = new StringBuilder();

        for (Manager manager : managers) {
            stringBuilder.append(managers.indexOf(manager) + 1)
                         .append(" - ")
                         .append(manager.getFirstname() == null ? StringUtils.EMPTY : manager.getFirstname())
                         .append(" ")
                         .append(manager.getSurname() == null ? StringUtils.EMPTY : manager.getSurname())
                         .append(", ")
                         .append(manager.getPhoneNumber() == null ? StringUtils.EMPTY : manager.getPhoneNumber())
                         .append("\nКоличество отправленных отложенных сообщений: ")
                         .append(manager.getCountOfPostponeMessages())
                         .append(managers.indexOf(manager) != managers.size() - 1 ? "\n\n" : StringUtils.EMPTY);
        }
        return stringBuilder.toString();
    }

    public String getManagerInformation(Manager manager) {
        Objects.requireNonNull(manager, "Manager is null");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Имя: ")
                     .append(manager.getFirstname() != null ? manager.getFirstname() : "Не указал")
                     .append("\n")
                     .append("Фамилия: ")
                     .append(manager.getSurname() != null ? manager.getSurname() : "Не указал")
                     .append("\n")
                     .append("Номер телефона: ")
                     .append(manager.getPhoneNumber() != null ? manager.getPhoneNumber() : "Не указал");

        return stringBuilder.toString();
    }

    public String formatManagers(List<Manager> managers) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Список менеджеров:\n");

        for (Manager manager : managers) {
            stringBuilder.append(managers.indexOf(manager) + 1)
                         .append(" - ")
                         .append("Имя: ")
                         .append(manager.getFirstname() == null ? "Еще не указал" : manager.getFirstname())
                         .append("\nФамилия: ")
                         .append(manager.getSurname() == null ? "Еще не указал" : manager.getSurname())
                         .append("\nНомер телефона: ")
                         .append(manager.getPhoneNumber() == null ? "Еще не указал" : manager.getPhoneNumber())
                         .append(managers.indexOf(manager) != managers.size() - 1 ? "\n\n" : StringUtils.EMPTY);
        }

        return stringBuilder.toString();
    }
}
