package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.todo.entity.DailyTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyTodoRepository extends JpaRepository<DailyTodo, Long> {

    @Query("select distinct t from DailyTodo t " +
            "left join fetch t.todoHashtags th " +
            "left join fetch th.hashTag h " +
            "join fetch t.auth a " +
            "join fetch t.category c " +
            "join fetch a.profile p " +
            "where t.dailyDate =:localDate " +
            "and t.auth =:auth")
    List<DailyTodo> findDailyTodoOfUserByDate(@Param("localDate") LocalDate localDate, @Param("auth") Auth auth);

    @Query("select distinct t from DailyTodo t " +
            "left join fetch t.todoHashtags th " +
            "left join fetch th.hashTag h " +
            "join fetch t.auth a " +
            "join fetch t.category c " +
            "join fetch a.profile p " +
            "where t.id =:id")
    Optional<DailyTodo> findDailyTodoById(@Param("id") Long id);
}
