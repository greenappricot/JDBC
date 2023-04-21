package com.jdbc.controller;

import java.util.List;

import com.jdbc.common.MemberController;
import com.jdbc.model.dto.MemberDto;
import com.jdbc.model.service.MemberService;
import com.jdbc.view.MainView;

public class MemberControllerImpl implements MemberController {
	
	// private MemberDao dao= new MemberDao(); Dao 직접 호출 하지 않고 service 거쳐서 호출한다.-> 의존성 낮추고 독립성 높임
	private MemberService service= new MemberService(); 
	
	// MemberController interface에 있는 추상메소드를 구현해줌
	@Override
	public void mainMenu() {
		new MainView().mainMenu();
	}

	@Override
	public void selectAllMember() {
		// TODO Auto-generated method stub
		List<MemberDto> members= service.selectAllMember();
		new MainView().printMembers(members);
	}

	@Override
	public void selectMemberById() {
		String id= new MainView().inputData("아이디");
		MemberDto m= service.selectMemberById(id);
		new MainView().printMember(m);
	}

	@Override
	public void insertMember() {
		MemberDto m= new MainView().insertMember();
		int result= service.insertMember(m);
		new MainView().printMsg(result>0? "회원 등록 성공 :)" : "회원 등록 실패 :(");
		
	}

	@Override
	public void updateMember() {
		MemberDto m= new MainView().updateMember();
		int result= service.updateMember(m);
		new MainView().printMsg(result>0? "회원 수정 성공": "회원 수정 실패");
	}

	@Override
	public void selectMemberByName() {
		String name= new MainView().inputData("이름");
		List<MemberDto> m= service.selectMemberByName(name);
		new MainView().printMembers(m);
		
	}
	
	@Override
	public void deleteMember() {
		String id= new MainView().deleteMember();
		int result= service.deleteMember(id);
		new MainView().printMsg(result>0? "회원 삭제 성공": "회원 삭제 실패");
	}

}
