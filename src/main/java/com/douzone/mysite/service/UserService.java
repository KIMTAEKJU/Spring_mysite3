package com.douzone.mysite.service;

import javax.validation.Valid;

import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService 
{
	@Autowired
	private UserDao userDao;
	
	public void join(UserVo userVo) // 조인이라는 비즈니스  dao에는 비즈니스가 들어가면 안되니까
	{
		// 1. DB에 가입 회원 정보 Insert 하기
		userDao.insert(userVo);
		
		// 2. email 주소 확인하는 메일 보내기
	}
	
	@RequestMapping("asdsa")
	public String formValidationCheck(@Valid UserVo userVo, BindingResult result, Model model)
	{
		if( result.hasErrors())
		{
			model.addAllAttributes(result.getModel());
			return "/user/login";
		}
		
		return null;
	}
	
	public UserVo login(UserVo userVo)
	{
		if( userVo.getEmail().equals("") || userVo.getPassword().equals(""))
			return null;
		return userDao.get(userVo.getEmail(), userVo.getPassword());
	}
	
	public void modify(UserVo userVo)
	{
		System.out.println(userDao.update(userVo));
	}
	
	public boolean existEmail(String email)
	{
		UserVo userVo = userDao.get(email);
		return userVo != null;
	}
	
}
