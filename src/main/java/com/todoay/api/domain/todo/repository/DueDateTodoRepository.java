package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DueDateTodoRepository extends JpaRepository<DueDateTodo, Long> {

    @Query("select distinct t from DueDateTodo t left join fetch t.todoHashtags th" +
            " left join fetch th.hashTag h " +
            "join fetch t.auth a" +
            " where t.auth = :auth and t.isFinished = false")
    List<DueDateTodo> findNotFinishedDueDateTodoByAuth(@Param("auth") Auth auth);

    @Query("select distinct t from DueDateTodo t left join fetch t.todoHashtags th " +
            "left join fetch th.hashTag h " +
            "join fetch t.auth a " +
            "where t.auth = :auth and t.isFinished = true ")
    List<DueDateTodo> findFinishedDueDateTodoByAuth(@Param("auth")Auth auth);
}
