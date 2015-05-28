package com.lite.app.framework.service;

import org.dozer.Mapper;
import javax.annotation.Resource;

public abstract class BaseService implements Service{

    @Resource
    protected Mapper mapper;

}
