package beer.cheese.controller.api;


import beer.cheese.exception.InvalidParameterException;
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

    private static final String DEFAULT_AFTER_DATE_TIME = "1970-01-01T00:00:00.000";

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

    @GetMapping(value = "/{category}/posts")
    public Page<PostVO> listPosts(@PathVariable String category,
                                  @RequestParam(value = "before", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
                                  @RequestParam(value = "after", required = false, defaultValue = DEFAULT_AFTER_DATE_TIME)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after,
                                   @PageableDefault(size = 5)Pageable pageable){
        if(before == null)
            before = LocalDateTime.now();
        else if(after != null){
            if(!before.isAfter(after))
                throw new InvalidParameterException("before time must be great than after, but before time: [" + before.toString() + "], after time: [" + after.toString() + "]");
        }
        return postService.listPostsByCategory(category, before, after, pageable);
    }
}
