package com.mussa.fintech.sme.common.dto;

import java.util.List;

public class PagedResponse<T> extends ApiResponse {

    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    private PagedResponse(
            String message,
            List<T> items,
            int page,
            int size,
            long totalElements,
            int totalPages) {

        super(true, message, 200);
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public static <T> PagedResponse<T> of(
            String message,
            List<T> items,
            int page,
            int size,
            long totalElements,
            int totalPages) {

        return new PagedResponse<>(
                message, items, page, size, totalElements, totalPages);
    }
}

