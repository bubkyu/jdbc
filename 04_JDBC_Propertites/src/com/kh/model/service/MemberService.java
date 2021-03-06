package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

import static com.kh.common.JDBCTemplate.*; 


// Service : DB와 연결시키는 Connection 격체 생성 후 
//			 Dao 호출 (이때, 생성된 Connection객체와 Controller로 부터 전달된 값 같이 넘겨줄거임) 
//			 돌아오는 반환값 받아서 만약에 트랜잭션 처리가 필요할 경우 트랜잭션 처리도 여기서 진행
public class MemberService {
	
	public int insertMember(Member m) {
		/*
		// 처리 결과를 받을 변수 
		int result = 0; 
		
		//DB의 연결정보를 담는 객체 
		Connection conn = null;
		
		
		//jdbc driver 등록철히
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 오타있다거나, ojdbc6.jar 파일 추가 안되어있을 경우 
			// Connection 객체 생성 
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","JDBC","JDBC");
			
			result = new MemberDao().insertMember(conn, m);
			
			// 트랜잭션 처리
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback(); 
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close(); 
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
		Connection conn = getConnection(); 
		int result = new MemberDao().insertMember(conn, m);
		
		if(result > 0) {
			commit(conn);
			
		}else {
			rollback(conn); 
		}
		
		close(conn); 
		
		
		return result; 
		
	}//e.insertMember
	
	public ArrayList<Member> selectList() {
		Connection conn = /*JDBCTemplate.*/getConnection();
		ArrayList<Member> list = new MemberDao().selectList(conn);
		
		/*JDBCTemplate.*/close(conn);
		
		return list; 
		
		
	}//e.selectList
	
	public Member searchById(String userId) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().searchById(conn, userId);
		
		close(conn);
		
		return m; 
		
	}//e.searchByID

	public ArrayList<Member> searchByName(String userName) {
		Connection conn = getConnection();
		ArrayList<Member> list = new MemberDao().searchByName(conn, userName);
		
		close(conn);
		
		return list; 
	}//e.searchByName
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().updateMember(conn,m);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result; 
	}//e.updateMember
	
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, userId);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result; 
		
	}//e.deleteMember
	
	public Member loginMember(String id, String pwd) {
		Connection conn = getConnection();
		Member m = new MemberDao().loginMember(conn,id,pwd);
		
		close(conn);
		
		return m; 
	}
	
}