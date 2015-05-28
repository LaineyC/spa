package com.lite.app.membership.service;

import com.lite.app.framework.service.BaseService;
import com.lite.app.membership.dao.MemberDao;
import com.lite.app.membership.model.Member;
import com.lite.app.membership.po.MemberPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class MemberServiceImpl extends BaseService implements MemberService{

    @Autowired
    private MemberDao memberDao;

    public Member save(Member member){
        MemberPo memberPo = mapper.map(member,MemberPo.class);
        memberDao.insert(memberPo);
        member.setId(memberPo.getId());
        return member;
    }

    public int saveBatch(Collection<Member> members){
        List<MemberPo> memberPos = new ArrayList<MemberPo>();
        for(Member member : members){
            MemberPo memberPo = mapper.map(member,MemberPo.class);
            memberPos.add(memberPo);
        }
        int count = memberDao.insertBatch(memberPos);
        return count;
    }

    public boolean delete(Integer id){
        return memberDao.delete(id);
    }

    public int deleteBatch(Collection<Integer> ids){
        return memberDao.deleteBatch(ids);
    }

    public boolean update(Member member){
        MemberPo memberPo = mapper.map(member,MemberPo.class);
         memberDao.update(memberPo);
        return true;
    }

    public int updateBatch(Collection<Member> members){
        List<MemberPo> memberPos = new ArrayList<MemberPo>();
        for(Member member : members){
            MemberPo memberPo = mapper.map(member,MemberPo.class);
            memberPos.add(memberPo);
        }
        int count = memberDao.updateBatch(memberPos);
        return count;
    }

    @Transactional(readOnly = true)
    public List<Member> getByIds(Collection<Integer> ids){
        List<Member> members = new ArrayList<Member>();
        List<MemberPo> memberPos = memberDao.selectByIds(ids);
        for(MemberPo memberPo : memberPos){
            Member member = mapper.map(memberPo,Member.class);
            members.add(member);
        }
        return members;
    }

    @Transactional(readOnly = true)
    public List<Member> getAll(){
        List<Member> members = new ArrayList<Member>();
        List<MemberPo> memberPos = memberDao.selectAll();
        for(MemberPo memberPo : memberPos){
            Member member = mapper.map(memberPo,Member.class);
            members.add(member);
        }
        return members;
    }

    @Transactional(readOnly = true)
    public boolean exists(Member member, Integer id){
        MemberPo memberPo = mapper.map(member,MemberPo.class);
        return memberDao.exists(memberPo, id);
    }

    @Transactional(readOnly = true)
    public Member getById(Integer id){
        MemberPo memberPo = memberDao.selectById(id);
        Member member = mapper.map(memberPo,Member.class);
        return member;
    }

}
