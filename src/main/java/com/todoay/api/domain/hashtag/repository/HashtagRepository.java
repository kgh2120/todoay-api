package com.todoay.api.domain.hashtag.repository;

import com.todoay.api.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

    Optional<Hashtag> findByName(String name);


    // 미리보기 5개 검색하기
    List<Hashtag> findTop5ByNameStartsWith(String name);

    // 페이지로 검색하기
    Slice<Hashtag> findHashtagByNameContaining(String name, Pageable pageable);
}
