package com.github.giovane.api.service.user.validation;


import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;


@Data
public final class UserForm {

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @CPF
    @NotNull
    private String cpf;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String address;

}
