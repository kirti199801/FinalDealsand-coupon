package com.cg.dealscoupan.userservice;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tomcat.jni.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.dealscoupan.userservice.dao.UserProfileRepository;
import com.cg.dealscoupan.userservice.entity.ClientType;
import com.cg.dealscoupan.userservice.service.UserProfileService;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserProfileService service;
	
	@MockBean
	private UserProfileRepository repository;
	
	
	public void getUsersTest() {
		//when(repository.findAll()),thenReturn(Stream.of(new User()).collect(Collectors.toList()))
		
	}


	private void thenReturn(List<User> collect) {
		// TODO Auto-generated method stub
		
	}


	
}
