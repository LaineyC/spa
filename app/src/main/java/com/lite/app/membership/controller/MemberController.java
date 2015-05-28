package com.lite.app.membership.controller;

import com.lite.app.framework.bean.File;
import com.lite.app.framework.controller.BaseController;
import com.lite.app.framework.util.FileUtil;
import com.lite.app.membership.model.Member;
import com.lite.app.membership.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MemberController extends BaseController{

    @Autowired
    private MemberService memberService;

    @ResponseBody
    @RequestMapping(params="method=membership.member.get")
    public Member get(String id) {
        Member member = new Member();
        member.setName("321321");
        member.setAge(44);
        member.setIsDelete(false);
        memberService.save(member);

        //member.setId(11);
        member.setName("hdadadaha");
        memberService.update(member);
        /*List<Member> members = new ArrayList<Member>();
        members.add(member);
        memberService.updateBatch(members);*/
        //memberService.delete(member.getId());

      /*  memberService.get(1);

        List<Member> members = new ArrayList<Member>();
        for(int i = 0 ; i < 5 ; i++){
            Member m = new Member();
            m.setName("321321");
            m.setAge(44);
            m.setIsDelete(false);
            members.add(m);
        }
        memberService.saveBatch(members);*/
       /* List<Integer> ids = new ArrayList<Integer>();
        ids.add(80);
        ids.add(81);
        memberService.deleteBatch(ids);*/

        return member;
    }

    @ResponseBody
    @RequestMapping(params="method=membership.member.edit")
    public Member edit(@RequestBody Member member){
        File headFile = member.getHeadFile();
        FileUtil.write(headFile,"E:/Temp");
        member.setHeadFile(null);
        return member;
    }

}
