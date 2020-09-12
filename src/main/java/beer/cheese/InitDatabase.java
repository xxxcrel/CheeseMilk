package beer.cheese;

import beer.cheese.constant.AppConstants;
import beer.cheese.repository.*;
import beer.cheese.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class InitDatabase {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ManageGroupRepository manageGroupRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        initRole();
        initAdminUser();
        initOfficialCategory();
        insertBubbles();
    }

    private void initRole() {
        Role role = new Role();
        role.setRoleName("SUPER_ADMIN");
        role.setDescription("super admin of system");
        roleRepository.save(role);
    }

    private void initAdminUser() {
        Role role = roleRepository.findByRoleName("SUPER_ADMIN");

        User admin = new User();
        admin.setUsername("xxxcrel");
        admin.setBio("You know nothing");
        admin.setEmail("crelxc@gmail.com");
        admin.setGender(1);
        admin.setCreatedAt(new Date());
        admin.setEnabled(true);
        admin.setLocation("0793");
        admin.setNickname("CrelDevi");
        admin.setPassword(passwordEncoder.encode("xc514xxx"));
        admin.getRoles().add(role);
        User testUser = new User();
        testUser.setUsername("tester");
        testUser.setNickname("Tester");
        testUser.setEmail("test@test.com");
        testUser.setBio("i'm tester");
        testUser.setEnabled(true);
        testUser.setAvatarUrl(AppConstants.STATIC_SERVER_PREFIX + AppConstants.USER_AVATAR_PATH + "avatar.jpg");
        testUser.setPassword(passwordEncoder.encode("tester"));
        testUser.setGender(1);
        userRepository.save(admin);
        userRepository.save(testUser);
    }


    private void initOfficialCategory() {
        User owner = userRepository.findByUsername("xxxcrel").get();
        Category whiteWall = new Category();
        whiteWall.setCategoryName("WhiteWall");
        whiteWall.setDescription("南理表白墙，专注大学生的幸福");
        ManagerGroup whiteWallGroup = new ManagerGroup();
        whiteWallGroup.setCategory(whiteWall);
        whiteWallGroup.setOwner(owner);
        manageGroupRepository.save(whiteWallGroup);


        Category moments = new Category();
        moments.setCategoryName("Moments");
        moments.setDescription("记录生活,忘记你");
        ManagerGroup momentsGroup = new ManagerGroup();
        momentsGroup.setCategory(moments);
        momentsGroup.setOwner(owner);
        manageGroupRepository.save(momentsGroup);

        Category idleFish = new Category();
        idleFish.setCategoryName("IdleFish");
        idleFish.setDescription("南理闲鱼，淘尽二手");
        ManagerGroup idleFishGroup = new ManagerGroup();
        idleFishGroup.setCategory(idleFish);
        idleFishGroup.setOwner(owner);
        manageGroupRepository.save(idleFishGroup);

        Category rentHouse = new Category();
        rentHouse.setCategoryName("RentHouse");
        rentHouse.setDescription("租房，出租，你想要的我都有");
        ManagerGroup rentHouseGroup = new ManagerGroup();
        rentHouseGroup.setCategory(rentHouse);
        rentHouseGroup.setOwner(owner);
        manageGroupRepository.save(rentHouseGroup);

        Category iKnowNothing = new Category();
        iKnowNothing.setCategoryName("IKnowNothing");
        iKnowNothing.setDescription("你不知道的，我们会知道");
        ManagerGroup iKnowNothingGroup = new ManagerGroup();
        iKnowNothingGroup.setCategory(iKnowNothing);
        iKnowNothingGroup.setOwner(owner);
        manageGroupRepository.save(iKnowNothingGroup);

        Category bullshit = new Category();
        bullshit.setCategoryName("Bullshit");
        bullshit.setDescription("离我远点，我只想吐槽(Leave me along, I was just bullshitting");
        ManagerGroup bullshitGroup = new ManagerGroup();
        bullshitGroup.setCategory(bullshit);
        bullshitGroup.setOwner(owner);
        manageGroupRepository.save(bullshitGroup);
    }

    private void initManagerGroup() {

    }

    private void insertBubbles(){
        User user = userRepository.findByUsername("tester").get();
        Category whiteWall = categoryRepository.findByCategoryName("WhiteWall").get();
        for(int i = 0; i < 10; ++i){
            postRepository.save(generatePost(user, whiteWall));
        }
        Category moments = categoryRepository.findByCategoryName("Moments").get();
        for(int i = 0; i < 10; ++i){
            postRepository.save(generatePost(user, moments));
        }
    }

    private Post generatePost(User user, Category category){
        Post post = new Post();
        post.setCategory(category);
        post.setUser(user);
        post.setCreatedAt(new Date());
        post.setTags("life.share");
        post.setContent("hello this is a test bubble");
        post.setStarCount(10);
        post.getImages().add(new Image(AppConstants.STATIC_SERVER_PREFIX + "/images/bubble/daisy.jpg"));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setCreatedAt(new Date());
        comment.setContent("this is is a test review");
        comment.setUser(user);
        post.getComments().add(comment);
        return post;
    }
}