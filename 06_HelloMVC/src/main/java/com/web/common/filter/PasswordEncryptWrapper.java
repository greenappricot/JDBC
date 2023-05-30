package com.web.common.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordEncryptWrapper extends HttpServletRequestWrapper {

	public PasswordEncryptWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String key) {
		// super -> HttpServletRequestWrapper 
		// super.getParameter(key); -> 원본
		String oriVal=super.getParameter(key);
		if(key.equals("password")) {
			// 암호화 처리 로직 작성
			System.out.println("원본 : "+oriVal);
			String encryptData=getSHA512(oriVal);
			System.out.println("암호화 값 : "+encryptData);
			return encryptData;
		} 
//			return super.getParameter(key); 
			return oriVal;
	}
	
	private String getSHA512(String oriVal) {
		// 단방향 암호화 처리하기
		// java에서 제공하는 클래스와 메소드를 이용한다.
		// MessageDigest 클래스 이용하기
		// java가 제공하는 암호화 알고리즘 적용하기
		MessageDigest md=null;
		try {
			md= MessageDigest.getInstance("SHA-512"); // 암호화할 수 있는 MessageDigest 구현
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 생성된 MessageDigest 클래스를 이용해서 원본값 암호화처리
		// 암호화처리는 byte 단위로 쪼개서 처리한다.(매개변수로 받은 String을 byte 배열로 변환한다)
		byte[] oriValByte=oriVal.getBytes();
		md.update(oriValByte); // md에 원본값을 byte 형식으로 넣음
		byte[] encryptData=md.digest(); // byte 배열로 암호화된 방식으로 encryptData에 저장한다. -> byte로 적용된 상태 
		// byte 상태이므로 String으로 처리할 수 있게 처리한다. 처리한 String을 반환한다.
		String encryptStrData= Base64.getEncoder().encodeToString(encryptData);
		
		return encryptStrData;
	}

}
