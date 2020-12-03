package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
    public List<Car> findAllByBrand(String brand);
}
