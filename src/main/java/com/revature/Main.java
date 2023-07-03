package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.member.Member;
import com.revature.member.MemberController;
import com.revature.member.MemberRepository;
import com.revature.member.MemberService;
import io.javalin.Javalin;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
                    config.plugins.enableCors(cors ->{
                        cors.add(it -> {
                            it.anyHost();
                            it.exposeHeader("Authorization");
                        });
                    });
                }

        ).start(8080);

        ObjectMapper objectMapper = new ObjectMapper();
        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        MemberController memberController = new MemberController(app, memberService, objectMapper);


        Member member1 = new Member("admin@mail.com", "admin".toCharArray(), "admin", "admin", new BigDecimal("5700.28"));
        Member member2 = new Member("cjester@mail.com", "pass1".toCharArray(), "admin", "admin", new BigDecimal("5700.28"));
        Member member3 = new Member("benserf@mail.com", "pass2".toCharArray(), "admin", "admin", new BigDecimal("5700.28"));

        memberRepository.create(member1);
        memberRepository.create(member2);
        memberRepository.create(member3);
    }
}