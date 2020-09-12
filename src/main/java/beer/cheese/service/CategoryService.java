package beer.cheese.service;

import beer.cheese.model.entity.Category;
import beer.cheese.view.vo.CategoryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<Category> listCategories(Pageable pageable);

    void addCategory(CategoryVO categoryVO);


}
