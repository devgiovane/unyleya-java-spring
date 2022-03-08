package com.github.giovane.api.service.user.validation;


import com.github.giovane.api.entity.UserEntity;
import com.github.giovane.api.factory.NotificationError;
import com.github.giovane.api.repository.IUserRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;


public final class UserExist {

    private final IUserRepository repository;

    public UserExist(IUserRepository repository) {
        this.repository = repository;
    }

    public Optional<UserEntity> byId(NotificationError notificationError, Long id) {
        Optional<UserEntity> userExist = this.repository.findById(id);
        if (userExist.isEmpty()) {
            notificationError.setStatusCode(HttpStatus.NOT_FOUND);
            return userExist;
        }
        notificationError.setStatusCode(HttpStatus.CONFLICT);
        return userExist;
    }

    public Optional<UserEntity> byCpf(NotificationError notificationError, String cpf) {
        Optional<UserEntity> userExist = this.repository.findByCpf(cpf);
        if (userExist.isEmpty()) {
            notificationError.setStatusCode(HttpStatus.NOT_FOUND);
            return userExist;
        }
        notificationError.setErrors("cpf", "already exists");
        notificationError.setStatusCode(HttpStatus.CONFLICT);
        return userExist;
    }

    public Optional<UserEntity> byEmail(NotificationError notificationError, String email) {
        Optional<UserEntity> userExist = this.repository.findByEmail(email);
        if (userExist.isEmpty()) {
            notificationError.setStatusCode(HttpStatus.NOT_FOUND);
            return userExist;
        }
        notificationError.setErrors("email", "already exists");
        notificationError.setStatusCode(HttpStatus.CONFLICT);
        return userExist;
    }

}
