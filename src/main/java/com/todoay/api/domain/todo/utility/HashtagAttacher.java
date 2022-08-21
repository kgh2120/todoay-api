package com.todoay.api.domain.todo.utility;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.entity.Todo;

import java.util.List;
import java.util.stream.Collectors;

public interface HashtagAttacher {

     static void attachHashtag(Todo todo, List<HashtagInfoDto> hashtagNames, HashtagRepository hashtagRepository){
         if(hashtagNames==null || hashtagNames.isEmpty())
             return;
        List<Hashtag> hashtags = getHashtagsByHashtagNames(hashtagNames, hashtagRepository);
        todo.associateWithHashtag(hashtags);


    }

    private static List<Hashtag> getHashtagsByHashtagNames(List<HashtagInfoDto> hashtagNames, HashtagRepository hashtagRepository) {
        return hashtagNames.stream().map(n-> hashtagRepository.findByName(n.getName())
                .orElseGet(() -> hashtagRepository.save(new Hashtag(n.getName())))
            ).collect(Collectors.toList());
    }
}
