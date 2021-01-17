package com.kh.run;

import com.kh.view.MemberMenu;

public class Run {

	public static void main(String[] args) {
		
		/*
		 * model 	= 데이터에 대한 처리 역할
		 * 	> vo  	= 데이터를 담을 수 있는 역할
		 * 	> dao	= DB와 직접적으로 연결해서 sql문 실행 및 결과
		 * 	
		 * 
		 * view		= 사용자가 보게될 시각적인 요소(화면) -> 입력 및 출력 (Scanner / print)
		 * 
		 * view - > Controller	: 요청, 메소드 호출[전달값]
		 * Controller - > view : 메소드 호출, 
		 * 
		 * controller = 사용자가 요청한 내용을 처리 역할
		 * view 에서 전달받은 데이터를 가공처리 후 Dao 요청
		 * 
		 * Controller -> DAO : 메소드 호출[전달값]
		 * Dao 에서 반환받은 결과가 성공인지 실패인지 판단(조건식)
		 * 
		 * Dao		= DB와 직접 연결 한 후 sql 문 실행 1) DB와 연결 2) sql문 실행 및 결과 받기 , 만일 DML구문이였다면, 트랜잭션처리 3) JDBC객체 자원 반납 4) 결과 반환
		 * 
		 * Dao - > Controller :  return 결과
		 *   
		 */
			// 프로그램 실행만 담당( 즉, 사용자가 보게될 첫 화면을 띄어주기만 하면됨)
		new MemberMenu().mainMenu();
		
	}

}

















