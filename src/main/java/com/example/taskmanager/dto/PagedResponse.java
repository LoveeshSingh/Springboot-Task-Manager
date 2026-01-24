package com.example.taskmanager.dto;

import java.util.List;

public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int totalPages;
    private long totalElements;
    private boolean last;

    public PagedResponse(
            List<T> content,
            int page,
            int totalPages,
            long totalElements,
            boolean last) {

        this.content = content;
        this.page = page;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public boolean isLast() {
        return last;
    }
}
