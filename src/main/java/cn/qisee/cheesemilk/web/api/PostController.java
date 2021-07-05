package cn.qisee.cheesemilk.web.api;

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

import cn.qisee.cheesemilk.entity.User;
import beer.cheese.entity.dto.CommentDTO;
import cn.qisee.cheesemilk.security.CurrentUser;
import cn.qisee.cheesemilk.service.CommentService;
import cn.qisee.cheesemilk.service.PostService;
import cn.qisee.cheesemilk.web.response.Result;

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
