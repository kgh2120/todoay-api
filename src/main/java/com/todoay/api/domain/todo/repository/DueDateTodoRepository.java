package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.todo.entity.DueDateTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DueDateTodoRepository extends JpaRepository<DueDateTodo, Long> {
}
