package com.jdbc.view;

import java.util.List;
import java.util.Scanner;

import com.jdbc.common.MemberController; // interface를 중간에 껴서 의존성을 떨어뜨린다. -> interface에 controller에 있는 method들이 추상메소드로 구현되어있다. -> controller가 interface를 
import com.jdbc.controller.MemberControllerImpl;
import com.jdbc.model.dto.MemberDto;

public class MainView {
	
//	private MemberController controller= new MemberController(); // 데이터 저장하는 저장소가 독립적이라서 new로 생성해서 사용해도 된다  
	private MemberController controller= new MemberControllerImpl(); // interface이기 때문에 new 생성자 이용해서 사용할 수 없음 
//	private MemberController controller; // 생성과 동시에 호출하지 않고 기본생성자 사용해서 생성할 수 있다. 
//	public MainView(MemberController controller) {
//		this.controller= controller; 
//	}
//	
	
	public void mainMenu() {
		Scanner sc= new Scanner(System.in);
		while(true) {
			System.out.println("==== jdbc 회원관리 프로그램 ====");
			System.out.println("1. 전체 회원 조회");
			System.out.println("2. 아이디로 회원 조회");
			System.out.println("3. 이름으로 회원 조회");
			System.out.println("4. 회원 등록");
			System.out.println("5. 회원 수정(이름, 나이, 이메일, 주소)");
			System.out.println("6. 회원 삭제");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴 선택 : ");
			int cho= sc.nextInt();
			switch(cho) {
				case 1 : controller.selectAllMember(); break;
				case 2 : controller.selectMemberById(); break;
				case 3 : controller.selectMemberByName(); break;
				case 4 : controller.insertMember(); break;
				case 5 : controller.updateMember(); break;
				case 6 : controller.deleteMember(); break;
				case 0 : System.out.println("프로그램을 종료합니다"); return;
				default : System.out.println("0 ~ 5 사이의 메뉴를 입력하세요"); 
			}
		}
	}// close mainMenu
	
	public void printMembers(List<MemberDto> members) {
		System.out.println("==== 조회된 결과 ====");
		members.forEach(m->System.out.println(m)); //toString override 필수, 분기처리 해줘야함
		System.out.println("=====================");
	}
	
	// 검색할 id를 입력받는 메소드
	public String inputData(String title) {
		Scanner sc= new Scanner(System.in);
		System.out.println("==== 검색할 "+title+" 입력 ====");
		System.out.print("입력 : ");
		return sc.nextLine();
	}
	
	public void printMember(MemberDto m) {
		System.out.println("==== 조회된 결과 ====");
		System.out.println(m!=null? m:"검색결과가 없습니다");
		System.out.println("=====================");
	}
	
	public MemberDto insertMember() {
		Scanner sc= new Scanner(System.in);
		MemberDto m= new MemberDto();
		System.out.println("==== 등록할 회원 정보 입력 ====");
		System.out.print("아이디 : ");
		String memberId=sc.nextLine();
		m.setMemberId(memberId);
		System.out.print("비밀번호 : ");
		String memberPwd=sc.nextLine();
		m.setMemberPwd(memberPwd);
		System.out.print("이름 : ");
		String memberName= sc.nextLine();
		m.setMemberName(memberName);
		System.out.print("성별(M/F) : ");
		char gender= sc.next().charAt(0);
		m.setGender(gender);
		System.out.print("나이 : ");
		int age= sc.nextInt();
		m.setAge(age);
		System.out.print("email : ");
		sc.nextLine();
		String email= sc.nextLine();
		m.setEmail(email);
		System.out.print("전화번호 : ");
		String phone= sc.nextLine();
		m.setPhone(phone);
		System.out.print("주소 : ");
		String address= sc.nextLine();
		m.setAddress(address);
		System.out.print("취미(,로 구분) : ");
		String[] hobby= sc.nextLine().split(",");
		m.setHobby(hobby);
//		return new MemberDto(memberId, memberPwd, memberName, gender, age, email, phone, address, hobby); 
		return m;
		// Date값은 가입일이므로 사용자에게 입력받지 않는다
		// 1. null값으로 처리 2. MemberDto클래스에 Date값 없는 매개변수 있는 생성자 추가하기 3.Setter로 생성 4. builder패턴 구현 
		
	}
	public void printMsg(String msg) {
		System.out.println("==== 시스템 메세지 ====");
		System.out.println(msg);
		System.out.println("=======================");
	}
	
	public MemberDto updateMember() {
		Scanner sc= new Scanner(System.in);
		MemberDto m= new MemberDto();
		System.out.println("==== 수정할 회원 정보 등록 ====");
		System.out.print("변경할 아이디 : ");
		String memberId= sc.nextLine();
		m.setMemberId(memberId);
		System.out.print("수정할 이름 : ");
		String memberName= sc.nextLine();
		m.setMemberName(memberName);
		System.out.print("수정할 나이 : ");
		int age= sc.nextInt();
		m.setAge(age);
		System.out.print("수정할 이메일 : ");
		sc.nextLine();
		String email= sc.nextLine();
		m.setEmail(email);
		System.out.print("수정할 주소 : ");
		String Address= sc.nextLine();
		m.setAddress(Address);
		
		return m;
	}
	
	public String deleteMember() {
		Scanner sc= new Scanner(System.in);
		MemberDto m= new MemberDto();
		System.out.println("==== 삭제할 회원 정보 입력 ====");
		System.out.print("삭제할 회원 아이디 : ");
		String memberId= sc.nextLine();
		
		return memberId;
	}
	
	
}










