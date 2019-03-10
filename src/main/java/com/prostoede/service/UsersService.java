package com.prostoede.service;

import com.prostoede.dao.repository.UsersRepository;
import com.prostoede.entity.Base;
import com.prostoede.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author prostoede
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UsersService {
    
    private final UsersRepository usersRepository;
    
    public Users getUsers(Base test){
    
	return usersRepository.getUsers(test.getId());
	
    }
    
    public Users insert(Base test){
    
	return usersRepository.insert(test);
	
    }
    
    public Long getCount(){
    
	return usersRepository.getCount();
    }
    
}
