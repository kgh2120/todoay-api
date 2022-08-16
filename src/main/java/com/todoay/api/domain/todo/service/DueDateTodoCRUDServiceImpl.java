package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.dto.DueDateTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveResponseDto;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DueDateTodoRepository;
import com.todoay.api.global.context.LoginAuthContext;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DueDateTodoCRUDServiceImpl implements DueDateTodoCRUDService {
    private final DueDateTodoRepository dueDateTodoRepository;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;

    private final LoginAuthContext loginAuthContext;
    private final HashtagRepository hashtagRepository;


    @Override
    public DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto) {
        Auth auth = authRepository.findByEmail(jwtProvider.getLoginId()).get();
        DueDateTodo dueDateTodo = DueDateTodo.builder()
                .title(dto.getTitle())
                .isPublic(dto.isPublic())
                .isFinished(false)
                .dueDate(dto.getDueDate())
                .description(dto.getDescription())
                .importance(Importance.valueOf(dto.getImportance().toUpperCase()))
                .auth(auth)
                .build();
        dueDateTodo.associateWithHashtag(getHashtagsByHashtagNames(dto.getHashtagNames()));
        dueDateTodoRepository.save(dueDateTodo);
        return DueDateTodoSaveResponseDto.builder().id(dueDateTodo.getId()).build();
    }

    @Override
    @Transactional
    public void modifyDueDateTodo(Long id, DueDateTodoModifyRequestDto dto) {
        DueDateTodo dueDateTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dueDateTodo.modify(dto.getTitle(), dto.isPublic(),dto.isFinished(), dto.getDueDate(), dto.getDescription(),Importance.valueOf(dto.getImportance().toUpperCase()));
        dueDateTodo.associateWithHashtag(getHashtagsByHashtagNames(dto.getHashtagNames()));
    }

    @Transactional
    public void deleteDueDateTodo(Long id) {
        DueDateTodo dueDateTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dueDateTodoRepository.delete(dueDateTodo);
    }

    private DueDateTodo checkIsPresentAndIsMineAndGetTodo(Long id) {
        DueDateTodo dueDateTodo = checkIsPresentAndGetTodo(id);
        checkIsMine(dueDateTodo);
        return dueDateTodo;
    }

    private void checkIsMine(DueDateTodo dueDateTodo) {
        if(!dueDateTodo.getAuth().equals(loginAuthContext.getLoginAuth()))throw new NotYourTodoException();
    }

    private DueDateTodo checkIsPresentAndGetTodo(Long id) {
        return dueDateTodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }

    private List<Hashtag> getHashtagsByHashtagNames(List<HashtagInfoDto> hashtagNames) {
        List<Hashtag> tags = new ArrayList<>(); // dailyTodo로 전달할 list객체, 연관관계 메서드를 개별 Hashtag로 변경하면
        hashtagNames.forEach(n -> {
            String name = n.getName();
            hashtagRepository.findByName(name)
                    .ifPresentOrElse(tags::add, // 검색 결과가 존재한다면 연관 관계를 맺을 리스트에 추가
                            () ->{
                                Hashtag save = hashtagRepository.save(new Hashtag(n.getName())); // 없다면 DB에 저장하고 저장.
                                tags.add(save);
                            });
        });
        return tags;
    }
}
