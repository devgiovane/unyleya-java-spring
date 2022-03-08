package com.github.giovane.api.service.user.storage;


import com.github.giovane.api.entity.UserEntity;
import com.github.giovane.api.factory.NotificationError;
import com.github.giovane.api.repository.IUserRepository;
import com.github.giovane.api.service.user.validation.UserForm;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.ArrayList;


public final class UserStorage {

    private final IUserRepository repository;

    public UserStorage(IUserRepository repository) {
        this.repository = repository;
    }

    public boolean save(NotificationError notificationError, UserForm userForm) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userForm.getName());
            userEntity.setLastName(userEntity.getLastName());
            userEntity.setCpf(userForm.getCpf());
            userEntity.setEmail(userForm.getEmail());
            userEntity.setAddress(userForm.getAddress());

            this.repository.save(userEntity);
            return true;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in create user");
            return false;
        }
    }

    public List<UserEntity> findAll(NotificationError notificationError) {
        try {
            List<UserEntity> userEntityList = this.repository.findAll();
            if (userEntityList.isEmpty()) {
                notificationError.setStatusCode(HttpStatus.NOT_FOUND);
                return new ArrayList<>();
            }
            return userEntityList;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in create user");
            return new ArrayList<>();
        }
    }

    public UserEntity findOne(NotificationError notificationError, UserEntity userEntity) {
        return userEntity;
    }

    public boolean update(NotificationError notificationError, UserEntity userEntity, UserForm userForm) {
        try {
            userEntity.setName(userForm.getName());
            userEntity.setLastName(userEntity.getLastName());
            userEntity.setCpf(userForm.getCpf());
            userEntity.setEmail(userForm.getEmail());
            userEntity.setAddress(userForm.getAddress());

            this.repository.save(userEntity);
            return true;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in create user");
            return false;
        }
    }

    public boolean updateEmail(NotificationError notificationError, UserEntity userEntity, String email) {
        try {
            userEntity.setEmail(email);
            this.repository.save(userEntity);
            return true;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in create user");
            return false;
        }
    }

    public boolean delete(NotificationError notificationError, UserEntity userEntity) {
        try {
            this.repository.delete(userEntity);
            return true;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in create user");
            return false;
        }
    }


}
