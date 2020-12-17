package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import by.testbot.models.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT DISTINCT brand FROM Car")
    public List<String> findDistinctBrand();

    @Query("SELECT c.model FROM Car c WHERE c.brand = ?1")
    public List<String> findModelByBrand(String brand);

    public Car findFirstByBrandAndModel(String brand, String model);
}
