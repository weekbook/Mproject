package kr.icia.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.icia.domain.MemberVO;

public class CustomUser extends User {
   private static final long serialVersionUID = 1L;
   private MemberVO member;
   // ��񿡼� ������ ȸ������ �ʱ�ȭ.

   public CustomUser(String username, String password
         , Collection<? extends GrantedAuthority> authorities) {
      super(username, password, authorities);
      // ����� �����鼭 �ǹ������� ������ ������.
      // <? extends Ŭ������> : ���ʸ� Ÿ���� ���� ����.
      // <? super Ŭ������> : ���ʸ� Ÿ���� ���� ����.
      // <?> : ���ʸ� Ÿ�� ���� ����.

   }
   
   public CustomUser(MemberVO vo) {
      super(vo.getUserid(), vo.getUserpw()
            , vo.getAuthList().stream()
            .map(auth -> new SimpleGrantedAuthority(
                  auth.getAuth())).collect(Collectors.toList()));
      // ��񿡼� ������ ���̵�, �н�����, ������ �����ͼ� ������ �ʱ�ȭ ��.
      this.member = vo;
      // ����� ���̵�, �н�����, ���� ������� �ʱ�ȭ.
   }   
   // ����ڰ� �α��� â���� ���̵�� �н����带 �Է��ϸ�,
   // �ش� ���̵� ������ ��ġ�ϴ� ȸ�� ������ ã��.(���� ó��)
}