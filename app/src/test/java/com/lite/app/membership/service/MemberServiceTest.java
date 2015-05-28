package com.lite.app.membership.service;

import com.lite.app.framework.test.BaseTest;
import com.lite.app.membership.model.Member;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.List;

@TransactionConfiguration(defaultRollback = false)
public class MemberServiceTest extends BaseTest{

    @Autowired
    private MemberService memberService;

    @Test
    public void saveTest(){
        /*Member member = new Member();
        member.setName("321321");
        member.setAge(44);
        member.setIsDelete(false);
        memberService.save(member);
        memberService.delete(member.getId());*/
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(118);
        List<Member> members = memberService.getByIds(ids);
        for(Member member : members){
            System.out.println(member.getName());
        }
        Member member = new Member();
        member.setName("21");
        member.setAge(21);
        boolean exists = memberService.exists(member,1);
        Assert.assertEquals(true, exists);
    }

}
