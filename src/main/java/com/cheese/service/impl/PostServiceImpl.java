package com.cheese.service.impl;

import com.cheese.constant.AppConstants;
import com.cheese.model.dto.PostDTO;
import com.cheese.model.entity.Image;
import com.cheese.model.entity.Post;
import com.cheese.model.entity.Category;
import com.cheese.model.entity.User;
import com.cheese.database.repository.CategoryRepository;
import com.cheese.database.repository.PostRepository;
import com.cheese.database.repository.UserRepository;
import com.cheese.security.acl.AclDTO;
import com.cheese.security.acl.AclManager;
import com.cheese.service.FileService;
import com.cheese.service.PostService;
import com.cheese.model.vo.PostVO;
import com.cheese.exception.NotFoundException;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private final Log logger = LogFactory.getLog(getClass());
    public static final String[] POSTVO_TO_POST_IGNORE = new String[]{"id", "likeCount", "commentCount", "createdAt",};
    public static final String[] POST_TO_POSTVO_IGNORE = new String[]{""};

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AclManager aclManager;

    @Autowired
    @Qualifier("jdkFileService")
    private FileService fileService;


    /**
     * 用来转换Post->PostVO
     */
    private static class CustomPostCopy{

        public static PostVO apply(Post post) {
            PostVO postVO = new PostVO();
            BeanUtils.copyProperties(post, postVO, POST_TO_POSTVO_IGNORE);
            postVO.setNickname(post.getUser().getNickname());
            postVO.setAvatarUrl(post.getUser().getAvatarUrl());
            postVO.setCategoryName(post.getCategory().getCategoryName());
            postVO.setImageUrls(post.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList()));
            postVO.setCommentUrl(AppConstants.API_BASE_URL + "posts/" + post.getId() + "/comments");
            return postVO;
        }
    }


    @Override
    @Transactional
    public Page<PostVO> listPostsByCurrentUser(User currentUser, Pageable pageable) {
        Page<Post> rawPostList = postRepository.getAllByUser(currentUser, pageable);
        return rawPostList.map(CustomPostCopy::apply);
    }

    /************ doesn't need authentication *****************/


    @Override
    @Transactional
    public Page<PostVO> listPostsByUsername(String username, Pageable pageable){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("user: " + username + " not found"));

        return postRepository.getAllByUser(user, pageable).map(CustomPostCopy::apply);
    }

    @Override
    @Transactional
    public Page<PostVO> listPostsByCategory(String category_, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(category_).orElseThrow(()->new NotFoundException("category: " + category_ + " not exists"));
        return  postRepository.getAllByCategory(category, pageable).map(CustomPostCopy::apply);
    }

    @Override
    public PostVO getPostByPid(Long pid) throws NotFoundException{
        Post post = postRepository.findById(pid).orElseThrow(() -> new NotFoundException("post: " + pid + " not found"));
        return CustomPostCopy.apply(post);
    }



    /*****************  need authentication *******************/

    @Override
    public void postPlainBubble(User currentUser, PostDTO postDTO) {

    }

    @Override
    public void removeBubble(User operateUser, Long postID) {

    }

    @Override
    @Transactional
    public void postFlexBubble(User currentUser, PostDTO postDTO, List<MultipartFile> images) {
        //transfer bubble content to persistent object
        Post po = Post.builder().fromDTO(postDTO);
        po.setUser(currentUser);
        po.setCategory(categoryRepository.findByCategoryName(postDTO.getCategory()).orElseThrow(() -> new NotFoundException("category name not found")));

        //add bubble images
        if (!images.isEmpty()) {
            images.forEach(image -> {
                Assert.hasText(image.getOriginalFilename());
            });
            po.setImages(url2Image(images.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(Collectors.toList())));
            fileService.uploadFiles(AppConstants.BUBBLE_IMAGE_PATH, images.stream().collect(Collectors.toMap(MultipartFile::getOriginalFilename, image -> image)));
        }

        // save
        postRepository.save(po);

        // add permission on post object
        aclManager.addPermission(AclDTO.builder().securedObject(po).role(po.getCategory().getCategoryName()).permission(BasePermission.DELETE).build());

    }

    private Set<Image> url2Image(List<String> filenames){
        HashSet<Image> set = new HashSet<>();
        String prefix = AppConstants.STATIC_SERVER_PREFIX;
        String path = AppConstants.BUBBLE_IMAGE_PATH;
        filenames.forEach(filename -> {
            String imageUrl = prefix.concat(path).concat(filename);
            logger.info("bubble image: " + imageUrl);
            set.add(new Image(imageUrl));
        });
        return set;
    }

}
