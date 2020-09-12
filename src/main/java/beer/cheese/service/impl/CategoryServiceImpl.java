package beer.cheese.service.impl;

import beer.cheese.repository.CategoryRepository;
import beer.cheese.model.entity.Category;
import beer.cheese.view.vo.CategoryVO;
import beer.cheese.service.CategoryService;
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
