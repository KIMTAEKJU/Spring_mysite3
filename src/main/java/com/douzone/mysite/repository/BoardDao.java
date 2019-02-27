package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class BoardDao 
{
	@Autowired
	private SqlSession sqlSession;
	
	public boolean update(long no)
	{
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try 
		{
			 conn = getConnection();
			 
			 String sql = "update board set hit = hit + 1 where no = ?";
			 
			 pstmt = conn.prepareCall(sql);
			 
			 pstmt.setLong(1, no);
			 
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
	
	public int update(BoardVo vo)
	{
		return sqlSession.update("board.replyWrite", vo);
	}
	
	public int update(BoardVo bVo, long sessionUserNo)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("title", bVo.getTitle());
		map.put("contents", bVo.getContents());
		map.put("no", bVo.getNo());
		map.put("userNo", sessionUserNo);
		
		return sqlSession.update("board.modifyWrite", map);
	}
	
	public int delete(BoardVo bVo)
	{
		return sqlSession.delete("board.deleteWrite", bVo);
	}
	
	public int insert(BoardVo bVo, UserVo uVo)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("title", bVo.getTitle());
		map.put("contents", bVo.getContents());
		map.put("userNo", uVo.getNo());
		
		return sqlSession.insert("board.insert", map);
		
	}
	
	public String getMaxBoardNo()
	{
		return sqlSession.selectOne("board.getMaxBoardNo");
	}
	
	public int insert(BoardVo vo)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("title", vo.getTitle());
		map.put("contents", vo.getContents());
		map.put("gNo", vo.getgNo());
		map.put("oNo", vo.getoNo());
		map.put("depth", vo.getDepth());
		map.put("userNo", vo.getUserNo());

		return sqlSession.insert("board.insert", map);
	}
	
//	public List<BoardVo> getCommentCount(long boardNo)
//	{
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		List<BoardVo> list = new ArrayList<>();
//		try 
//		{
//			 conn = getConnection();
//			 
//			 String sql = "select b.no, count(*) from comment a, board b where a.board_no = b.no group by b.no";
//			 
//			 pstmt = conn.prepareCall(sql);
//			 
//			 pstmt.setLong(1, boardNo);
//			 
//			 rs = pstmt.executeQuery();
//			 
//			 while (rs.next())
//			 {
//				 long boardNos = rs.getLong(1);
//				 long commentCount = rs.getLong(2);
//				 
//				 BoardVo vo = new BoardVo();
//				 vo.setNo(boardNos);
//				 vo.setCommentCount(commentCount);
//				 
//				 list.add(vo);
//				 
//			 }
//			 
//		} 
//		catch (SQLException e) 
//		{
//			System.out.println("error : " + e);
//		}
//		finally 
//		{
//			try 
//			{
//				if (rs != null)
//					rs.close();
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
//		return list;
//	}
	
	public List<BoardVo> get(long no)
	{
		List<BoardVo> list = sqlSession.selectList("board.getViewInfo", no);
		return list;
	}
	
	public List<BoardVo> get(String no)
	{
		return sqlSession.selectList("board.getGnoOno", no);
	}
	
	public List<BoardVo> get(String kwd, int startPage, int listCount)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("kwd", kwd);
		map.put("startPage", startPage - 1);
		map.put("listCount", listCount);
		
		return sqlSession.selectList("board.getList", map);
	}
	
	public List<BoardVo> getTotalCount(String kwd)
	{
		BoardVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<BoardVo> list = new ArrayList<>();
		
		try 
		{
			 conn = getConnection();
			 String sql = "SELECT \r\n" + 
				 		"    COUNT(*)\r\n" + 
				 		"FROM\r\n" + 
				 		"    (SELECT \r\n" + 
				 		"        COUNT(*)\r\n" + 
				 		"    FROM\r\n" + 
				 		"        board a, user b\r\n" + 
				 		"    WHERE\r\n" + 
				 		"        a.user_no = b.no\r\n" + 
				 		"            AND (a.title LIKE '%" + kwd + "%'\r\n" + 
				 		"            OR a.contents LIKE '%" + kwd + "%'\r\n" + 
				 		"            OR b.name LIKE '%" + kwd + "%')\r\n" + 
				 		"    GROUP BY a.no\r\n" + 
				 		"    ORDER BY a.g_no DESC , a.o_no ASC) a";
			 
			 pstmt = conn.prepareCall(sql);

			 rs = pstmt.executeQuery();
			 
			 while (rs.next())
			 {
				 long totalCount = rs.getLong(1);
				 
				 
				 result = new BoardVo();
				 result.setTotalCount(totalCount);
				 
				 list.add(result);
			 }
		} 
		catch (SQLException e) 
		{
			System.out.println("error : " + e);
		}
		finally 
		{
			try 
			{
				if (rs != null)
					rs.close();
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
		
		return list;
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
