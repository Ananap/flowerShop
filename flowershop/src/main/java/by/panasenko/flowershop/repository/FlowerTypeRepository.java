package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.FlowerType;
import by.panasenko.flowershop.model.product.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerTypeRepository extends JpaRepository<FlowerType, Integer> {
    FlowerType findByFlower(Flower flower);

    @Override
    FlowerType getOne(Integer integer);
}
