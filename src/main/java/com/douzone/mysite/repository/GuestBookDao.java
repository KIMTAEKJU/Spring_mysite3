package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao 
{
	@Autowired
	private SqlSession sqlSession;
	
	public long insert(GuestBookVo vo)
	{
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println("count : " + count);
		long no = vo.getNo();
		System.out.println("no : " + no);
		return no;
	}
	
	public int delete(GuestBookVo vo)
	{
		return sqlSession.delete("guestbook.delete", vo);
	}
	
	
	public List<GuestBookVo> getList()
	{
		return sqlSession.selectList("guestbook.getList");
	}
	
	public GuestBookVo getSelect(long no)
	{
		return sqlSession.selectOne("guestbook.getSelect", no);
	}
	
	public List<GuestBookVo> getTimelineSelect(long page)
	{
		return sqlSession.selectList("guestbook.getTimelineSelect", page);
	}
}