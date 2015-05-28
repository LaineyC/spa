package com.lite.app.membership.dao;

import com.lite.app.framework.annotation.MyBatisRepository;
import com.lite.app.framework.dao.Dao;
import com.lite.app.membership.po.MemberPo;

@MyBatisRepository
public interface MemberDao extends Dao<MemberPo,Integer>{

}
