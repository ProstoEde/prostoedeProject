package com.prostoede.dao.repository;

import com.prostoede.dao.mapper.UsersDaoMapper;
import com.prostoede.entity.Base;
import com.prostoede.entity.Users;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author prostoede
 */
@Repository
@Slf4j
public class UsersRepository {
    
    @Autowired
    private UsersDaoMapper usersDaoMapper;
    
    public Users getUsers(Integer id){
    
	List<Users> list = usersDaoMapper.selectUsers(id);
	log.info("Select user : " + list.get(0).getName());
	return CollectionUtils.isEmpty(list) ? null : list.get(0);
	
    }
    
    public Users getUsersByName(String name){
    
	List<Users> list = usersDaoMapper.selectByName(name);
	log.info("Select user :" + list.get(0).getName());
	return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
    
    @Transactional
    public Users insert(Base test){
    
	log.debug("Insert user :" + test.getName());
	return usersDaoMapper.insert(test);
	
    }
    
    public Long getCount(){
	
	return usersDaoMapper.count();
    }
    
    
}
