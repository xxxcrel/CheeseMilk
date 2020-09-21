package beer.cheese.service.impl;

import beer.cheese.constant.AppConstants;
import static beer.cheese.controller.api.MultiDataQueryController.DateTuple;

import beer.cheese.model.entity.*;
import beer.cheese.repository.CategoryRepository;
import beer.cheese.repository.PostRepository;
import beer.cheese.repository.StarRepository;
import beer.cheese.repository.UserRepository;
import beer.cheese.exception.NotFoundException;
import beer.cheese.model.dto.PostDTO;
import beer.cheese.security.acl.AclDTO;
import beer.cheese.security.acl.AclManager;
import beer.cheese.service.FileService;
import beer.cheese.service.PostService;
import beer.cheese.view.vo.PostVO;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

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
    private StarRepository starRepository;

    @Autowired
    private AclManager aclManager;

    @Autowired
    @Qualifier("jdkFileService")
    private FileService fileService;


    /**
     * 用来转换Post->PostVO
     */
    private static class CustomPostCopy {

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
    public Page<PostVO> listPostsByUser(User currentUser, DateTuple queryPeriod, Pageable pageable) {
        Page<Post> rawPostList = postRepository.getAllByUserAndCreatedAtBetween(currentUser, queryPeriod.start, queryPeriod.end, pageable);
        return rawPostList.map(CustomPostCopy::apply);
    }

    /************ doesn't need authentication *****************/


    @Override
    @Transactional
    public Page<PostVO> listPostsByUsername(String username, DateTuple queryPeriod, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("user: " + username + " not found"));

        return listPostsByUser(user, queryPeriod, pageable);
    }


    @Override
    public PostVO getPostByPid(Long pid) throws NotFoundException {
        Post post = postRepository.findById(pid).orElseThrow(() -> new NotFoundException("post: " + pid + " not found"));
        return CustomPostCopy.apply(post);
    }


    @Override
    @Transactional
    public Page<PostVO> listPostsByCategory(String _category, DateTuple queryPeriod, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(_category).orElseThrow(() -> new NotFoundException("category: " + _category + " not found"));
        return postRepository.getAllByCategoryAndCreatedAtBetween(category, queryPeriod.start, queryPeriod.end, pageable).map(CustomPostCopy::apply);
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

    @Override
    @Transactional
    public void giveAStar(User currentUser, Long postId) {

        Star star = new Star();
        star.setStarPK(new Star.StarPK(currentUser, postId, Star.ResourceType.COMMENT.ordinal()));
        starRepository.save(star);
        postRepository.updateStars(postId, 1);
    }

    private Set<Image> url2Image(List<String> filenames) {
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
