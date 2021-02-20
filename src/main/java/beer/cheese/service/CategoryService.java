package beer.cheese.service;

import beer.cheese.repository.CategoryRepository;
import beer.cheese.model.entity.Category;
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

    }
}
