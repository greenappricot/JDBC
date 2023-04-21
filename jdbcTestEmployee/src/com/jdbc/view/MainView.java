package com.jdbc.view;

import java.util.Scanner;

import com.jdbc.controller.Controller;
import com.jdbc.controller.ControllerImpl;

public class MainView {
	
	private Controller controller= new ControllerImpl();
	
	public void mainMenu() {
		Scanner sc= new Scanner(System.in);
		System.out.println("==== 사원 관리 프로그램 ====");
		System.out.println("1. 전체 사원 조회");
		System.out.println("2. 항목별 사원 조회");
		System.out.println("3. 사원 등록");
		System.out.println("4. 사원 수정");
		System.out.println("5. 사원 삭제");
		System.out.println("6. 부서 관리(등록, 수정, 삭제)");
		System.out.println("7. 직책 관리(등록, 수정, 삭제)");
		System.out.print("입력 : ");
		int num=sc.nextInt();
		
		switch(num) {
			case 1 : controller.selectAllEmployee(); break; 
		}
	}
	
}
