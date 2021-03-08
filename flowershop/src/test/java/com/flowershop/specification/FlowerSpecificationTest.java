package com.flowershop.specification;

import com.flowershop.model.product.Flower;
import com.flowershop.model.product.FlowerSearchCriteria;
import com.flowershop.repository.FlowerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FlowerSpecificationTest {

    @Autowired
    private FlowerRepository flowerRepository;

    @Test
    public void filterByCategoryTest() {
        Integer categoryId = 2;
        Specification<Flower> specification = FlowerSpecification.filterByCategory(categoryId);
        List<Flower> flowerList = flowerRepository.findAll(specification);
        assertNotNull(flowerList);
        assertEquals(2, flowerList.size());
    }

    @Test
    public void filterByKeywordTest() {
        String keyword = "иалк";
        Specification<Flower> specification = FlowerSpecification.filterByKeyword(keyword);
        List<Flower> flowerList = flowerRepository.findAll(specification);
        assertNotNull(flowerList);
        assertEquals(1, flowerList.size());
    }

    @Test
    public void filterByPriceTest() {
        Double priceFrom = 50.0;
        Double priceTo = 150.0;
        Specification<Flower> specification = FlowerSpecification.filterByPrice(priceFrom, priceTo);
        List<Flower> flowerList = flowerRepository.findAll(specification);
        assertNotNull(flowerList);
        assertEquals(3, flowerList.size());
    }

    @Test
    public void filterByAllCriteriaTest() {
        String keyword = "та";
        Integer category = 2;
        Double priceFrom = 50.0;
        Double priceTo = 150.0;
        FlowerSearchCriteria flowerSearchCriteria = new FlowerSearchCriteria(keyword, category, priceFrom, priceTo);
        Specification<Flower> specification = FlowerSpecification.filterByAllCriteria(flowerSearchCriteria);
        List<Flower> flowerList = flowerRepository.findAll(specification);
        assertNotNull(flowerList);
        assertEquals(1, flowerList.size());

    }
}
