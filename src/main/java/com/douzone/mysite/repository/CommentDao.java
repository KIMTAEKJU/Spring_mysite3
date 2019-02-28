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
	
	public int insertReplyComment(CommentVo vo)
	{
		return sqlSession.insert("comment.insertReplyComment", vo);
	}
	
	public int insert(CommentVo vo)
	{
		return sqlSession.insert("comment.insertComment", vo);
	}
}
