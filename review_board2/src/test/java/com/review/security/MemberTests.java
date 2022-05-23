package com.review.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.review.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		{"file:src/main/webapp/WEB-INF/spring/root-context.xml",
			"file:src/main/webapp/WEB-INF/spring/security-context.xml"
		})
@Log4j
public class MemberTests {
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwEncoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
//	@Test
//	public void testInertMember() {
//		String sqlString = "insert into tbl_member(userid, userpw" + ",username) values(?,?,?)";
//		for (int i = 0; i < 100; i++) {
//			Connection connection = null;
//			PreparedStatement pStatement = null;
//			try {
//				connection = ds.getConnection();
//				pStatement = connection.prepareStatement(sqlString);
//				pStatement.setString(2, pwEncoder.encode("pw"+i));
//				
//				if (i<80) {
//					pStatement.setString(1, "user" + i);
//					pStatement.setString(3, "일반사용자" + i);
//				}else if (i<90) {
//					pStatement.setString(1, "manager" + i);
//					pStatement.setString(3, "운영자" + i);
//				} else {
//					pStatement.setString(1, "admin" + i);
//					pStatement.setString(3, "관리자" + i);
//				}
//				pStatement.executeUpdate();
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				if (pStatement != null) {
//					try {
//						pStatement.close();
//					} catch (Exception e2) {}
//				}
//				if (connection!=null) {
//					try {
//						connection.close();
//					} catch (Exception e2) {}
//				}
//			}
//		}
//	}
	
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
