package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.product.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Integer>, JpaSpecificationExecutor<Flower> {
    void deleteById(Integer id);

    @Query("SELECT f.id, f.flowerImage, f.name, f.price FROM Flower f WHERE f.name LIKE %:keyword%")
    List<Object[]> findByKeyword(String keyword);
}
