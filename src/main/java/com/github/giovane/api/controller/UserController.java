package com.github.giovane.api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.giovane.api.entity.UserEntity;
import com.github.giovane.api.service.user.validation.UserExist;
import com.github.giovane.api.view.UserView;
import com.github.giovane.api.factory.NotificationError;
import com.github.giovane.api.repository.IUserRepository;
import com.github.giovane.api.service.user.storage.UserStorage;
import com.github.giovane.api.service.user.validation.UserForm;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user")
public final class UserController {

    private final UserView userView;
    private final UserStorage storage;
    private final UserExist userExist;

    @Autowired
    public UserController(IUserRepository repository) {
        this.userView = new UserView();
        this.storage = new UserStorage(repository);
        this.userExist = new UserExist(repository);
    }

    @PostMapping("")
    public @ResponseBody ResponseEntity<String> create(@Valid UserForm userForm, BindingResult bindingResult) {
        NotificationError notificationError = new NotificationError();
        if (bindingResult.hasErrors()) {
            notificationError.captureErrors(bindingResult);
            return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
        }
        Optional<UserEntity> userExist = this.userExist.byCpf(notificationError, userForm.getCpf());
        if(userExist.isPresent()) {
            return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
        }
        userExist = this.userExist.byEmail(notificationError, userForm.getEmail());
        if (userExist.isPresent()) {
            return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
        }
        boolean isSave = this.storage.save(notificationError, userForm);
        if (isSave) {
            return new ResponseEntity<>("", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
    }

    @GetMapping("")
    public ResponseEntity<String> readAll() {
        NotificationError notificationError = new NotificationError();
        List<UserEntity> userEntityList = this.storage.findAll(notificationError);
        if(!userEntityList.isEmpty()) {
            return new ResponseEntity<>(this.userView.showUsers(userEntityList), HttpStatus.OK);
        }
        return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readOne(@PathVariable(name = "id") Long id) {
        NotificationError notificationError = new NotificationError();
        Optional<UserEntity> userExist = this.userExist.byId(notificationError, id);
        if (userExist.isPresent()) {
            UserEntity userEntity = this.storage.findOne(notificationError, userExist.get());
            return new ResponseEntity<>(this.userView.showUser(userEntity), HttpStatus.OK);
        }
        return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @Valid UserForm userForm, BindingResult bindingResult) {
        boolean isEdited = false;
        NotificationError notificationError = new NotificationError();
        if (bindingResult.hasErrors()) {
            notificationError.captureErrors(bindingResult);
            return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
        }
        Optional<UserEntity> userExist = this.userExist.byId(notificationError, id);
        if (userExist.isPresent()) {
            isEdited = this.storage.update(notificationError, userExist.get(), userForm);
        }
        if (isEdited) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable(name = "id") Long id, String email) {
        NotificationError notificationError = new NotificationError();
        boolean isEdited = false;
        Optional<UserEntity> userExist = this.userExist.byId(notificationError, id);
        if (userExist.isPresent()) {
            isEdited = this.storage.updateEmail(notificationError, userExist.get(), email);
        }
        if (isEdited) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        NotificationError notificationError = new NotificationError();
        boolean isDeleted = false;
        Optional<UserEntity> userExist = this.userExist.byId(notificationError, id);
        if (userExist.isPresent()) {
            isDeleted = this.storage.delete(notificationError, userExist.get());
        }
        if (isDeleted) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationError.toJson(), notificationError.getStatusCode());
    }

}
