package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.todo.entity.DailyTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyTodoRepository extends JpaRepository<DailyTodo, Long> {
}
