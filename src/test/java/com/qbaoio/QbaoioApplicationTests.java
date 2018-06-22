package com.qbaoio;

import com.qbaoio.qbaoio.entity.user.User;
import com.qbaoio.qbaoio.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QbaoioApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setActivateType(0);
		user.setEmail("123456@qq.com");
		user.setPassWord("000000");
		user.setUserName("gfy");
		user.setPhone("99999999");

		userService.saveUser(user);
	}

}
