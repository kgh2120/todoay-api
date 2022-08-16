package com.todoay.api.domain.todo.utility;

import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashtagAttacherTest {

    Map<String, Optional<String>> testerMap = new HashMap<>();


    @Autowired
    HashtagRepository repository;

    @BeforeEach
    void beforeEach() {
        testerMap.put("t1",Optional.of("t1"));
        testerMap.put("t2",Optional.of("t2"));
        testerMap.put("t3",Optional.of("t3"));
    }

    @Test
    void optionalTest() {
        String find = testerMap.get("t1").orElse("hello");
        assertThat(find).isEqualTo("t1");

    }
    @Test
    void optionalTest2() {
        String find = testerMap.getOrDefault("t13333",Optional.empty()).orElse("hello");
        assertThat(find).isEqualTo("hello");

    }

    @Test
    void hashtagOptionalTest() {
        String t1 = "#태그1";
        String t3 = "#태그2";
        List<String> names = new ArrayList<>();
        names.add(t1);
        names.add(t3);




        System.out.println("before : " + repository.count());
        for (String name : names) {
            boolean present = repository.findByName(name).isPresent();
            System.out.println("present = " + present);
            Hashtag hashtag = repository.findByName(name).orElseGet(() -> repository.save(new Hashtag(name)));
            System.out.println(String.format("hashtag id : %d, name : %s", hashtag.getId(),hashtag.getName()));
        }

        System.out.println("after : " + repository.count());
        assertThat(repository.count()).isSameAs(10L);

    }

}