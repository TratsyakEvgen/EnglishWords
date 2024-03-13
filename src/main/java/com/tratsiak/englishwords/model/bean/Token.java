package com.tratsiak.englishwords.model.bean;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Token {
    private String access;
    private String refresh;
}
