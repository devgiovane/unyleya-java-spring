package com.github.giovane.api.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.giovane.api.entity.UserEntity;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByCpf(String cpf);
    Optional<UserEntity> findByEmail(String email);
}
