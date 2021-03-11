package by.panasenko.flowershop.model.product;

import org.springframework.data.domain.Sort;

public class FlowerPageCriteria extends PageCriteria{
    private FlowerSearchCriteria flowerSearchCriteria;

    public FlowerPageCriteria(int pageNumber, int pageSize, Sort.Direction sortDir, String sortBy, FlowerSearchCriteria flowerSearchCriteria) {
        super(pageNumber, pageSize, sortDir, sortBy);
        this.flowerSearchCriteria = flowerSearchCriteria;
    }

    public FlowerSearchCriteria getFlowerSearchCriteria() {
        return flowerSearchCriteria;
    }

    public void setFlowerSearchCriteria(FlowerSearchCriteria flowerSearchCriteria) {
        this.flowerSearchCriteria = flowerSearchCriteria;
    }
}
