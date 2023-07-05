package com.revature.member;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTestSuite {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService sut;

    @Test
    public void test_givenFindAllMembers_ReturnAllAvailableMembers(){
        // Arrange
        List<Member> memberList = new ArrayList<>();
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();

        memberList.add(member1);
        memberList.add(member2);
        memberList.add(member3);
        when(memberRepository.findAll()).thenReturn(memberList);


        // Act
        int actualSize = sut.findAll().size();
        verify(memberRepository, times(1)).findAll();
        verifyNoMoreInteractions(memberRepository);

        // Assert
        Assertions.assertEquals(3, actualSize);
    }


    @Test
    public void test_givenGetMemberBalanceWithValidEmail_ReturnBalance(){
        // Arrange
        Member validMember = new Member("valid@mail.com", "password".toCharArray(), "valid_fname", "valid_lname", new BigDecimal("100.00"));
        when(memberRepository.findById(validMember.getEmail())).thenReturn(validMember);

        // Act
        BigDecimal actualBalance = sut.getMemberBalance(validMember.getEmail());
        verify(memberRepository, times(1)).findById(validMember.getEmail());
        verifyNoMoreInteractions(memberRepository);

        // Assert
        Assertions.assertEquals(validMember.getBalance(), actualBalance);
    }


    public void test_givenRegisterExistingMember_ThrowsRecordExistsException(){

    }


    public void test_givenRegisterValidNewMember_ReturnNewMember(){


    }


    public void test_givenWithdrawalUpdateBalance_ReturnsNewBalance(){


    }


    public void test_givenDepositUpdateBalance_ReturnsNewBalance(){

    }


    public void test_givenWithdrawalUpdateBalanceWithInsufficientFunds_ThrowsInsufficientBalanceException(){

    }
}
