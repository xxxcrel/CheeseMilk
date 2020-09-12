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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "/posts/{postID}/comments/", params = {"parent_id"})
    @ResponseStatus(HttpStatus.OK)
    public Result<String> postComment(@CurrentUser User user,
                                      @PathVariable long postID,
                                      @RequestPart("meta-data") CommentDTO commentDTO) {

        commentService.addComment(user, postID, commentDTO);

        return Result.ok("comment successful");
    }

    @GetMapping(value = "/posts/{postID}/comments", produces = MediaType.APPLICATION_JSON_VALUE, params = {"level", "commented_id"})
    public Page<CommentVO> getBubbleCommentList(@PathVariable Long postID,
                                                @RequestParam(name = "level", defaultValue = "first") String level,
                                                @RequestParam(name = "commented_id", required = false) Long parentId,
                                                @PageableDefault Pageable pageable) {

        if (level.equals("first")) {
            //if it's first level comment, ignore parent_id, search by post id
            return commentService.listCommentsByPost(postID, pageable);
        }
        else if(level.equals("second") && parentId != null){
            return commentService.listCommentsByParent(parentId, pageable);
        }
        throw new InvalidParameterException("invalid parameter: level=" + level + "&parent_id=" + (parentId == null ? "null" : parentId));
    }

    @DeleteMapping("/posts/{postID}")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> removeBubble(@CurrentUser User user, @PathVariable Long postID) {
        postService.removeBubble(user, postID);

        return Result.ok("remove post" + postID + " successful");
    }
}
