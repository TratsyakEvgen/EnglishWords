package com.tratsiak.englishwords.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorResponse {
    private Timestamp timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
