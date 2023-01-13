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

    @Size(min = 1, max = 30, message = "The title's length is not good!")
    @NotNull(message = "Incorrect title!")
    @NotEmpty(message = "Incorrect title!")
    @NotBlank(message = "Incorrect title!")
    private String title;

    @Size(min = 1, max = 200, message = "The description's length is not good!")
    @NotNull(message = "Incorrect description!")
    @NotEmpty(message = "Incorrect description!")
    @NotBlank(message = "Incorrect description!")
    private String description;
}
