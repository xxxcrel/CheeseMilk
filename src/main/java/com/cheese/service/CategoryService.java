package com.cheese.service;

import com.cheese.model.entity.Category;
import com.cheese.model.vo.CategoryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<Category> listCategories(Pageable pageable);

    void addCategory(CategoryVO categoryVO);


}
