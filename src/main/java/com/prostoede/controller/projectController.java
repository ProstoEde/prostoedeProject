package com.prostoede.controller;

import com.prostoede.entity.Base;
import com.prostoede.entity.Users;
import com.prostoede.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author prostoede
 */
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class projectController {
    
    private final UsersService usersService;
        
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public @ResponseBody Users getUsers(@RequestBody Base test){
    
	return usersService.getUsers(test);
	
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody Users insert(@RequestBody Base test){
    
	return usersService.insert(test);
	
    }
    
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public @ResponseBody Long getCount(){
    
	return usersService.getCount();
    }
    
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView getStart(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    
    @RequestMapping(value = "/test1")
    public String test(){
	return "test";
    }
    
}
