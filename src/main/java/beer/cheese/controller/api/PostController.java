package beer.cheese.controller.api;

import beer.cheese.exception.InvalidParameterException;
import beer.cheese.model.dto.CommentDTO;
import beer.cheese.model.entity.User;
import beer.cheese.security.CurrentUser;
import beer.cheese.service.CommentService;
import beer.cheese.service.PostService;
import beer.cheese.view.Result;
import beer.cheese.view.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static beer.cheese.controller.api.CategoryController.DEFAULT_AFTER_DATE_TIME;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "/posts/{postID}/comments/")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> addComment(@CurrentUser User user,
                                      @PathVariable long postID,
                                      @RequestPart("meta-data") CommentDTO commentDTO) {

        commentService.addComment(user, postID, commentDTO);

        return Result.ok("comment successful");
    }

    @GetMapping(value = "/posts/{postID}/stars")
    @ResponseStatus(HttpStatus.OK)
    public void starPost(@CurrentUser User user, @PathVariable Long postID){
        postService.giveAStar(user, postID);
    }

    @DeleteMapping("/posts/{postID}")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> removeBubble(@CurrentUser User user, @PathVariable Long postID) {

        postService.removeBubble(user, postID);

        return Result.ok("remove post" + postID + " successful");
    }
}
