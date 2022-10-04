package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.todo.entity.RepeatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatRepository extends JpaRepository<RepeatGroup, Long> {
}
