package com.jdbc.controller;

import com.jdbc.view.MainView;

public class ControllerImpl implements Controller {
	public void mainMenu() {
		new MainView().mainMenu();
	}
	
	@Override
	public void selectAllEmployee(){
//		List<Employee> e= 
	}
}
