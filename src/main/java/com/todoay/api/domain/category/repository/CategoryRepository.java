package com.todoay.api.domain.category.repository;

import com.todoay.api.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c inner join c.auth a on a.email = :email")
    List<Category> findCategoryByAuthEmail(@Param("email") String email);
}
