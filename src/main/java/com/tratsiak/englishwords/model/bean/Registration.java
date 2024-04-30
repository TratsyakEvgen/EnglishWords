package com.tratsiak.englishwords.model.bean;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Registration {

    @NotBlank(message = "Login must contain at least one non-whitespace character.")
    private String login;
    @NotBlank(message = "Password must contain at least one non-whitespace character.")
    private String password;
    @NotBlank(message = "Repeat password must contain at least one non-whitespace character.")
    private String repeatPassword;
}
