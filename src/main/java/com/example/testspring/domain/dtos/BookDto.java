package com.example.testspring.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BookDto {
    private Long id;

    @Size(min = 1, max = 30)
    @NotNull @NotEmpty @NotBlank
    private String title;

    @Size(min = 1, max = 200)
    @NotNull @NotEmpty @NotBlank
    private String description;
}
