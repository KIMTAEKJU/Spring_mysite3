package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;

@Repository
public class CommentDao 
{
	@Autowired
	private SqlSession sqlSession;
	
	public int updateONOGreaterThanEqual(CommentVo cVo)
	{
		return sqlSession.update("comment.ONOGreaterThanEqual", cVo);
	}
	
	public List<CommentVo> getMaxONO(long gNo)
	{
		return sqlSession.selectList("comment.replyCommentGetMaxONO", gNo);
	}
	
	public String check(CommentVo vo)
	{
		return sqlSession.selectOne("comment.replyCommentCheck");
	}
	
//	public boolean deleteLoginComment(String userNo, String commentNo)
//	{
//		boolean result = false;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try 
//		{
//			 conn = getConnection();
//			 
//			 String sql = "delete from comment where no = ? and user_no = ?";
//			 
//			 pstmt = conn.prepareCall(sql);
//			 
//			 pstmt.setString(1, commentNo);
//			 pstmt.setString(2, userNo);
//			 
//			 int count = pstmt.executeUpdate();
//			 result = count == 1;
//		} 
//		catch (SQLException e) 
//		{
//			System.out.println("error : " + e);
//		}
//		finally 
//		{
//			try 
//			{
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} 
//			catch (SQLException e) 
//			{
//				e.printStackTrace();
//			}
//		}
//		
//		return result;
//	}
	
	public int delete(CommentVo cVo)
	{
		return sqlSession.delete("comment.deleteComment", cVo);
	}
	
	public int update(CommentVo vo)
	{
		return sqlSession.update("comment.modifyComment", vo);
	}
	
	public List<CommentVo> get(String no)
	{
		return sqlSession.selectList("comment.replyCommentInfo", no);
	}
	
	public List<CommentVo> get(long no)
	{
		return sqlSession.selectList("comment.getCommentList", no);
	}
	
	public boolean insertReplyComment(CommentVo vo)
	{
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try 
		{
			 conn = getConnection();
			 //(select ifnull(max(g_no)+1,1) from comment a)
			 String sql = "insert into comment values(null, ?, now(), ?, ?, ?, ?, ?, ?, ?)";
			 
			 pstmt = conn.prepareCall(sql);
			 
			 System.out.println("vo.getoNo : " + vo.getoNo());
			 
			 pstmt.setString(1, vo.getContents());
			 pstmt.setLong(2, vo.getBoardNo());
			 pstmt.setString(3, vo.getUserNo());
			 pstmt.setString(4, vo.getName());
			 pstmt.setString(5, vo.getPassword());
			 pstmt.setLong(6, vo.getgNo());
			 pstmt.setLong(7, vo.getoNo());
			 pstmt.setLong(8, vo.getDepth());
			 
			 int count = pstmt.executeUpdate();
			 result = count == 1;
		} 
		catch (SQLException e) 
		{
			System.out.println("error : " + e);
		}
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int insert(CommentVo vo)
	{
		return sqlSession.insert("comment.insertComment", vo);
	}
	
	private static Connection getConnection() throws SQLException
	{
		Connection conn = null;
		
		try 
		{
			// 1. JDBC Driver(MySQL) 로딩
			Class.forName("com.mysql.jdbc.Driver"); // 제대로 로딩됐는지 확인
			
			// 2. 연결하기 (Connection 객체 얻어오기)
			String url = "jdbc:mysql://localhost:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("드라이버 로딩 실패 : " + e);
		}
		
		return conn;
	}
}
