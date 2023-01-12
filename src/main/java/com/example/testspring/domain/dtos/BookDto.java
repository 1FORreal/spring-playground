package com.example.testspring.domain.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BookDto {
    private Long id;
    private String title;
    private String description;
}
