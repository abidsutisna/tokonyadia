package com.enigma.tokonyadia.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponseWrapper<T> {
    private List<T> payload;
    private Integer totalPage;
    private Integer totalElement;
    private Integer currentPage;
    private Integer pageSize;

    public PageResponseWrapper(Page<T> page) {
        this.payload = page.getContent();
        this.totalPage = page.getTotalPages();
        this.totalElement = page.getNumberOfElements();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
    }
    
    
}
