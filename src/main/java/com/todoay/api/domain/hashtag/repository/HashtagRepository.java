package com.todoay.api.domain.hashtag.repository;

import com.todoay.api.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

    Optional<Hashtag> findByName(String name);


    Slice<Hashtag> findHashtagByNameContaining(String name, Pageable pageable);
}
