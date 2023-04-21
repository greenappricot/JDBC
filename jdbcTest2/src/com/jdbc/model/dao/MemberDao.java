package com.jdbc.model.dao;

import static com.jdbc.common.JdbcTemplete.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jdbc.common.JdbcTemplete;
import com.jdbc.model.dto.MemberDto;

public class MemberDao {
	
	private Properties sql= new Properties(); // sql 구문 적어놓은 properties 연결 -> 기본 생성자나 초기화블럭으로 만들어서 사용할 수 있다.
	
	{
		try {
			String path=MemberDao.class.getResource("/sql/member/member_sql.properties").getPath();
			//                                       /패키지이름/파일 이름.properties
			sql.load(new FileReader(path));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
//	public List<MemberDto> selectAllMember(Connection conn){ // 연결 정보를 매개변수로 반환받는다. 
//		Statement stmt= null; // Statement는 접속할 때마다 만든다 -> 공용으로 두고 같이 사용할 수 없음 접속 정보만 공통으로 사용할 수 있다. 
//		ResultSet rs= null;
//		String sql="SELECT * FROM MEMBER";
//		List<MemberDto> members= new ArrayList<MemberDto>();
//		try {
//			stmt= conn.createStatement();
//			rs= stmt.executeQuery(sql);
//			while(rs.next()) members.add(getMember(rs));
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//				close(rs);
//				close(stmt);
//		}
//		return members;
//	}//
	public List<MemberDto> selectAllMember(Connection conn){ // 연결 정보를 매개변수로 반환받는다. 
		Statement stmt= null; // Statement는 접속할 때마다 만든다 -> 공용으로 두고 같이 사용할 수 없음 접속 정보만 공통으로 사용할 수 있다. 
		ResultSet rs= null;
//		String sql="SELECT * FROM MEMBER";
		String sql=this.sql.getProperty("selectMemberAll"); // 초기화블럭 내의 sql 파일의 selectMemberAll을 가져온다. 
		List<MemberDto> members= new ArrayList<MemberDto>();
		try {
			stmt= conn.createStatement();
			rs= stmt.executeQuery(sql);
			while(rs.next()) members.add(getMember(rs));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
				close(rs);
				close(stmt);
		}
		return members;
	}//
	
	public MemberDto selectMemberById(Connection conn, String id) {
		// PreparedStatement : SQL문에 변수값을 대입할 때, 자료형에 맞춰서 편리하게 대입할 수 있다
		//                     Statement를 상속받은 클래스 
		
		PreparedStatement pstmt= null;
		ResultSet rs= null;
		MemberDto m= null;
		
		// PreparedStatement 객체 이용해서 sql문을 실행하려면 Statement와 차이가 있다. 
		// 외부의 값을 받아서(매개변수) sql문을 구성할 때, 표현과 값 대입 방식이 다르다.
		// Statement : 자료형 표시하면서 전부 입력해야한다. 
		//             "SELECT * FROM MEMBER WHERE MEMBER=ID='"+변수명+"'";
		// PreparedStatement : 외부값을 "?" 표시해서 쿼리문을 작성한다. -> 값을 넣는 메소드를 입력
		//                     "SELECT * FROM MEMBER WHERE MEMBER_ID=?" 
//		String sql="SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		String sql=this.sql.getProperty("selectMemberById"); 
		// 코드 상 sql쿼리문을 확인할 수 없다. 
		
		try {
			// PreparedStatement는 conn.preparedStatement()메소드를 이용
			// 메소드의 인수로 sql문을 대입해준다. 
			pstmt= conn.prepareStatement(sql); // 선언 전에 sql문 선언해야한다. -> statement는 execute할 때 대입함
			// ? 표시되어있는 곳의 값을 대입해줘야한다.
			// 대입 : PreparedStatement가 제공하는 메소드를 이용
			// PreparedStatement.set자료형(String, Int, Date...) (위치값, 대입할값);
			// 위치값 : 1부터 시작하여 왼->오 / (ex. 첫번째 ? : 1, 두번째 ? : 2, ...)
			pstmt.setString(1, id);
			
			// "?"(위치홀더)로 표시한 값이 있으면 반드시 값을 대입해줘야한다 -> 대입하지 않으면 Exception 발생함
			// ORA-17041 : 인덱스에서 누락된 IN 또는 OUT 매개변수 :: 1 -> 위치홀더 1번에 대한 값이 없다. 
			
			rs=pstmt.executeQuery(); // 내부에서 직접 가지고 있기 때문에 ()안에 값 안 넣어도 된다
			if(rs.next()) m=getMember(rs);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return m;
	}//selectMemberById close
	
	public List<MemberDto> selectMemberByName(Connection conn, String name) {
		List<MemberDto> m= new ArrayList<MemberDto>();
		PreparedStatement pstmt= null;
		ResultSet rs= null;
//		String sql= "SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE ?";
		// '%'||?||'%' -> '% name %'로 인식된다 
		// '%?%' -> '%'name'%' 로 인식되기 때문에 name 컬럼을 찾는다 
		
		String sql= this.sql.getProperty("selectMemberByName");
		
		try {
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, "%"+name+"%");
			rs= pstmt.executeQuery();
			while(rs.next()) m.add(getMember(rs));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return m;
	}
	
	
	public int insertMember(Connection conn, MemberDto m) {
		int result=0;
		PreparedStatement pstmt= null;
//		String sql= "INSERT INTO MEMBER VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		String sql= this.sql.getProperty("insertMember");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPwd());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, String.valueOf(m.getGender()));
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, String.join(",",m.getHobby()));
			
			result= pstmt.executeUpdate(); // 생성할 때부터 쿼리문을 가지고 있기 때문에 매개변수를 넣지 않는다. (! Statement는 넣음)
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTemplete.close(pstmt);
		}
		return result;
	}
	
	public int updateMember(Connection conn, MemberDto m) {
		PreparedStatement pstmt= null;
		int result=0;
//		String sql="UPDATE MEMBER SET MEMBER_NAME=?, AGE=?, EMAIL=?, ADDRESS=? WHERE MEMBER_ID=?";
		String sql=this.sql.getProperty("updateMember");
//		updateMember= UPDATE MEMBER SET MEMBER_NAME=?, AGE=?, EMAIL=?, ADDRESS=?, WHERE MEMBER_ID=?
//		이름, 나이, 이메일, 주소
		try {
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1,m.getMemberName());
			pstmt.setInt(2,m.getAge());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getMemberId());
			
			result= pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTemplete.close(pstmt);
		}
		return result;
	}
	
	public int deleteMember(Connection conn, String id) {
		int result=0;
		PreparedStatement pstmt= null;
//		String sql="DELETE MEMBER WHERE MEMBER_ID=?";
		String sql=this.sql.getProperty("deleteMember");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTemplete.close(pstmt);
		}
		
		return result;
	}
	
	
	// DB데이터 Table에 있는 row의 각 컬럼의 값을 가져와서 m의 필드값에 입력하는 메소드
	private MemberDto getMember(ResultSet rs) throws SQLException { 
		MemberDto m= new MemberDto();
		m.setMemberId(rs.getString("member_id"));
		m.setMemberPwd(rs.getString("member_pwd"));
		m.setMemberName(rs.getString("member_name"));
		m.setGender(rs.getString("gender").charAt(0));
		m.setAge(rs.getInt("age"));
		m.setEmail(rs.getString("email"));
		m.setPhone(rs.getString("phone"));
		m.setAddress(rs.getString("address"));
		m.setHobby(rs.getString("hobby").split(","));
		m.setEnrollDate(rs.getDate("enroll_date"));
		return m;
	}
	
}

















