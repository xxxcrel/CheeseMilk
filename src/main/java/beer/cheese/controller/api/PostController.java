package beer.cheese.controller.api;

import beer.cheese.model.dto.CommentDTO;
import beer.cheese.model.User;
import beer.cheese.security.CurrentUser;
import beer.cheese.service.CommentService;
import beer.cheese.service.PostService;
import beer.cheese.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        postService.starPost(user, postID);
    }

    @DeleteMapping(value = "/posts/{postID}/stars")
    public void unstarPost(@CurrentUser User user, @PathVariable Long postID){
        postService.unstarPost(user, postID);
    }
    @DeleteMapping("/posts/{postID}")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> removeBubble(@CurrentUser User user, @PathVariable Long postID) {

        postService.removeBubble(user, postID);

        return Result.ok("remove post" + postID + " successful");
    }
}
