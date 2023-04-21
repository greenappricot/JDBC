package com.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.model.vo.Member;

public class BasicJdbcTest {

	public static void main(String[] args) {
		// jdbc를 이용해서 오라클과 연동하기
		// 1. 오라클에서 제공하는 ojdbc.jar 파일을 사용하고 있는 java 버전에 맞춰서 다운로드
		// 2. 이클립스에서 프로젝트 생성하고 생성된 프로젝트 라이브러리에 다운 받은 jar파일 추가한다. (인코딩도 해주기)
		
		// 프로젝트(application)에서 DB에 접속하기
		// 1. jar파일이 제공하는 클래스가 있는지 확인하기 -> 없으면 error 발생하므로 예외처리 필수 (ClassNotFoundException -> 발생시 jar파일 확인)
		// 내가 가지고 있는 라이브러리에 이런 패키지가 있는지 확인하는 구문
		Connection conn=null;
		Statement stmt= null;
		ResultSet rs= null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			// 2. DriverManager 클래스를 이용해서 접속하는 객체를 만들어준다.
			// DriverManager 클래스가 제공하는 getConnection() static method를 이용해서 Connection 객체를 가져온다. 
			// 								-> getConnection() 메소드는 Connection객체를 반환한다.
			// getConnection -> 3개의 매개변수가 선언되어있다.
			// 첫번째 인수 String : 접속할 DB의 주소, 버전정보, 포트번호
			// 						접속할 DBMS마다 문자열 패턴이 정해져있다. 
			//			   오라클 : jdbc:oracle:thin:@ip주소:포트번호:버전
			//				    ex) jdbc:oracle:thin:@goodee.ddns.net:10000:xe(oracle expire버전쓰고 있어서)
			// 두번째 인수 String : DB접속 계정 ID
			// 세번째 인수 String : DB접속 계정 비밀번호
			conn= DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","STUDENT","STUDENT");

			// jdbc객체가 알아서 auto commit 해버린다. -> false 처리해준다 
			// true일 때 commit, rollback 하면 error 발생한다.
			conn.setAutoCommit(false); 			// 트랜잭션을 개발자가 직접 처리하겠다 
			
			System.out.println("DB접속확인 완료 :)");
			
			// 3. 접속된 DB에 sql문을 실행하고 결과를 가져와야한다.
			// sql문을 실행하기 위해서 실행할 객체가 필요하다.
			// Statement, PreparedStatement : 문자열로 작성한 sql구문을 연결된 DB에서 실행하는 객체
			// sql문을 실행하려면 Statement의 멤버 메소드인 executeQuery(), executeUpdate() 메소드를 이용한다.
			// 				   SELECT : executeQuery() 실행 -> (조회한 결과인) ResultSet객체 반환 
			// INSERT, UPDATE, DELETE : executeUpdate() 실행 -> int 반환
			
				// 3-1) 쿼리문 작성하기
				// MEMBER 테이블에 있는 아이디가 admin인 사원 조회하기
				// SELECT * FROM MEMBER WHERE MEMBER_ID='admin';
				// 문자열변수에 sql문 저장할 때는 ';'를 생략 -> error 발생함
//				String sql= "SELECT * FROM MEMBER WHERE MEMBER_ID='admin'";
				
				// 등록된 전체 회원 가져오기
				String sql="SELECT * FROM MEMBER";
			
				// 3-2) (쿼리문 실행시킬) Statement 객체 가져오기
				// Connection클래스가 제공하는 createStatement() 메소드 이용
				stmt= conn.createStatement(); //SQLException 처리 필수
			
				// 3-3) 쿼리문 실행하기
				// Statement가 제공하는 executeQuery() 실행하고 ResultSet객체로 반환받는다
				rs= stmt.executeQuery(sql);
				System.out.println(rs);
			
			// 4. ResultSet 이용 방법 
			// 반환된 SELECT문의 실행결과는 ResultSet 객체가 제공하는 메소드를 이용해서 컬럼별 값을 가져온다.
			// next() : 데이터의 row를 지정 -> row 데이터를 가져온다 / 반환형 : boolean (반복문 사용해서 데이터를 가져올 수 있다)
				
			// get자료형("컬럼명"||인덱스번호) : type에 맞춰서 자료형을 가져온다.
			// getString() : char, varchar2, nchar, nvarchar2 자료형을 가져올 때
			// getInt() / getDouble() : number 자료형을 가져올 때 
			// getDate() / getTimeStamp() : date, timestamp 자료형을 가져올 때
			
//			rs.next(); // 1번째 row를 지칭
//			String memberId= rs.getString("member_id"); // 컬럼명과 테이블명은 대소문자 구분하지 않는다.
//			String memberPwd= rs.getString("member_pwd");
//			int age= rs.getInt("age");
//			Date enrollDate= rs.getDate("enroll_date");
//			System.out.println(memberId+" "+memberPwd+" "+age+" "+enrollDate);
				
//			System.out.println(rs.next()); // false값 나옴 -> where절에 pk값 조건이 있을 때, 조회할 다음 값이 없기 때문에 마지막 행 다음의 결과 집합 오류 발생 
//			memberId= rs.getString("member_id"); 
//			memberPwd= rs.getString("member_pwd");
//			age= rs.getInt("age");
//			enrollDate= rs.getDate("enroll_date");
//			System.out.println(memberId+" "+memberPwd+" "+age+" "+enrollDate);
			
			// 반복문을 이용해서 MEMBER table의 모든 row값 조회하기
//			while(rs.next()) {
//				String memberId= rs.getString("member_id"); // 컬럼명과 테이블명은 대소문자 구분하지 않는다.
//				String memberPwd= rs.getString("member_pwd");
//				int age= rs.getInt("age");
//				Date enrollDate= rs.getDate("enroll_date");
//				System.out.println(memberId+" "+memberPwd+" "+age+" "+enrollDate);
//			} 
			
			// DB의 row를 가져올 때, java에서는 class로 저장해서 관리한다. (array나 list로 저장할 수 있다)
				
				// 하나의 row 가져올 때 예시
//			Member m= new Member();
			List<Member> memberList= new ArrayList<Member>();
			while(rs.next()) {
				Member m= new Member(); // while문 밖에 선언하면 최종값이 하나만 출력됨 
				m.setMemberId(rs.getString("member_id")); 
				// m.setMemberId(rs.getString(1)); -> 1번째 컬럼값 가져옴 -> 가독성 떨어져서 추천하지 않음 
				m.setMemberPwd(rs.getString("member_pwd"));
				m.setMemberName(rs.getString("member_name"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enroll_date"));
				memberList.add(m);
			}
			memberList.forEach((m)->System.out.println(m));
		
				
//			내가 작성한 코드
//			Collection<Member> memberData= new ArrayList<Member>();
//			while(rs.next()) {
//				String memberId= rs.getString("member_id");
//				String memberPwd= rs.getString("member_pwd");
//				String memberName= rs.getString("member_name");
//				String gender= rs.getString("gender");
//				int age= rs.getInt("age");
//				String email= rs.getString("email");
//				String phone= rs.getString("phone");
//				String address= rs.getString("address");
//				String hobby= rs.getString("hobby");
//				Date enrollDate= rs.getDate("enroll_date");
				
//				memberData.add(new Member(memberId, memberPwd, memberName, gender, age, email, phone, address, hobby, enrollDate));
//			}
//			memberData.forEach((l)->System.out.println(l));
			
				// DML 구문 실행하기
				// insert, update, delete문
				// 트랜잭션 처리 해줘야한다.
				// 1. sql문 작성 -> literal 형태에 맞춰서 작성해야한다. (String-> '', number-> 0, ...)
				sql= "INSERT INTO MEMBER VALUES('inhoru','inhoru','최인호','M',26,'inhoru@inhoru.com','0103829502','금천구','영화감상, 애니감상, 코딩',sysdate)";
				int result= stmt.executeUpdate(sql); // 1 출력됨
				
				// 트랜잭션 구문으로 처리하기 (insert, update, delete 작업이 많아질 때 필수로 처리해야한다.)
				if(result>0) conn.commit(); 
				else conn.rollback();
				System.out.println(result);
			
						
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			// 5. 생성한 객체를 반드시 반환해줘야한다.
			// Connection, Statement, [ResultSet : select할 때만 생성됨]
			// close() 메소드를 이용해서 반환한다.
			// 생성한 역순으로 반환해야한다.
			// rs, stmt, conn 선언하기 위해 try-catch문 밖에서 객체 초기화한다 -> 예외처리때문에 try-catch문 또 사용한다.
			
			try {
				// 순서에 주의! 최근에 만들어준 메소드부터 닫아줘야한다.
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		

	}

}


















