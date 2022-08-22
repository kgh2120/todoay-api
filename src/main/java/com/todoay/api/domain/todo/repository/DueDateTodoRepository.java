package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DueDateTodoRepository extends JpaRepository<DueDateTodo, Long> {

    @Query("select t from DueDateTodo t join fetch t.todoHashtags h where t.auth = :auth")
    List<DueDateTodo> findAllByAuth(@Param("auth") Auth auth);
}
