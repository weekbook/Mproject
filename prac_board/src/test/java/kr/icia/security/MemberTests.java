package kr.icia.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.icia.mapper.MemberMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		{"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberTests {
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder; // 비번 암호화 객체
	
	@Setter(onMethod_ = @Autowired) // 오라클 연동 객체
	private DataSource ds;
	
	@Setter(onMethod_ = {@Autowired }) // {}중괄호는 여러개 값을 배열처럼 전달하고자 할때 사용
	private MemberMapper memberMapper;
	
//	@Test
//	public void testInsertMember() {
//		String sql = "insert into tbl_member(userid, userpw "+",username) values(?,?,?)";
//		// 과거 쿼리문 처리 방식
//		// ?물음표에 순차적으로 변수값이 할당.
//		for(int i = 0; i<100; i++) {
//			Connection con = null; // 오라클과 연동을 위한 커넥션
//			PreparedStatement pstmt = null; // 쿼리문을 자바에서 오라클로 전달하기 위한 객체
//			try {
//				con = ds.getConnection(); // 데이터 소스를 통해서 커넥션을 얻어내고,
//				pstmt = con.prepareStatement(sql); // 위의 쿼리문을 preparedStatement 객체로 전환.
//				pstmt.setString(2, pwencoder.encode("pw" + i)); // 2번째 항목 비번 할당.
//				if (i<80) { // 0~79 일반사용자
//					pstmt.setString(1,  "user" + i); // 1번째 항목 userid 할당
//					pstmt.setString(3,  "일반사용자" + i); //3 3번째 항목 username 할당
//				} else if (i < 90) { // 80~89 운영자
//					pstmt.setString(1,  "manager" + i); 
//					pstmt.setString(3,  "운영자" + i);
//				} else { // 90~99 관리자
//					pstmt.setString(1,  "admin" + i);
//					pstmt.setString(3,  "관리자" + i);
//				}
//				pstmt.executeUpdate(); // 오라클로 전달되는 쿼리문이 실행.
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (Exception e) {}
//				}
//				if(con!=null) {
//					try {
//						con.close();
//					}catch (Exception e) {}
//				}
//			}
//		}
//	} // 인서트
	
	@Test
	public void testInsertAuth() {
		String sql = "insert into tbl_member_auth (userid, auth)"
				+ " values(?,?)";
		for(int i=0; i<100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				if (i<80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, "ROLE_USER");
				}else if(i<90) {
					pstmt.setString(1, "manager" + i);
					pstmt.setString(2, "ROLE_MEMBER");
				} else {
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e) {}
				}
				if(con!=null) {
					try {
						con.close();
					}catch (Exception e) {}
				}
			}
		}
	}
	
}
