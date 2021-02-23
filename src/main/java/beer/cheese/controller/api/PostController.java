package beer.cheese.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import beer.cheese.model.User;
import beer.cheese.model.dto.CommentDTO;
import beer.cheese.security.CurrentUser;
import beer.cheese.service.CommentService;
import beer.cheese.service.PostService;
import beer.cheese.view.Result;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "/user/posts")
    public Result publishPost(@RequestParam("category") String category,
                              @RequestPart("meta-data") Map<String, String> data,
                              @RequestPart("images[]") List<MultipartFile> images) {

    }


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
    public void starPost(@CurrentUser User user, @PathVariable Long postID) {
        postService.starPost(user, postID);
    }

    @DeleteMapping(value = "/posts/{postID}/stars")
    public void unstarPost(@CurrentUser User user, @PathVariable Long postID) {
        postService.unstarPost(user, postID);
    }

    @DeleteMapping("/posts/{postID}")
    @ResponseStatus(HttpStatus.OK)
    public Result<String> removeBubble(@CurrentUser User user, @PathVariable Long postID) {

        postService.removeBubble(user, postID);

        return Result.ok("remove post" + postID + " successful");
    }
}
