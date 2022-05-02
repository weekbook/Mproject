package kr.icia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.icia.domain.MemberVO;
import kr.icia.mapper.MemberMapper;
import kr.icia.security.domain.CustomUser;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
   @Setter(onMethod_ = { @Autowired })
   private MemberMapper memberMapper;
   // ���� ������ ���� ���� �������̽� �ʱ�ȭ

   @Override
   public UserDetails loadUserByUsername(String username) 
         throws UsernameNotFoundException {
      log.warn("load user by userName : " + username);
      MemberVO vo = memberMapper.read(username);
      // ���޵� id�� ����� ������ �˻�.

      return vo == null ? null : new CustomUser(vo);
      // �˻����� ������ ��, �˻��Ǹ� �ش� ���� ����.
   }

}


