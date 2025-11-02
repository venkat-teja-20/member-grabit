package com.grabit.service.security;

import com.grabit.bean.auth.PermissionDTO;
import com.grabit.entity.Member;
import com.grabit.feign.AuthInterface;
import com.grabit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthInterface authInterface;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member=memberRepository.findMemberByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().toValue()));
        List<PermissionDTO> permissionDTOList=authInterface.getPermissions(member.getRole().toValue()).getPermissions();
        authorities.addAll(permissionDTOList.stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission().toValue())).toList());
        return new User(email,member.getPassword(),Boolean.parseBoolean(member.getIsActive().toString()),true,true,true,authorities);
    }
}
