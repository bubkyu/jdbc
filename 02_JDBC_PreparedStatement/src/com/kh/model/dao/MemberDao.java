package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

public class MemberDao {

	/*
	 * Statement와 PreparedStatement의 차이점
	 * - Statement 같은 경우 완성된 sql문을 바로 실행하는 객체(sql문을 완성형태로 만들어둬야한다.)
	 * - PreparedStatement 같은 경우 미완성된 sql문을 잠시 보관해둘 수 있는 객체
	 * 		해당 sql문을 실행하기 전 완성형태로 만든 후 실행하면 됨.
	 * * 기존의 Statement 방식
	 * Connection객체를 통해 Statement 객체 생성		: stmt = conn.createStatement();
	 * Statement 객체를 통해 완성된 sql문 실행 및 결과   : 결과   = stmt.executeXXXXXX( select문의 경우 executeQuery이고, dml문의 경우 executeUpdate)완성된 sql문달
	 * 
	 * * PreparedStatement 방식
	 * Connection 객체를 통해서 PreparedStatement 객체 생성
	 * 				(단, 미완성된 sql문을 담은채로 생성)		: pstmt = conn.prepareStatement(미완성된 sql);
	 * PreparedStatement 에서 제공하는 메소들 통해 완성형태로  : pstmt.setString(문자열) / pstmt.setInt(정수형) pstmt.setXXX(1, "대체할값");....
	 * 그리고 나서 해당 완성된 sql문 실행 및 결과				: 결과 = pstmt.executeXXX(); 
	 * 
	 */
	
	
	public int insertMember(Member m) {	// insert 문  -> 처리된 행의 갯수(int)
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		// 실행하고자 하는 sql문 미완성된 형태로 둘 수 있다. -> 미리 사용자가 입력한 값들 공간(?)만 확보
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?,?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			pstmt = conn.prepareStatement(sql);	// PreparedStatement 객체 생성시 sql문 담은 채로 생성
			
			// 현재 담긴 sql문 미완성된 상태이기에 바로 실행은 불가 - > 각각의 공간에 실제 값 대체한 후 실행
			pstmt.setString(1, m.getUserId());	//--> setString으로 대체 시 'user01' (양옆에 홀따옴표 붙어서 들어간다.)
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());	// -> setInt로 대체시 20 (홑따옴표 안붙어서 들어간다)
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return result;
		
		
	} // e.insetMember
	
	public ArrayList<Member> selectList(){
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				Member m = new Member();
				
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				
				list.add(m);
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch (Exception e) {
				
			}
		}
		return list;
		
	}// selectList
	
	public Member selectByUserId(String userId) {// select문
		
		Member m = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
						   rset.getString("USERID"),
						   rset.getString("USERPWD"),
						   rset.getString("USERNAME"),
						   rset.getString("GENDER"),
						   rset.getInt("AGE"),
						   rset.getString("EMAIL"),
						   rset.getString("PHONE"),
						   rset.getString("ADDRESS"),
						   rset.getString("HOBBY"),
						   rset.getDate("ENROLLDATE") 
						   ); 
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
				
			}catch (Exception e) {
				
			}
		}
		
		return m;
	}// selectByUserId
	
	public ArrayList<Member> selectByUserName(String keyword){
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" + keyword + "%'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("USERNO"),
									rset.getString("USERID"),
									rset.getString("USERPWD"),
									rset.getString("USERNAME"),
									rset.getString("GENDER"),
									rset.getInt("AGE"),
									rset.getString("EMAIL"),
									rset.getString("PHONE"),
									rset.getString("ADDRESS"),
							        rset.getString("HOBBY"),
							   	    rset.getDate("ENROLLDATE") 
									   )
						);
			}//e.while
			
		} catch (ClassNotFoundException e) {
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch (Exception e) {
				
			}
		}
		return list;
		
	}// selectByUserName
	
	public int updateMember(Member m) {	// update문
		
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "UPDATE MEMBER "
				   + "SET USERPWD = '" + m.getUserPwd() + "', "
				   + 	   "EMAIL = '" + m.getEmail() + "', "
				   +	   "PHONE = '" + m.getPhone() + "', "
				   + 	 "ADDRESS = '" + m.getAddress() +"' "
				   + "WHERE USERID = '" + m.getUserId() + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
			if(result >0) {// 맞으면
				conn.commit();
			}else {	//틀리면
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return result;
	} // upDateMember
	
	public int deleteMember(String userId) {
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID='" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
			if(result > 0) {	// 성공
				conn.commit();
			}else {	// 실패
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
				
			}catch (Exception e) {
				
			}
		}
		return result;
		
	}
	
	
}

















