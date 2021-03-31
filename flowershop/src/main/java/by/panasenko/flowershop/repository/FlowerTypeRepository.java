package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.FlowerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerTypeRepository extends JpaRepository<FlowerType, Integer> {
    FlowerType getOne(Integer integer);
}
