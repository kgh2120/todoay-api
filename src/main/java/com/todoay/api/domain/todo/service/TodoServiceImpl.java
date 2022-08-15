package com.todoay.api.domain.todo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service @Transactional
public class TodoServiceImpl implements TodoService{


    private final HashtagRepository hashtagRepository;

//    public TodoServiceImpl(HashtagRepository hashtagRepository) {
//        this.hashtagRepository = hashtagRepository;
//    }

    @Override
    public void save() {

        // 해시 태그 names 리스트로 추출
        // 존재하는지 체크.
        // 존재 안한다면 생성. 및 association 걸기.


    }

    @Override
    public void update() {

    }

}
