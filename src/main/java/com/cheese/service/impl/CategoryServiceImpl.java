package com.cheese.service.impl;

import com.cheese.model.entity.Category;
import com.cheese.model.vo.CategoryVO;
import com.cheese.database.repository.CategoryRepository;
import com.cheese.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> listCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public void addCategory(CategoryVO categoryVO) {

    }
}
