package com.grabit.service.security;

import com.grabit.entity.Member;
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

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        Member member=memberRepository.findByPhoneNumber(mobile).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().getRole().name()));
        authorities.addAll(member.getRole().getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission().name())).toList());
        return new User(mobile,member.getPassword(),Boolean.parseBoolean(member.getIsActive().toString()),true,true,true,authorities);
    }
}
