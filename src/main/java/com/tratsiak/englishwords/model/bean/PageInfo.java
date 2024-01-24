package com.tratsiak.englishwords.model.bean;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PageInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Min(value = 0, message = "The page number must be >=0")
    private int page;

    @Min(value = 1, message = "The page size must be >0")
    private int size;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
