package com.hello.core.discount;

import com.hello.core.member.Member;

public interface DisCountPolicy {
    int discount(Member member, int price);

}
