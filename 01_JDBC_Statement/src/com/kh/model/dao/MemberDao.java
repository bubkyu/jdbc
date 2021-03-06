package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// Dao(Data Access Object) : DB와 직접적으로 접근하는 담당
// 1) DB에 접속하여 
// 2) sql문 실행 및 결과 받기( select문 경우 : 조회된 ResultSet / dml문 경우 : 처리된 행의 갯수)
// 	  만일 dml문 수행했다면 트랜잭션 처리하기(성공이면 commit, 실패면 rollback)
// 3) 결과 반환
public class MemberDao {
	
	/*
	 * 	* JDBC 객체
	 * 	- Connection : DB의 연결정보를 담는 객체 
	 * 	- [Prepared]Statement : DB에 SQL문을 전달해서 실행하고 그 결과를 받아내는 객체 
	 * 				> Statement : 실행 시킬 sql 문의 완성 형태여야됨!
	 * 				> PreparedStatement : 실행시킬 sql문이 미완성 상태여도됨
	 * 	- ResultSet : Select문 수행 후 조회된 결과들이 담겨있는 객체
	 * 	
	 * 	* 처리 순서
	 * 	1) jdbc driver 등록 : 해당 DBMS가 제공하는 클래스로 등록 
	 *  2) Connection 객체 생성 : 연결하고자 하는 DB 정보를 입력해서 DB와 연결하며 생성 
	 *  3) Statement 객체 생성 : Connection 객체를 이용해서 생성(sql문 실행 및 결과를 담당하는 객체) 
	 *  4) SQL문 전송 후 실행 및 결과 받기 : Statement 객체를 이용해서 sql문 실행 후 결과 받기 
	 *  5) 실행결과 --> select문일 경우 --> ResultSet객체(조회된 데이터들이 담겨있음) --> 6-1)
	 *  		 --> dml(insert,update,delete) 문일 경우 --> int (처리된 행의 갯수) --> 6-2)
	 *  
	 *  6-1) ResultSet에 담겨있는 데이터들 하나씩 하나씩 뽑아서 vo 객체에 주섬주섬담기 
	 *  6-2) 트랜잭션 처리 (성공이면 commit, 실패면 rollback) 
	 *  
	 *  7)** 다쓴 JDBC 용 객체들 반드시 자원 반납(close)	--> 생성된 역순으로 
	 */
	

	public int insertMember(Member m) {	// insert문 실행
		
		// 필요한 변수들 먼저 셋팅 
		
		// 처리된 결과(처리된 행의 갯수)를 받아줄 변수 
		int result = 0; 
		// DB의 연결 정보를 담는 객체 선언
		Connection conn = null; 
		// SQL문을 전송해서 실행 후 결과 받는 객체 선언
		Statement stmt = null; 
		
		// 실행할 sql문 (완성형태로 만들어줄 것!!) --> 끝에 세미콜론 있으면 안됨!!! 
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'user02','pass02','홍길녀','F', 20, ...)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL," 
											 + "'" + m.getUserId() + "', "
											 + "'" + m.getUserPwd() + "', "
											 + "'" + m.getUserName() + "', "
											 + "'" + m.getGender() + "', "
											 + "'" + m.getAge() + ","
											 + "'" + m.getEmail() + "', "
											 + "'" + m.getPhone() + "', "
											 + "'" + m.getAddress() + "', "
											 + "'" + m.getHobby() + "', SYSDATE)";
						
		//System.out.println(sql); --> 잘 입력 됬는지 test
		
