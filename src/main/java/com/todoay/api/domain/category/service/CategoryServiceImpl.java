package com.todoay.api.domain.category.service;

import com.todoay.api.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

}
