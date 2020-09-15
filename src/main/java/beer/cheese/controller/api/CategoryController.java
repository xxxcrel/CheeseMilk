package beer.cheese.controller.api;


import beer.cheese.model.entity.Category;
import beer.cheese.service.CategoryService;
import beer.cheese.service.CommentService;
import beer.cheese.service.PostService;
import beer.cheese.view.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/categories")
public class CategoryController {

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

    @GetMapping(value = "/{category}/posts", params = {"page", "size"})
    public Page<PostVO> listPosts(@PathVariable String category, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return postService.listPostsByCategory(category, pageable);
    }

    @GetMapping(value = "/{category}/posts", params = {"before"})
    public Page<PostVO> listPosts(@PathVariable String category,
                                   @RequestParam("before")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
                                   @PageableDefault(size = 5)Pageable pageable){
        return postService.listPostsByCategory(category, before, pageable);
    }
}
