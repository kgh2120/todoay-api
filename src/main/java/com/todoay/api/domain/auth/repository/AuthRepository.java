package com.todoay.api.domain.auth.repository;

import com.todoay.api.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByEmail(String email);

    @Query("select a from Auth a inner join a.profile p on p.nickname = :nickname")
    Optional<Auth> findByNickname(@Param("nickname") String nickname);
}
