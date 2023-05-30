package com.web.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

// 양방향 암호화하는 메소드를 제공하는 클래스
// java에서 제공하는 클래스를 이용해서 양방향 암호화 진행
// java.crypto 패키지, java.security 패키지에서 제공하는 클래스를 이용한다. 
// 양방향 암호화는 암호화와 복호화를 할 수 있는 key가 있어야한다.
// 대칭키 암호화 : 암호화, 복호화 key가 하나 -> AES 방식의 암호화 알고리즘
// 비대칭키 암호화 : 암호화 key 1개(공개key), 복호화 key 1개(개인key) 2개를 한 쌍으로 한다. -> RSA방식의 암호화 알고리즘

// 1. key 생성하기
// 2. 암호화 메소드 선언
// 3. 복호화 메소드 선언

public class AESEncryptor {
	private static SecretKey key; // 암호화 키 저장하는 객체 생성 -> 특정 위치에 file로 저장하고 호출해서 사용한다.
	private String path; // 키가 파일로 저장된 위치를 나타내는 변수
	
	// 기본 생성자 
	public AESEncryptor() {
		// key 값을 생성하거나 가져오기
		// 1. 생성된 키가 있으면 저장된 파일에서 key를 가져와서 key변수에 저장한다.
		// 2. 생성된 키가 없으면 SecretKey 클래스를 생성해서 key 변수에 저장한다.
		// key를 저장할 파일명은 hr.today;
		this.path=AESEncryptor.class.getResource("/").getPath(); // classes 폴더에 저장됨.
		this.path=this.path.substring(0,this.path.indexOf("classes"));
		System.out.println(path);
		File keyFile= new File(this.path+"hr.today");
		if(keyFile.exists()) {
			try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream(keyFile));) {
				this.key=(SecretKey)ois.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			generatorKey(keyFile);
		}
	}
	
	private void generatorKey(File keyFile) {
		// java.crypto 패키지에서 제공하는 keyGenerator클래스를 이용해서 SecretKey 생성한다.
		// key 생성시 salt값이 필요하다.
		SecureRandom rnd= new SecureRandom();
		KeyGenerator keygen= null;
		
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(keyFile))) {
			keygen= KeyGenerator.getInstance("AES");
			keygen.init(128,rnd); // 128bit 랜덤값으로 key값을 초기화한다.
			AESEncryptor.key=keygen.generateKey(); // key값 생성해서 멤버변수에 저장한다. ( -> 매번 새로 생성될 수 없으므로 -> resource 방식으로 )
			// file에 저장한다. 
			oos.writeObject(key);
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}//
	
	// 암호화메소드
	public static String encryptData(String oriData) throws Exception {
		// 암호화 처리하는 메소드 
		// 매개변수로 받은 oriData를 SecretKey로 처리해서 암호화한다. -> base64 encoder 이용해서 변환한다. 
		Cipher cipher= Cipher.getInstance("AES"); // AES방식으로 처리한 값을 갖는 Cipher 생성
		cipher.init(Cipher.ENCRYPT_MODE, AESEncryptor.key); // cipher 초기화
		
		// 매개변수로 받는 oriData를 byte형식의 배열 분리해서로 저장한다. 
		byte[] targetData= oriData.getBytes(Charset.forName("UTF-8")); // 다시 byte 형태를 조합해서 String 값으로 반환해야 하므로 encoding 형식을 적어준다.
		byte[] encryptData= cipher.doFinal(targetData); // 매개변수로 들어온 targetData를 AES형식으로 암호화 처리해서 byte 배열로 저장한다
		
		return Base64.getEncoder().encodeToString(encryptData); // byte 배열을 String으로 변환해서 반환한다. 
	}
	
	// 복호화메소드 (encrypt -> decrypt) (byte->String)
	public static String decryptData(String encryptData) throws Exception {
		// 암호화 한 encryptData를 byte로 변환
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, AESEncryptor.key); // 암호화 할 때와 같은 키를 이용해야 한다. -> 그래야 암호화된 키가 복호화된다.
		
		byte[] encrypt= Base64.getDecoder().decode(encryptData.getBytes(Charset.forName("UTF-8")));
		byte[] decryptData= cipher.doFinal(encrypt);
		
		return new String(decryptData);
	}
	
	
}
