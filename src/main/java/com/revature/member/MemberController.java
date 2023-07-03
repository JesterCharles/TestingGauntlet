package com.revature.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class MemberController {

    private final MemberService memberService;
    private Javalin app;
    private final ObjectMapper objectMapper;

    public MemberController(Javalin app, MemberService memberService, ObjectMapper objectMapper){
        this.memberService = memberService;
        this.app = app;
        this.objectMapper = objectMapper;
        generateEndpoints();
    }

    private void generateEndpoints(){
        app.get("customers", this::getAllMembers);
        app.get("balance/{email}", this::checkBalance);
        app.post("register", this::registerMember);
        app.put("withdrawal", this::withdrawalBalance);
        app.put("deposit", this::depositBalance);
    }

    private void getAllMembers(Context context){
        List<Member> members = memberService.findAll();
        context.json(members);
    }

    private void registerMember(Context context) throws JsonProcessingException {

        Member newMember = objectMapper.readValue(context.body(), Member.class);
        Member registeredMember = memberService.register(newMember);

        context.json(registeredMember);
        context.status(202);
    }

    private void checkBalance(Context context){
        String email = context.pathParam("email");
        BigDecimal balance = memberService.getMemberBalance(email);
        context.json("Available balance: " + balance);
    }

    private void withdrawalBalance(Context context){
        String email = context.queryParam("email");
        BigDecimal amount = new BigDecimal(Objects.requireNonNull(context.queryParam("amount")));
        BigDecimal balance = memberService.updateBalance(email, amount.negate());
        context.json("Available balance: " + balance);
    }

    private void depositBalance(Context context){
        String email = context.queryParam("email");
        BigDecimal amount = new BigDecimal(Objects.requireNonNull(context.queryParam("amount")));
        BigDecimal balance = memberService.updateBalance(email, amount);
        context.json("Available balance: " + balance);
    }







}
