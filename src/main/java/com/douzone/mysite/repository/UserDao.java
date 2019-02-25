package com.douzone.mysite.repository;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserDaoException;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao 
{
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public String getPassword(long userNo)
	{
		return sqlSession.selectOne("user.getPassword", userNo);
	}
	public UserVo get(String email)
	{
		return sqlSession.selectOne("user.getByEmail", email);
	}
	
	public int update(UserVo vo)
	{
		return sqlSession.update("user.updateUserInfo", vo);
	}
	
	public int insert(UserVo vo) throws UserDaoException
	{
		return sqlSession.insert("user.insert", vo); // 네임스페이스의 아이디로 statement를 줌
		
	}
	
	public UserVo get(String email, String password)
	{
		Map<String, String> map = new HashMap<>(); // 별로 좋지않은 방법 vo를 사용하는게 베스트
		map.put("email", email);
		map.put("password", password);
		
		System.out.println("email : " + email);
		System.out.println("password : " + password);
		return sqlSession.selectOne("user.getByEmailAndPassword", map); // 여기서 2개가나오면 에러가남
	}
}	
