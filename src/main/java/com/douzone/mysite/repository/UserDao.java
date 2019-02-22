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
	
//	public UserVo get(Long no)
//	{
//		UserVo result = null;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try 
//		{
//			 conn = dataSource.getConnection();
//			 
//			 String sql = "select no, name, email, gender from user where no = ?";
//			 
//			 pstmt = conn.prepareCall(sql);
//			 
//			 pstmt.setLong(1, no);
//			 
//			 rs = pstmt.executeQuery();
//			 
//			 if (rs.next())
//			 {
//				 long nos = rs.getLong(1);
//				 String name = rs.getString(2);
//				 String email = rs.getString(3);
//				 String gender = rs.getString(4);
//				 
//				 
//				 result = new UserVo();
//				 result.setNo(nos);
//				 result.setName(name);
//				 result.setEmail(email);
//				 result.setGender(gender);
//			 }
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
//		return result;
//	}
	
//	private static Connection getConnection() throws SQLException
//	{
//		Connection conn = null;
//		
//		try 
//		{
//			// 1. JDBC Driver(MySQL) 로딩
//			Class.forName("com.mysql.jdbc.Driver"); // 제대로 로딩됐는지 확인
//			
//			// 2. 연결하기 (Connection 객체 얻어오기)
//			String url = "jdbc:mysql://localhost:3306/webdb";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//		} 
//		catch (ClassNotFoundException e) 
//		{
//			System.out.println("드라이버 로딩 실패 : " + e);
//		}
//		
//		return conn;
//	}

