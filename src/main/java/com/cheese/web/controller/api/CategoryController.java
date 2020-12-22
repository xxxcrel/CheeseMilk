package com.cheese.web.controller.api;

import com.cheese.exception.NotFoundException;
import com.cheese.model.entity.Category;
import com.cheese.model.entity.Comment;
import com.cheese.model.vo.CommentVO;
import com.cheese.model.vo.PostVO;
import com.cheese.service.CategoryService;
import com.cheese.service.CommentService;
import com.cheese.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /**
     *
     * @param category
     * @param pageable
     * @return Page<PostVO>
     * @throws NotFoundException category must be existed
     */
    @GetMapping("/{category}/posts")
    public Page<PostVO> listPosts(@PathVariable String category, @PageableDefault(sort = "createdAt") Pageable pageable){
        return postService.listPostsByCategory(category, pageable);
    }

}
