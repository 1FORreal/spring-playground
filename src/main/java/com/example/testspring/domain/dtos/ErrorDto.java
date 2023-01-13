package com.example.testspring.domain.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorDto {
    private LocalDateTime timestamp;
    private String message;



}
