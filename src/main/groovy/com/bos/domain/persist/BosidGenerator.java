package com.bos.domain.persist;

import com.bos.domain.Bostype;
import com.bos.domain.IBostype;
import com.bos.domain.ICoreObject;
import com.bos.domain.util.BostypeUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class BosidGenerator implements IdentifierGenerator {


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if(object instanceof ICoreObject){
            Bostype bostype=object.getClass().getAnnotation(Bostype.class);
            String bt=bostype.value();
            IBostype ibt=new MyBostype(bt);
            return BostypeUtils.getBostypeid(ibt);
        }else{
            throw new RuntimeException("Can't generate id and persistent for those NOT ICoreObject!!!");
        }
    }
}
