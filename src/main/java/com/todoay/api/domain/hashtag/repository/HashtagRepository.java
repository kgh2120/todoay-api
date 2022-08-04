package com.todoay.api.domain.hashtag.repository;

import com.todoay.api.domain.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
}
