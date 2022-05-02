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
	private PasswordEncoder pwencoder; // ��� ��ȣȭ ��ü
	
	@Setter(onMethod_ = @Autowired) // ����Ŭ ���� ��ü
	private DataSource ds;
	
	@Setter(onMethod_ = {@Autowired }) // {}�߰�ȣ�� ������ ���� �迭ó�� �����ϰ��� �Ҷ� ���
	private MemberMapper memberMapper;
	
//	@Test
//	public void testInsertMember() {
//		String sql = "insert into tbl_member(userid, userpw "+",username) values(?,?,?)";
//		// ���� ������ ó�� ���
//		// ?����ǥ�� ���������� �������� �Ҵ�.
//		for(int i = 0; i<100; i++) {
//			Connection con = null; // ����Ŭ�� ������ ���� Ŀ�ؼ�
//			PreparedStatement pstmt = null; // �������� �ڹٿ��� ����Ŭ�� �����ϱ� ���� ��ü
//			try {
//				con = ds.getConnection(); // ������ �ҽ��� ���ؼ� Ŀ�ؼ��� ����,
//				pstmt = con.prepareStatement(sql); // ���� �������� preparedStatement ��ü�� ��ȯ.
//				pstmt.setString(2, pwencoder.encode("pw" + i)); // 2��° �׸� ��� �Ҵ�.
//				if (i<80) { // 0~79 �Ϲݻ����
//					pstmt.setString(1,  "user" + i); // 1��° �׸� userid �Ҵ�
//					pstmt.setString(3,  "�Ϲݻ����" + i); //3 3��° �׸� username �Ҵ�
//				} else if (i < 90) { // 80~89 ���
//					pstmt.setString(1,  "manager" + i); 
//					pstmt.setString(3,  "���" + i);
//				} else { // 90~99 ������
//					pstmt.setString(1,  "admin" + i);
//					pstmt.setString(3,  "������" + i);
//				}
//				pstmt.executeUpdate(); // ����Ŭ�� ���޵Ǵ� �������� ����.
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
//	} // �μ�Ʈ
	
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
