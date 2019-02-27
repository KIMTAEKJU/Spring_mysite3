package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.SiteDao;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService 
{
	@Autowired
	private SiteDao siteDao;
	
	public void update(SiteVo vo)
	{
		siteDao.mainPageUpdate(vo);
	}
	
	public List<SiteVo> getSite()
	{
		return siteDao.getSite();
	}
}
