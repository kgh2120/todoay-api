package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.domain.todo.dto.daily.*;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.entity.RepeatGroup;
import com.todoay.api.domain.todo.entity.TodoHashtag;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DailyTodoRepository;
import com.todoay.api.domain.todo.repository.RepeatRepository;
import com.todoay.api.domain.todo.utility.EnumTransformer;
import com.todoay.api.domain.todo.utility.HashtagAttacher;
import com.todoay.api.domain.todo.utility.repeat.Duration;
import com.todoay.api.domain.todo.utility.repeat.RepeatType;
import com.todoay.api.global.context.LoginAuthContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

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

    private final RepeatRepository repeatRepository;

    private final HashtagRepository hashtagRepository;

    private final AuthRepository authRepository;

    private final LoginAuthContext loginAuthContext;

    @Override
    @Transactional
    public DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto) {
        Auth auth = getLoggedInAuth();
        DailyTodo dailyTodo = saveNewDailyTodoEntity(dto, auth);

        return DailyTodoSaveResponseDto.builder().id(dailyTodo.getId()).build();
    }

    @Override
    @Transactional
    public void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dailyTodo.modify(dto, checkIsPresentAndIsMineGetCategory(dto.getCategoryId()));
        attachHashtagToDailyTodo(dailyTodo, dto.getHashtagNames());
    }

    @Override
    public List<DailyTodoReadResponseDto> readDailyTodosByDate(LocalDate date) {
        Auth loggedInAuth = getLoggedInAuth();
        List<DailyTodo> dailyTodos = dailyTodoRepository.findDailyTodoOfUserByDate(date, loggedInAuth);
        return dailyTodos.stream()
                .map(DailyTodoReadResponseDto::createDto)
                .collect(Collectors.toList());
    }

    @Override
    public DailyTodoReadDetailResponseDto readDailyTodoDetailById(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        return DailyTodoReadDetailResponseDto.createReadResponseDto(dailyTodo);
    }

    @Override
    @Transactional
    @Async
    public ListenableFuture<ResponseEntity<Void>> repeatDailyTodo(Long id, DailyTodoRepeatRequestDto dto) {
        DailyTodo origin = checkIsPresentAndIsMineAndGetTodo(id);
        List<LocalDate> repeatedDate = getRepeatedDate(dto,origin.getDailyDate());
        createDailyTodoByRepeatedDate(origin,repeatedDate);
        return new AsyncResult<>(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @Override
    @Transactional
    public void modifyDailyDate(Long id, DailyTodoDailyDateModifyRequestDto dto) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dailyTodo.changeDailyDate(dto.getDailyDate());
    }

    @Transactional
    @Override
    public void deleteAllRepeatedDailyTodo(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        repeatRepository.deleteById(dailyTodo.getRepeatGroup().getId());
    }

    private void createDailyTodoByRepeatedDate(DailyTodo source, List<LocalDate> repeatedDate) {
        RepeatGroup repeatGroup = new RepeatGroup();
        repeatRepository.save(repeatGroup);
        source.enterRepeatGroup(repeatGroup);
        repeatedDate.forEach(d ->{
            DailyTodo repeated = (DailyTodo) source.clone();
            repeated.changeDateForRepeat(d);
            List<Hashtag> hashtags = source.getTodoHashtags().stream().map(TodoHashtag::getHashTag)
                    .collect(Collectors.toList());
            repeated.associateWithHashtag(hashtags);
            repeated.enterRepeatGroup(repeatGroup);
            dailyTodoRepository.save(repeated);
        });
    }

    private List<LocalDate> getRepeatedDate(DailyTodoRepeatRequestDto dto, LocalDate targetDate) {
        RepeatType repeatType = transformUserInputToRepeatTypeEnum(dto);
        Duration duration = transformUserInputToDurationEnum(dto);
        return createRepeatedDateByCondition(dto, targetDate, repeatType, duration);
    }

    private List<LocalDate> createRepeatedDateByCondition(DailyTodoRepeatRequestDto dto, LocalDate targetDate, RepeatType repeatType, Duration duration) {
        return duration.select(repeatType.getDateRepeator(), dto.getRepeat(), targetDate);
    }

    private Duration transformUserInputToDurationEnum(DailyTodoRepeatRequestDto dto) {
        return (Duration) EnumTransformer.valueOfEnum(Duration.class, dto.getDuration());
    }

    private RepeatType transformUserInputToRepeatTypeEnum(DailyTodoRepeatRequestDto dto) {
        return (RepeatType) EnumTransformer.valueOfEnum(RepeatType.class, dto.getRepeatType());
    }


    private DailyTodo checkIsPresentAndIsMineAndGetTodo(Long id) {
        return (DailyTodo) checkIsPresentAndIsMineAndGetTodo(id,loginAuthContext);
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

    @Override
    public DailyTodo checkIsPresentAndGetTodo(Long id) {
        return dailyTodoRepository.findDailyTodoById(id).orElseThrow(TodoNotFoundException::new);
    }

    private Auth getLoggedInAuth() {
        return authRepository.findByEmail(loginAuthContext.getLoginAuth().getEmail())
                .orElseThrow(EmailNotFoundException::new);
    }

    private DailyTodo saveNewDailyTodoEntity(DailyTodoSaveRequestDto dto, Auth auth) {
        DailyTodo dailyTodo = createDailyTodoEntity(dto, auth);
        dailyTodoRepository.save(dailyTodo);
        return dailyTodo;
    }

    private DailyTodo createDailyTodoEntity(DailyTodoSaveRequestDto dto, Auth auth) {
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
                .auth(auth)
                .build();
        attachHashtagToDailyTodo(dailyTodo, dto.getHashtagNames());
        return dailyTodo;
    }

    private void attachHashtagToDailyTodo(DailyTodo dailyTodo, List<HashtagInfoDto> dto) {
        HashtagAttacher.attachHashtag(dailyTodo, dto, hashtagRepository);
    }
}

