package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	public void insertMember(Member m) {
		
		int result = new MemberService().insertMember(m);
		
		if(result > 0) { // 성공
			new MemberMenu().displaySuccess("성공적으로 회원가입 되었습니다.");
			
		} else {	// 실패 
			new MemberMenu().displayFail("회원가입에 실패했습니다.");
		}
		
	}//e.insertMember
	
	public void selectList() {
		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) { // 조회결과가 없을 경우 
			new MemberMenu().displayNoData("조회된 데이터가 없습니다.");
		}else { // 조회결과가 담겨있을 경우 
			new MemberMenu().displayMemberList(list);
		}
		
	}//e.selectList
	
	public void searchById(String userId) {
		Member m = new MemberService().searchById(userId);
		
		if(m == null ) {// 조회가 안되었을 경우(일치하는 회원을 찾지 못했을 경우)
			new MemberMenu().displayNoData(userId + "에 해당되는 조회 결과 없습니다.");
		}else { // 조회가 되었을 경우 
			new MemberMenu().displayMember(m);
		}
		
	}//e.searchById
	
	public void searchByName(String userName) {
		ArrayList<Member> list = new MemberService().searchByName(userName);
		
		if(list.isEmpty()) {
			new MemberMenu().displayFail(userName + "에 해당되는 조회 결과 없습니다.");
		}else {
			new MemberMenu().displayMemberList(list);
		}
		
	}//e.searchByName
	
	public void updateMember(Member m) {
		int result = new MemberService().updateMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("업데이트 되었습니다.");
		}else {
			new MemberMenu().displayFail("업데이트 실패했습니다.");
		}
		
	}//e.updateMember
	
	public void deleteMember(String userId) {
		int result = new MemberService().deleteMember(userId);
		
		if(result >0) {
			new MemberMenu().displaySuccess("삭제 성공");
		}else {
			new MemberMenu().displayFail("삭제 실패했습니다");
		}
	}//e.deleteMember
	
	public void loginMember(String id, String pwd) {
		Member m = new MemberService().loginMember(id, pwd);
		
		if(m == null) {
			new MemberMenu().displayNoData("없는 아이디 입니다.");
		}else {
			new MemberMenu().displayMember(m);
		}
	}//e.loginMember
}