package com.renren.infra.xweb.data;

import org.springside.modules.test.data.RandomData;

import com.renren.infra.xweb.entity.User;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class UserData {

	public static User randomUser() {
		String userName = RandomData.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPlainPassword("123456");
		user.setEmail(userName + "@renren-inc.com");

		return user;
	}

	/*public static User randomUserWithAdminRole() {
		User user = UserData.randomUser();
		Role adminRole = UserData.adminRole();
		user.getRoleList().add(adminRole);
		return user;
	}

	public static Role adminRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		return role;
	}*/
}
