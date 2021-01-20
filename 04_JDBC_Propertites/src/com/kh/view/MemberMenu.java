package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private MemberController mc = new MemberController(); 
	
	
	public void mainMenu() {
		
		int menu; // ---> 반복족으로 loop문에서 메뉴를 받을때 loop을 빠져나오면 또 박스를 만들기 떄문에 공간을 차지 하기 떄문에 밖에다가 미리 선언해두는것도 방법 
		while(true) {
			System.out.println("\n===회원 관리 프로그램===");
			System.out.println("1. 회원 가입");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 이름(키워드)으로 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("7. 로그인");
			System.out.println("0. 프로그램종료");
			System.out.print("메뉴 선택: ");
			menu = sc.nextInt();
			sc.nextLine(); 
			
			switch(menu) {
			case 1 : insertMember(); 
					break;
			case 2 : mc.selectList(); 
					break;
			case 3 : mc.searchById(inputMemberId()); 
					break;
			case 4 : mc.searchByName(inputMemberName()); 
					break;
			case 5 : updateMember(); 
					break;
			case 6 : mc.deleteMember(inputMemberId()); 
					break;
			case 7 : loginMember(); 
					break; 
			case 0 : System.out.println("정말로 끝내겠습니까?(y/n)"); 
					 if(sc.nextLine().toUpperCase().charAt(0) == 'Y') {
						 System.out.println("프로그램을 종료합니다.");
						 return; 
					 } else {
						 break; 
					 }
			default :  System.out.println("메뉴를 다시 선택해주세요.");
			}
			
		}//e.loop
		
	}//e.mainMenu
	
	// 사용자에게 값을 입력받는 화면들
	// 1. 회원 가입 화면 (회원의 정보들 입력받는 화면)
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
		
		// 회원 가입 요청 !!! (사용자가 입력한 값들 주섬주섬담은 Member 객체 전달하면서)
		mc.insertMember(new Member(userId,userPwd,userName,gender,age,email,phone,address,hobby));
	}
	
	// 2. 회원 아이디 입력받는 화면(회원 아이디로검색, 회원정보수정, 회원탈퇴)
	public String inputMemberId() {
		System.out.print("\n회원 아이디 입력:");
		return sc.nextLine(); 
		
	}
	
	// 3. 회원 이름 입력받는 화면
	public String inputMemberName() {
		System.out.print("\n검색할 회원명(키워드) 입력 : " );
		return sc.nextLine(); 
		
		// String input = sc.nextLine();
		//  return input; --> 이 내용을 위 와 같이 간다한게 하기 !! 굳 
	}
	
	// 4. 회원 정보 수정하는 화면 (변경하고자하는 회원아이디, 비번, 이메일, 전화번호, 주소 )
	public void updateMember() {
		System.out.println("\n==회원 정보 변경===");
		
		Member m = new Member(); 
		m.setUserId(inputMemberId());
		
		System.out.print("변경할 비밀번호: ");
		m.setUserPwd(sc.nextLine());
		
		System.out.print("변경할 이메일: ");
		m.setEmail(sc.nextLine());
		
		System.out.print("변경할 전화번호(-뺴고): ");
		m.setPhone(sc.nextLine());
		
		System.out.print("변경할 주소: ");
		m.setAddress(sc.nextLine());
		
		mc.updateMember(m);
	}
	
	public void loginMember() {
		System.out.println("\n====로그인====");
		
		System.out.print("ID: ");
		String id = sc.nextLine();
		
		System.out.print("PWD: ");
		String pwd = sc.nextLine();
		
		mc.loginMember(id, pwd); 
	
		
	}
	
	
	
	//사용자가 서비스 요청 후 보게되는 응답화면들 
	// 1. 서비스 요청 성공 했을 때의 응답화면 
	public void displaySuccess(String message) {
		System.out.println("\n 서비스 요청 성공: " + message);
	}
	
	// 2. 서비스 요청 실패했을 떄의 응답화면 
	public void displayFail(String message) {
		System.out.println("\n서비스 조회 실패 :" + message );
	}
	
	// 3. 조회 요청시 조회결과가 없을 경우의 응답화면 
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}
	
	// 4. 조회 요청시 한행만 조회되었을 떄 응답화면
	public void displayMember(Member m) {
		System.out.println("\n조회된 회원 정보는 다음과 같습니다." );
		System.out.println(m);
	}
	
	// 5. 조회 요청시 여러행 조회되었을 떄 응답화면 
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("\n조회된 회원 정보는 다음과 같습니다." );
		for(Member m : list) {
			System.out.println(m);
		}
	}
	
	
	

}