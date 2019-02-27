package com.douzone.mysite.service.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service("GuestbookApiService")
public class GuestbookService {

	@Autowired
	private GuestBookDao gDao;
	
	public Map<String, Object> list()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("list", gDao.getList());
		return map;
	}
	
	public long write(GuestBookVo gVo)
	{
		return gDao.insert(gVo);
	}
	
	public int delete(GuestBookVo gVo)
	{
		return gDao.delete(gVo);
	}
	
	public GuestBookVo ajaxSelect(long no)
	{
		return gDao.getSelect(no);
	}
	
	public List<GuestBookVo> getTimelineSelect(String page)
	{
		if( "".equals(page))
		{
			page = "1";
		}
		
		if (page.matches("\\d*") == false)
		{
			page = "1";
		}
		
		long sPage = Long.parseLong(page);
		
		return gDao.getTimelineSelect( (sPage - 1) * 5);
	}
}
