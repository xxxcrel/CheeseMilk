package beer.cheese.controller.api;


import beer.cheese.model.entity.Category;
import beer.cheese.service.CategoryService;
import beer.cheese.service.CommentService;
import beer.cheese.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    public static final String DEFAULT_AFTER_DATE_TIME = "1970-01-01T00:00:00.000";

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Page<Category> getCategories(@PageableDefault Pageable pageable){
        return categoryService.listCategories(pageable);
    }

}
