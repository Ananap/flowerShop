package com.flowershop.model.product;

public class FlowerSearchCriteria {
    private String keyword;
    private Integer category;
    private Double priceFrom;
    private Double priceTo;

    public FlowerSearchCriteria(String keyword, Integer category, Double priceFrom, Double priceTo) {
        this.keyword = keyword;
        this.category = category;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Double priceTo) {
        this.priceTo = priceTo;
    }
}
