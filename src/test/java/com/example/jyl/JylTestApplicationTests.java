package com.example.jyl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.jyl.dao.User;
import com.example.jyl.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JylTestApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	
/*    @Before public void initialize() {
       empty= new ArrayList();
    }*/
    
	
	@Test
	public void test(){
		  UserService userService=new UserService();
	       EntityWrapper ew=new EntityWrapper();
	       ew.setEntity(new User());
	       String name="wang";
	       Integer age=30;
	       ew.where("name = {0}",name).orderBy("id");
	       List<User> list = userService.selectList(ew);
	       System.out.println(list);
	       /*Page page2 = userService.selectPage(page, ew);*/
	    }
}
