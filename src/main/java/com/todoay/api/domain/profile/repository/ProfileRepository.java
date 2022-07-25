package com.todoay.api.domain.profile.repository;

import com.todoay.api.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p inner join p.auth a on a.email = :email")
    Optional<Profile> findProfileByAuthEmail(@Param("email") String email);

    Optional<Profile> findProfileByNickname(String nickname);
}