		try {
			// 1) JDBC DRIVER 등록 
			Class.forName("oracle.jdbc.driver.OracleDriver"); // // 오타있거나, ojdbc6.jar 파일이 추가되어있지 않을 경우 ClassNotFoundException 발생 
			
			// 2) Connection 객체 생성 ( DB에 연결 --< url, 계정명, 계정비밀번호) 
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","system","oracle");
			
			// 3) Statement 객체 생성 
			stmt = conn.createStatement(); 
			
			// 4), 5) sql문 해당 db에 전달 후 실행 --> 결과 받기 (처리된 행의 갯수) 
			result = stmt.executeUpdate(sql);  // --> insert, update, delete 구문일 경우 executeUpdate 메소드로 sql 호출 
			
			//System.out.println(result); --> test 
			
			// 6-2) 트랜잭션 처리 
			if(result > 0) {	//성고했을 경우 commit
				conn.commit();
				
			}else {	// 그게 아닐 경우 rollback 
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
				// 7) 다쓴 JDBC용 객체 자원 반납 (close) 단, 생성된 역순으로 
				stmt.close();
				conn.close(); 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return result; 
		
	} //e.insertMember
	
	
	public ArrayList<Member> selectList() {
		
		// 필요한 변수들 셋팅 
		
		// 처리된 결과(조회된 회원들 == 여러행) 들을 담아줄 ArrayList 생성 
		ArrayList<Member> list = new ArrayList</*Member*/>(); 
		
		//DB의 연결정보를 담는 객체 선언
		Connection conn = null; 
		
		//SQL문 실행 후 결과 받는 객체 선언
		Statement stmt = null; 
		
		// Select 문 실행 후 조회 결과값들이 실질적으로 담길 ResultSet 객체 선언
		ResultSet rset = null; 
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성 
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","system","oracle");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement(); 
			
			// 4), 5) SQL문 전송해서 실행 후 결과받기 
			rset = stmt.executeQuery(sql);		// select문은 executeQuery 메소드를 이용 
										// insert, update, select문은 executeUpdate 메소드 이용 
			
			// 6-1) ResultSet에 담겨있는 데이터 하나씩 뽑아서 주섬주섬 담기 
			while(rset.next()) {
				
				Member m = new Member(); 
				
				// 현재 rset의 커리가 가리키고 있는 행의 데이터를 뽑아서 담기 (컬럼명 제시해서 해당 컬럼값만 뽑아서) 
				// 컬럼명은 대소문자 구분없음 
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
				
				//리스트에 해당 완성된 회원 한행씩 추가
				list.add(m); 
					
			}
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				//7. 다쓴 JDBC 객체 자원 반납(생성된 역순으로)
				rset.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		return list; 
		
	}//e.selectList
	
	public Member selectByUserId(String userId) {	//select문
		
		// 필요한 변수들 셋팅
		
		// 1.처리결과(한 회원)를 담을 변수 
		Member m = null; 
		// 2.DB 연결정보 담는 객체
		Connection conn = null;
		// 3.SQL문 실행 후 결과 받는 객체
		Statement stmt = null;
		// 4.조회 결과가가 실질적으로 담기는 객체
		ResultSet rset = null;
		
		// 5.sql문 완성형탱로 만들기
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		
		try {
			//1)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			//3)
			stmt = conn.createStatement();
			//4), 5)
			rset = stmt.executeQuery(sql);
			
			//조회 결과가 한행일 꺼기 때문에 반복문 없이 조건문만으로 !!
			if(rset.next()) {
			
				//조회가 되었다면 이떄 생성해줄꺼임 !!
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
			// TODO Auto-generated catch block
			e.printStackTrace();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//7)
			try {
				rset.close();
				stmt.close();
				conn.close(); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return m;
		
	}//e.SelectByUserId
	
	public ArrayList<Member> selectByUserName(String keyword) {
	
		//필요한 변수들 셋팅
		
		//처리결과를 최종적으로 담줄 ArrayList 생성
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; 
		
		// 실행 할 sql문 완성형태 
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" + keyword +"%'";
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list; 
		
	}//e.selectByUserName
	
	
	public int updateMember(Member m) {	// update문
		
		// 처리 결과를 담아줄 변수
		
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		/*UPDATE MEMBER
		SET USERPWD = 'XX',
		    EMAIL = 'XXX',
		    PHONE = 'XXX',
		    ADDRESS = 'XXX'
		WHERE USERID = 'XX';
		*/
		
		// 실행할 sql문 완성형태로 작성
		String sql = "UPDATE MEMBER "
				   + "SET USERPWD = '" + m.getUserPwd() + "', "
				   + 	   "EMAIL = '" + m.getEmail() + "', "
				   +	   "PHONE = '" + m.getPhone() + "', "
				   + 	 "ADDRESS = '" + m.getAddress() +"' "
				   + "WHERE USERID = '" + m.getUserId() + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
			if(result > 0) { // 맞으면
				conn.commit();
			}else { // 틀리면
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
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return result;
		
		
	}
	
	public int deleteMember(String userId) {	// delete 처리된 행의 갯수 
		
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID='" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "oracle");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
			// 트랜잭션
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
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	
	
}




















