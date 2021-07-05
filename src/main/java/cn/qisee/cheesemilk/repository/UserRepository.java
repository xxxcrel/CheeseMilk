package cn.qisee.cheesemilk.repository;

import cn.qisee.cheesemilk.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    //user profile update operation
    @Modifying
    @Query("update User u set u.nickname = :nickname where u.id = :uid")
    int updateNickname(@Param("uid") Long uid, @Param("nickname") String nickname);

    @Modifying
    @Query("update User u set u.location = :location where u.id = :uid")
    int updateLocation(@Param("uid") Long uid, @Param("location") String location);

    @Modifying
    @Query("update User u set u.birth = :birth where u.id = :uid")
    int updateBirth(@Param("uid") Long uid, @Param("birth") Date birth);

    @Modifying
    @Query("update User u set u.bio = :bio where u.id = :uid")
    int updateBio(@Param("uid") Long uid, @Param("bio") String bio);

    @Modifying
    @Query("update User u set u.gender = :gender where u.id = :uid")
    int updateGender(@Param("uid") Long uid, @Param("gender") Integer gender);

    @Modifying
    @Query("update User u set u.avatarUrl = :avatarUrl where u.id = :uid")
    int updateAvatarUrl(@Param("uid") Long uid, @Param("avatarUrl")String avatarUrl);

}
