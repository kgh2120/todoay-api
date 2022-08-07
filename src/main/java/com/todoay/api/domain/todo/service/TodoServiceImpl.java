package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.repository.TodoRepository;
https://github.com/todoay/api/pull/136/conflict?name=src%252Fmain%252Fjava%252Fcom%252Ftodoay%252Fapi%252Fdomain%252Ftodo%252Fservice%252FTodoService.java&base_oid=02d1650aa0b4722a204b9b10ce68f2f883abd0b6&head_oid=6e35bed34e2448a6724639e4512f70c4a57f6602import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service @Transactional
public class TodoServiceImpl implements TodoService{


    private final HashtagRepository hashtagRepository;

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
