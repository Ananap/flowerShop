package by.panasenko.flowershop.model.product;

import org.springframework.data.domain.Sort;

public class PageCriteria {
    private int pageNumber;
    private int pageSize;
    private Sort.Direction sortDir;
    private String sortBy;

    public PageCriteria(int pageNumber, int pageSize, Sort.Direction sortDir, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDir = sortDir;
        this.sortBy = sortBy;
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

    public static Sort.Direction sortDirection(String sortDir) {
        if (sortDir == null){
            return null;
        } else if (sortDir.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
