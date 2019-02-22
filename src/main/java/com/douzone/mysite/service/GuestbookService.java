package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestBookDao gDao;
	
	public Map<String, Object> list()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("list", gDao.getList());
		return map;
	}
	
	public void write(GuestBookVo gVo)
	{
		gDao.insert(gVo);
	}
	
	public void delete(GuestBookVo gVo)
	{
		gDao.delete(gVo);
	}
}
