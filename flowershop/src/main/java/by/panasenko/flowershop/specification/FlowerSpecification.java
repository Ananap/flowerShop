package by.panasenko.flowershop.specification;

import by.panasenko.flowershop.model.FlowerType;
import by.panasenko.flowershop.model.FlowerType_;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerSearchCriteria;
import by.panasenko.flowershop.model.product.Flower_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Objects;

public class FlowerSpecification {
    public static Specification<Flower> filterByAllCriteria(FlowerSearchCriteria flowerSearchCriteria) {
        Specification<Flower> specification = (
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
        );
        if (Objects.nonNull(flowerSearchCriteria.getKeyword())) {
            specification = specification.and(filterByKeyword(flowerSearchCriteria.getKeyword()));
        }
        if (Objects.nonNull(flowerSearchCriteria.getCategory())) {
            specification = specification.and(filterByCategory(flowerSearchCriteria.getCategory()));
        }
        if (Objects.nonNull(flowerSearchCriteria.getPriceTo()) && Objects.nonNull(flowerSearchCriteria.getPriceFrom())) {
            specification = specification.and(filterByPrice(flowerSearchCriteria.getPriceFrom(), flowerSearchCriteria.getPriceTo()));
        }
        return specification;
    }

    public static Specification<Flower> filterByKeyword(String keyword) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Flower_.NAME), "%" + keyword + "%");
    }

    public static Specification<Flower> filterByCategory(Integer category) {
        return (Specification<Flower>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Flower, FlowerType> flowerTypeJoin = root.join(Flower_.FLOWER_TYPE, JoinType.LEFT);
            final Predicate categoryPredicate = criteriaBuilder.equal(flowerTypeJoin.get(FlowerType_.ID), category);
            return criteriaBuilder.and(categoryPredicate);
        };
    }

    public static Specification<Flower> filterByPrice(Double from, Double to) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Flower_.PRICE), from, to);
    }
}
