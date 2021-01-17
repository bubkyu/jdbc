package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

//View : 사용자가 보게될 시각적인 요소 담당 (입력 및 출력) 
public class MemberMenu {

	// Scanner 객체 생성(전역으로 입력받을 수 있게) 
	
	private Scanner sc = new Scanner(System.in);
	
	// MemberController 객체생성 (젼역에서 바로 요청할 수 있게)
	private MemberController mc = new MemberController();
	
	
	/**
	 * 	사용자가 보게될 첫화면 
	 */
public void mainMenu() {	
		
		while(true) {
			System.out.println("\n===회원 관리 프로그램===");
			System.out.println("1. 회원 가입");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 이름으로 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그르램 종료");
			System.out.print("번호 선택: ");
			int menu = sc.nextInt();
			sc.nextLine(); 
			
			switch(menu) {
			case 1 : insertMember();	break; // switch 빠져나가는 break;
			
			case 2 : mc.selectList();	break;
			
			case 3 : String userId = inputMemberId();//--> 반환된 값을 받기 위해 String으로 바꿈 	
					  mc.selectByUserId(userId);
					 break; 
					 
			case 4 : //String userName = inputMemberName();
					 //mc.selectByUserName(userName);
					 mc.selectByUserName(inputMemberName());
					 break;
					 
			case 5 :  updateMember(); break;
			
			case 6 :  mc.deleteMember(inputMemberId()); break;
			case 0 : System.out.println("프로그램을 종료합니다."); return; // 메소드를 빠져나가기 위해 return; 
			default : System.out.println("번호를 잘 못 입력하셨습니다. 다시 입력해주세요.");	// 스위치 마지막 문구이기때문에 알아서 빠져나감. 
			}// e.switch
			
			
		} //e.loop
		
		
	}
	
/**
 * 회원가입 창 (화면)
 * 즉, 회원의 정보를 입력받는 메소드 
 */

public void insertMember() {
	
	System.out.println("\n====회원 가입====");
	System.out.print("ID: ");
	String userId = sc.nextLine();
	
	System.out.print("PWD: ");
	String userPwd = sc.nextLine(); 
	
	System.out.print("Name: ");
	String userName = sc.nextLine(); 
	
	
	System.out.print("Gender(M/F): ");
	String gender = sc.nextLine().toUpperCase(); // 사용자가 소문자로 입력할 수 있기 떄문에 무조건 대문자가 오게끔 uppercase 사용
	
	System.out.print("Age: ");
	int age = sc.nextInt();
	sc.nextLine();
	
	System.out.print("Email: ");
	String email = sc.nextLine();
	
	System.out.print("ph Number(w/o -): ");
	String phone = sc.nextLine(); 
	
	System.out.print("Addresss: ");
	String address = sc.nextLine(); 
	
	System.out.print("Hobby(, w/o space): ");
	String hobby = sc.nextLine(); 
		
		
	// mc.insertMember(userId, userPwd, userName, .); 일일이 나열하면 많아지기 떄문에 한번에 보내주는 방번은 밑에 기본생성자 말고 매개변수생성자로 한번에 하기 
			// Member 객체 생성 후 주섬주섬 담자 
			Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby); 
			
			// 회원가입 요청 !! (Controller 메소드 호출) 
			mc.insertMember(m); 
		
		
	}

	/**
	 * 사용자에게 조회할 회원 아이디 입력받은 후 그 아디디 반환하는 메소드
	 * @return	--> 사용자가 입력한 아이디
	 */
public String inputMemberId() {
	
	System.out.print("회원 아이디 입력: ");
	String userId = sc.nextLine(); 
	
	return userId; // 회원정보변경,탈퇴를 위해서 메뉴로 다시 반환. --> 다시 사용하기 위해서 이다. 
	// return sc.nextLine(); 
	
}//e.inputMemberId
	
	/**
	 * 사용자에게 조회할 회원명(키워드)를 입력받은 후 반환하는 메소드
	 * @return	--> 조회할 회원명 (키워드)
	 */
public String inputMemberName() {
	
	System.out.print("회원 이름(키워드) 입력:");
	return sc.nextLine(); 
	
}//e.inputMemberName


/**
 *  사용자에게 변경할 정보들과 해당 회원의 아이디 입력받는 화면
 */
public void updateMember() {
	
	Member m = new Member();
	
	//System.out.println("아이디 : ");
	//m.setUserId(sc.nextLine());
	m.setUserId(inputMemberId());
	
	System.out.print("변경할 암호 : ");
	m.setUserPwd(sc.nextLine());
	
	System.out.print("변경할 이메일 : ");
	m.setEmail(sc.nextLine());
	
	System.out.print("변경할 전화번호(- 빼고 입력) : ");
	m.setPhone(sc.nextLine());
	
	System.out.print("변경할 주소 : ");
	m.setAddress(sc.nextLine());
	
	// m : 변경하고자하는 회원 아이디, 변경할 암호, 변경할 이메일, 변경할 전화번호, 변경할 주소
	
	mc.updateMember(m);
	
	
}




	//  서비스 요청 처리 후 사용자가 보게될 응답화면
public void displaySuccess(String message) {
	System.out.println("서비스 요청 성공:" + message);
}

public void displayFail(String message) {
	System.out.println("서비스 요청 성공:" + message);
}

public void displayNoData(String message) {
	System.out.println(message);
}
public void displayMemberList(ArrayList<Member> list) {
	System.out.println("\n조회된 데이터는 다음과 같습니다.\n");
	
	for(int i=0; i<list.size();i++) {
		System.out.println(list.get(i));
	}
	

	
}

public void displayMember(Member m) {
	System.out.println("\n조회된 데이터는 다음과 같습니다.\n");
	System.out.println(m);
}
	
}
















