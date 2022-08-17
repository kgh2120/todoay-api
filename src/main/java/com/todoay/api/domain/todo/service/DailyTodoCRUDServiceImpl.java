package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.domain.todo.dto.*;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.entity.TodoHashtag;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DailyTodoRepository;
import com.todoay.api.domain.todo.utility.EnumTransfomer;
import com.todoay.api.domain.todo.utility.HashtagAttacher;
import com.todoay.api.domain.todo.utility.repeat.Duration;
import com.todoay.api.domain.todo.utility.repeat.RepeatType;
import com.todoay.api.global.context.LoginAuthContext;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyTodoCRUDServiceImpl implements DailyTodoCRUDService{

    private final DailyTodoRepository dailyTodoRepository;
    private final CategoryRepository categoryRepository;
    private final AuthRepository authRepository;

    private final HashtagRepository hashtagRepository;
    private final JwtProvider jwtProvider;

    private final LoginAuthContext loginAuthContext;

    @Override
    @Transactional
    public DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto) {
        Auth auth = authRepository.findByEmail(jwtProvider.getLoginId()).orElseThrow(EmailNotFoundException::new);
        DailyTodo dailyTodo = DailyTodo.builder()
                .title(dto.getTitle())
                .isPublic(dto.isPublic())
                .isFinished(false)
                .description(dto.getDescription())
                .targetTime(dto.getTargetTime())
                .alarm(dto.getAlarm())
                .place(dto.getPlace())
                .people(dto.getPeople())
                .dailyDate(dto.getDailyDate())
                .category(checkIsPresentAndIsMineGetCategory(dto.getCategoryId()))
                .auth(auth)   // auth??
                .build();
        HashtagAttacher.attachHashtag(dailyTodo, dto.getHashtagNames(), hashtagRepository);
       dailyTodoRepository.save(dailyTodo);

        return DailyTodoSaveResponseDto.builder().id(dailyTodo.getId()).build();
    }



    @Override
    @Transactional
    public void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto) {
       DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
       dailyTodo.modify(dto.getTitle(), dto.isPublic(), dto.isFinished(), dto.getDescription(),
               dto.getTargetTime(), dto.getAlarm(), dto.getPlace(), dto.getPeople(), dto.getDailyDate(), checkIsPresentAndIsMineGetCategory(dto.getCategoryId()));
        HashtagAttacher.attachHashtag(dailyTodo, dto.getHashtagNames(), hashtagRepository);
    }

    @Override
    @Transactional
    public void deleteDailyTodo(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dailyTodoRepository.delete(dailyTodo);
    }

    @Override
    public List<DailyTodoReadResponseDto> readDailyTodosByDate(LocalDate date) {
        Auth loginedAuth = loginAuthContext.getLoginAuth();
        List<DailyTodo> dailyTodos = dailyTodoRepository.findDailyTodoOfUserByDate(date, loginedAuth);
        return dailyTodos.stream()
                .map(DailyTodoReadResponseDto::createReadResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DailyTodoReadResponseDto readDailyTodoById(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        return DailyTodoReadResponseDto.createReadResponseDto(dailyTodo);
    }

    @Override
    public void repeatDailyTodo(Long id, DailyTodoRepeatRequestDto dto) {
        DailyTodo source = checkIsPresentAndIsMineAndGetTodo(id);
        List<LocalDate> repeatedDate = getRepeatedDate(dto,source.getDailyDate());
        createDailyTodoByRepeatedDate(source,repeatedDate);
    }

    private void createDailyTodoByRepeatedDate(DailyTodo source, List<LocalDate> repeatedDate) {
        repeatedDate.forEach(d ->{
            DailyTodo repeated = (DailyTodo) source.clone();
            repeated.changeDateForRepeat(d);
            List<Hashtag> hashtags = source.getTodoHashtags().stream().map(TodoHashtag::getHashTag)
                    .collect(Collectors.toList());
            repeated.associateWithHashtag(hashtags);
            dailyTodoRepository.save(repeated);
        });
    }

    private List<LocalDate> getRepeatedDate(DailyTodoRepeatRequestDto dto, LocalDate targetDate) {
        RepeatType repeatType = (RepeatType) EnumTransfomer.valueOfEnum(RepeatType.class, dto.getRepeatType());
        Duration duration = (Duration) EnumTransfomer.valueOfEnum(Duration.class, dto.getDuration());
        return duration.select(repeatType.getDateRepeator(), dto.getRepeat(), targetDate);
    }



    private DailyTodo checkIsPresentAndIsMineAndGetTodo(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndGetTodo(id);
        checkIsMine(dailyTodo);
        return dailyTodo;
    }

    private void checkIsMine(DailyTodo dailyTodo) {
        if(!dailyTodo.getAuth().equals(loginAuthContext.getLoginAuth())) throw new NotYourTodoException();
    }

    private DailyTodo checkIsPresentAndGetTodo(Long id) {
        return dailyTodoRepository.findDailyTodoById(id).orElseThrow(TodoNotFoundException::new);
    }

    private Category checkIsPresentAndIsMineGetCategory(Long id) {
        Category category = checkIsPresentAndGetCategory(id);
        checkThisCategoryIsMine(category);
        return category;
    }

    private void checkThisCategoryIsMine(Category category) {
        if(!category.getAuth().equals(loginAuthContext.getLoginAuth()))
            throw new NotYourCategoryException();
    }

    private Category checkIsPresentAndGetCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }
}

