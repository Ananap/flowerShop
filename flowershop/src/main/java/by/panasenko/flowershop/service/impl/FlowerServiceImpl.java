package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.exception.ShopException;
import by.panasenko.flowershop.model.FlowerType;
import by.panasenko.flowershop.model.product.Flower;
import by.panasenko.flowershop.model.product.FlowerPageCriteria;
import by.panasenko.flowershop.repository.FlowerRepository;
import by.panasenko.flowershop.service.FlowerService;
import by.panasenko.flowershop.specification.FlowerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class FlowerServiceImpl implements FlowerService {
    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private FlowerTypeServiceImpl flowerTypeServiceImpl;

    public Flower save(Flower flower){
        return flowerRepository.save(flower);
    }

    public Flower findOne(Integer id) throws ShopException {
        Optional<Flower> flower = Optional.of(flowerRepository.getOne(id));
        return flower.orElseThrow(() -> new ShopException("Item with this id doesn't exist"));
    }

    public void deleteFlower(Integer id) {
        flowerRepository.deleteById(id);
    }

    public Page<Flower> findAll(FlowerPageCriteria flowerPageCriteria) {
        Pageable pageable = buildPage(flowerPageCriteria);
        Specification<Flower> specification = FlowerSpecification.filterByAllCriteria(flowerPageCriteria.getFlowerSearchCriteria());
        return flowerRepository.findAll(specification, pageable);
    }

    public Pageable buildPage(FlowerPageCriteria flowerPageCriteria) {
        Sort sort = Sort.by(flowerPageCriteria.getSortDir(), flowerPageCriteria.getSortBy());
        Pageable pageable = PageRequest.of((flowerPageCriteria.getPageNumber() - 1), flowerPageCriteria.getPageSize(), sort);
        return pageable;
    }

    public void fillModel(FlowerPageCriteria flowerPageCriteria, Page<Flower> page, Model model) {
        List<FlowerType> flowerTypeList = flowerTypeServiceImpl.findAll();
        List<Flower> flowerList = page.getContent();
        List<Integer> sizeList = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 10));
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("size", flowerPageCriteria.getPageSize());
        model.addAttribute("flowerList", flowerList);
        if (flowerList.size() == 0) {
            model.addAttribute("emptyList", true);
        }
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", flowerPageCriteria.getPageNumber());
        model.addAttribute("sortField", flowerPageCriteria.getSortBy());
        model.addAttribute("sortDir", flowerPageCriteria.getSortDir());
        model.addAttribute("flowerTypeList", flowerTypeList);
        String reverseSortDir = flowerPageCriteria.getSortDir() == Sort.Direction.ASC ? "asc" : "desc";
        model.addAttribute("reverseSortDir", reverseSortDir);
        Integer category = flowerPageCriteria.getFlowerSearchCriteria().getCategory();
        String keyword = flowerPageCriteria.getFlowerSearchCriteria().getKeyword();
        Double priceFrom = flowerPageCriteria.getFlowerSearchCriteria().getPriceFrom();
        Double priceTo = flowerPageCriteria.getFlowerSearchCriteria().getPriceTo();
        if (Objects.nonNull(category)) {
            FlowerType flowerType = flowerTypeServiceImpl.getOne(category);
            model.addAttribute("category", category);
            model.addAttribute("flowerTypeSelected",  flowerType);
        }
        if (Objects.nonNull(keyword)) {
            model.addAttribute("keyword", keyword);
        }
        if (Objects.nonNull(priceFrom)) {
            model.addAttribute("priceFrom", priceFrom);
        }
        if (Objects.nonNull(priceTo)) {
            model.addAttribute("priceTo", priceTo);
        }
    }
}
