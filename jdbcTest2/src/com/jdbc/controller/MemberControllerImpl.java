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
		String msg= service.insertMember(m);
		new MainView().printMsg(msg);
		
	}

	@Override
	public void updateMember() {
		MemberDto m= new MainView().updateMember();
		String msg= service.updateMember(m);
		new MainView().printMsg(msg);
	}

	@Override
	public void selectMemberByName() {
		// TODO Auto-generated method stub
		
	}

}
