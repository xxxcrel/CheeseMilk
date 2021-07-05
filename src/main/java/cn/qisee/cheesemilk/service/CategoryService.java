package cn.qisee.cheesemilk.service;

import cn.qisee.cheesemilk.repository.CategoryRepository;
import cn.qisee.cheesemilk.entity.Category;
import beer.cheese.view.vo.CategoryVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> listCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public void addCategory(CategoryVO categoryVO) {

        BasePermission
    }
}
