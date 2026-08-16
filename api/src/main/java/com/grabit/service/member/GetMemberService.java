package com.grabit.service.member;

import com.grabit.bean.member.MemberDTO;

public interface GetMemberService {

    public MemberDTO getMemberData(String email, String mobile);
}
