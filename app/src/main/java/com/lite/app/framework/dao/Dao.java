package com.lite.app.framework.dao;

import com.lite.app.framework.bean.Po;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface Dao<E extends Po,K extends Serializable> {

    boolean insert(E po);

    int insertBatch(Collection<E> pos);

    boolean delete(K id);

    int deleteBatch(Collection<K> ids);

    boolean update(E po);

    int updateBatch(Collection<E> pos);

    E selectById(K id);

    List<E> selectByIds(Collection<K> ids);

    List<E> selectAll();

    boolean exists(E po, K id);

}
