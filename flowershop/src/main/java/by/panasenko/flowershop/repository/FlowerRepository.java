package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.product.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Integer>, JpaSpecificationExecutor<Flower> {
    void deleteById(Integer id);
    List<Flower> findByNameContaining(String keyword);
}
