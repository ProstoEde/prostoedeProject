package com.prostoede.dao;

import com.prostoede.config.TestConfig;
import com.prostoede.dao.repository.UsersRepository;
import com.prostoede.entity.Base;
import com.prostoede.entity.Users;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertNotNull;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author prostoede
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
@Transactional
public class UsersDaoTest {
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Test
    public void getUserTest(){
    
	Base base = new Base();
	base.setName("User1");
	base.setAge(30);	
	Users user = usersRepository.insert(base);	
	assertNotNull(user);
	
	Users user2 = usersRepository.getUsersByName("user2");
	assertEquals("user2", user2.getName());
	
    }
    
}
