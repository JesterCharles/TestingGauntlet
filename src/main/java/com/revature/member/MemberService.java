package com.revature.member;

import com.revature.utility.exceptions.InsufficientBalanceException;
import com.revature.utility.exceptions.RecordExistsException;

import java.math.BigDecimal;
import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member register(Member newMember) {
        checkForExistingMember(newMember.getEmail());
        return memberRepository.create(newMember);
    }


    public BigDecimal getMemberBalance(String email) {
        Member member = getMember(email);
        return member.getBalance();
    }

    public BigDecimal updateBalance(String email, BigDecimal amount) {
        Member member = getMember(email);
        if((member.getBalance().add(amount).compareTo(BigDecimal.ZERO)) < 0) {
            throw new InsufficientBalanceException("Unfortunately the amount exceeds your current balance of " + member.getBalance());
        } else {
            member.setBalance(member.getBalance().add(amount));
            memberRepository.update(member);
            return member.getBalance();
        }

    }

    private Member getMember(String email) {
        return memberRepository.findById(email);
    }

    private boolean checkForExistingMember(String email){
        Member member = getMember(email);
        if(member  == null)
            return true;
        else
            throw new RecordExistsException("This member already exists, please try logging in with email or registering with a new email");
    }

}
