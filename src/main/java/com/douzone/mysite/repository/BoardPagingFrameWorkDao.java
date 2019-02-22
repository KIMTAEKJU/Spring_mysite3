package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardPagingFrameWorkVo;
import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardPagingFrameWorkDao
{
	@Autowired
	private SqlSession sqlSession;

	public long getTotalCount(String kwd)
	{
		return sqlSession.selectOne("board.getTotalCount", kwd);
	}
}
