package com.flowershop.model.product;

import org.springframework.data.domain.Sort;

public class FlowerPageCriteria {
    private int pageNumber = 0;
    private int pageSize = 3;
    private Sort.Direction sortDir = Sort.Direction.ASC;
    private String sortBy = "name";
    private FlowerSearchCriteria flowerSearchCriteria;

    public FlowerPageCriteria(int pageNumber, int pageSize, Sort.Direction sortDir, String sortBy, FlowerSearchCriteria flowerSearchCriteria) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDir = sortDir;
        this.sortBy = sortBy;
        this.flowerSearchCriteria = flowerSearchCriteria;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDir() {
        return sortDir;
    }

    public void setSortDir(Sort.Direction sortDir) {
        this.sortDir = sortDir;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public FlowerSearchCriteria getFlowerSearchCriteria() {
        return flowerSearchCriteria;
    }

    public void setFlowerSearchCriteria(FlowerSearchCriteria flowerSearchCriteria) {
        this.flowerSearchCriteria = flowerSearchCriteria;
    }

    public static Sort.Direction sortDirection(String sortDir) {
        if (sortDir == null){
            return null;
        } else if (sortDir.equals("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
