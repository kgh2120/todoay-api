package com.todoay.api.domain.category.service;

import com.todoay.api.domain.category.dto.*;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.global.context.LoginAuthContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryCRUDServiceImpl implements CategoryCRUDService {
    private final CategoryRepository categoryRepository;

    private final LoginAuthContext loginAuthContext;

    @Override
    public CategorySaveResponseDto addCategory(CategorySaveRequestDto dto) {
        log.info("Category-addCategory - Auth = {}",loginAuthContext.getLoginAuth());
        return new CategorySaveResponseDto(categoryRepository.save(new Category(dto.getName(), dto.getColor(), dto.getOrderIndex(), loginAuthContext.getLoginAuth())).getId());
    }

    @Override
    public void modifyCategory(Long id, CategoryModifyRequestDto dto) {
        checkIsPresentAndIsMineAndGetCategory(id).modify(dto.getName(), dto.getColor());
    }

    @Override
    public CategoryListByTokenResponseDto findCategoryByToken() {
        return CategoryListByTokenResponseDto.of(categoryRepository.findCategoryByAuth(loginAuthContext.getLoginAuth()));
    }

    @Override
    public void modifyOrderIndexes(CategoryOrderIndexModifyDto dto) {
        dto.getOrderIndexes().forEach(i -> checkIsPresentAndIsMineAndGetCategory(i.getId()).changeOrderIndex(i.getOrderIndex()));
    }

    @Override
    public void removeCategory(Long id) {
        categoryRepository.delete(checkIsPresentAndIsMineAndGetCategory(id));
    }

    @Override
    public void endCategory(Long id) {
        checkIsPresentAndIsMineAndGetCategory(id).end();
    }

    private Category checkIsPresentAndIsMineAndGetCategory(Long id) {
        Category category = checkIsPresentAndGetCategory(id);
        checkIsMine(category);
        return category;
    }

    private void checkIsMine(Category category) {
        if(!category.getAuth().equals(loginAuthContext.getLoginAuth())) throw new NotYourCategoryException();
    }

    private Category checkIsPresentAndGetCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }
}
