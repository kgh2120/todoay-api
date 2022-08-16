package com.todoay.api.domain.todo.utility;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.entity.Todo;

import java.util.ArrayList;
import java.util.List;

public interface HashtagAttacher {

    public static Todo attachHashtag(Todo todo, List<HashtagInfoDto> hashtagNames, HashtagRepository hashtagRepository){
        List<Hashtag> hashtags = getHashtagsByHashtagNames(hashtagNames, hashtagRepository);
        todo.associateWithHashtag(hashtags);
        return todo;
    }

    private static List<Hashtag> getHashtagsByHashtagNames(List<HashtagInfoDto> hashtagNames, HashtagRepository hashtagRepository) {
        List<Hashtag> tags = new ArrayList<>(); // dailyTodo로 전달할 list객체, 연관관계 메서드를 개별 Hashtag로 변경하면
        hashtagNames.forEach(n -> hashtagRepository.findByName(n.getName())
                .ifPresentOrElse(tags::add, // 검색 결과가 존재한다면 연관 관계를 맺을 리스트에 추가
                        () -> tags.add(hashtagRepository.save(new Hashtag(n.getName()))) // 없다면 DB에 저장하고 저장.
                ));
        return tags;
    }
}
