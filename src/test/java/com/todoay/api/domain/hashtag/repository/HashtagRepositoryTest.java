package com.todoay.api.domain.hashtag.repository;

import com.todoay.api.domain.hashtag.entity.Hashtag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashtagRepositoryTest {


    @Autowired
    HashtagRepository repository;

    @BeforeEach
    void beforeEach() {
        for (int i = 0; i < 20; i++) {
            Hashtag h1 = new Hashtag("#tag"+i);
            repository.save(h1);
        }
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }


    @Test
    void autoComplete_size_test() {
        List<Hashtag> tags = repository.findTop5ByNameStartsWith("#tag");
        assertThat(tags.size()).isSameAs(5);
    }

    @Test
    void search_first_test() {

        PageRequest request = PageRequest.of(0,5);
        String name = "#tag";

        Slice<Hashtag> tags = repository.findHashtagByNameStartsWith(name, request);
        List<Hashtag> content = tags.getContent();

        assertThat(tags.hasNext()).isTrue();
        assertThat(tags.getNumber()).isSameAs(0);
        assertThat(content.size()).isSameAs(5);

    }

    @Test
    void search_next_test() {
        PageRequest request = PageRequest.of(1,5);
        String name = "#tag";

        Slice<Hashtag> tags = repository.findHashtagByNameStartsWith(name, request);
        List<Hashtag> content = tags.getContent();

        assertThat(tags.hasNext()).isTrue();
        assertThat(tags.getNumber()).isSameAs(1);
        assertThat(content.size()).isSameAs(5);
    }

    @Test
    void search_no_more_search_exception() {
        PageRequest request = PageRequest.of(10,5);
        String name = "#tag";

        Slice<Hashtag> tags = repository.findHashtagByNameStartsWith(name, request);
        List<Hashtag> content = tags.getContent();
        assertThat(tags.hasNext()).isFalse();
        assertThat(tags.getNumber()).isNotSameAs(1);
        assertThat(content.size()).isNotSameAs(5);


    }



}