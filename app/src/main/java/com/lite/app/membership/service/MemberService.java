package com.lite.app.membership.service;

import com.lite.app.membership.model.Member;
import java.util.Collection;
import java.util.List;

public interface MemberService {

    public Member save(Member member);

    public int saveBatch(Collection<Member> pos);

    public boolean delete(Integer id);

    public int deleteBatch(Collection<Integer> ids);

    public boolean update(Member po);

    public int updateBatch(Collection<Member> pos);

    public Member getById(Integer id);

    public List<Member> getByIds(Collection<Integer> ids);

    public List<Member> getAll();

    public boolean exists(Member po, Integer id);

}
