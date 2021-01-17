package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {
	
	/**
	 * 사용자가 회원 가입 요청시 실행되는 메소드
	 * @param m	-> 회원가입시 입력한 회원의 정보들이 다 담겨있는 객체
	 */
	public void insertMember(Member m) {
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0) { //성공
			new MemberMenu().displaySuccess("회원 가입 성공");
		}else {	// 실패
			new MemberMenu().displayFail("회원 가입 실패");
		}
		
	} // e.insertMember
	
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayFail("조회 결과 없음");
		}else {
			new MemberMenu().displayMemberList(list);
			
		}
		
	} // selectList
	
	public void selectByUserId(String userID) {
		Member m = new MemberDao().selectByUserId(userID);
		
		if(m != null) {	//조회가 되었을때는
			new MemberMenu().displayMember(m);
			
		}else {
			new MemberMenu().displayNoData("정보 없습니다.");
		}
	}// selectByUserId
	
	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
		
		if(list.isEmpty()) { // 결과없음
			new MemberMenu().displayNoData(keyword + "에 해당 결과 없음.");
		}else { // 결과있음
			new MemberMenu().displayMemberList(list);
			
		}
	}	//selectByUserName
	
	public void updateMember(Member m) {
		int result = new MemberDao().updateMember(m);
		
		if(result > 0) {	// 성공 -> displaySuccess
			new MemberMenu().displaySuccess("회원 정보 변경 성공");
		}else {
			new MemberMenu().displayFail("회원 정보 변경 실패");
			
		}
	}
	
	public void deleteMember(String userId) {
		int result = new MemberDao().deleteMember(userId);
		
		if(result >0) {// 성공시
			new MemberMenu().displaySuccess("회원 탈퇴 성공");
		}else {
			new MemberMenu().displayFail("회원 탈퇴 실패");
		}
	}
	
	

}









