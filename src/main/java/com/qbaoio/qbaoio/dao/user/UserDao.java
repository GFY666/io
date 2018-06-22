package com.qbaoio.qbaoio.dao.user;

import com.qbaoio.qbaoio.entity.user.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

    List<User> findByUserName(String userName);

    List<User> findByEmail(String email);

    @Query("select u from User u where u.userName=:userName and u.passWord=:password")
    User findByUserNameAndPassWord(@Param("userName") String userName, @Param("password") String password);

    @Query("select u from User u where u.email=:email and u.passWord=:password")
    User findByEmailAndPassWord(@Param("email") String userName, @Param("password") String password);


    List<User> findAll(Specification<User> specification);
}
