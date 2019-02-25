package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao 
{
	@Autowired
	private SqlSession sqlSession;
	
	public List<SiteVo> getSite()
	{
		return sqlSession.selectList("site.getSite");
	}
	
	public void mainPageUpdate(SiteVo vo)
	{
		sqlSession.update("site.mainPageUpdate", vo);
	}
}
