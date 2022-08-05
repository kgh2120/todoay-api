package com.todoay.api.domain.hashtag.service;

import com.todoay.api.domain.hashtag.dto.HashtagAutoCompleteResponseDto;
import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.dto.HashtagSearchResopnseDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.exception.NoMoreDataException;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class HashtagServiceImplTest {

    @Autowired HashtagService hashtagService;
    @Autowired
    HashtagRepository repository;

    String name = "#태그";

    @BeforeEach
    void beforeEach() {
        for (int i = 0; i < 20; i++) {
            Hashtag h1 = new Hashtag("#태그"+i);
            repository.save(h1);
        }
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }


    @Test
    void autoComplete_test() {

        HashtagAutoCompleteResponseDto dto = hashtagService.searchHashtagAutoComplete(name);
        List<HashtagInfoDto> infos = dto.getInfos();

        assertThat(infos).hasSize(5);
    }

    @Test
    void search_test() {

        HashtagSearchResopnseDto dto = hashtagService.searchHashtag(name, 0);
        List<HashtagInfoDto> infos = dto.getInfos();
        int nextPage = dto.getNextPage();
        boolean hasNext = dto.isHasNext();

        assertThat(hasNext).isTrue();
        assertThat(nextPage).isSameAs(1);
        assertThat(infos).hasSize(5);

    }

    @Test
    void search_test_exception_page() {
        assertThatThrownBy(() -> hashtagService.searchHashtag(name, 1000))
                .isInstanceOf(NoMoreDataException.class);
    }

    @Test
    void search_test_exception_name() {
        assertThatThrownBy(() -> hashtagService.searchHashtag("qwecqwceqwecqwec", 0))
                .isInstanceOf(NoMoreDataException.class);
    }

}