package com.todoay.api.domain.category.repository;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findCategoryByAuth_Email(String email);
    List<Category> findCategoryByAuth(Auth auth);
}
