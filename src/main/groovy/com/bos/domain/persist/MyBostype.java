package com.bos.domain.persist;


import com.bos.domain.IBostype;

class MyBostype implements IBostype {
    private final String bt;

    public MyBostype(String bt) {
        this.bt=bt;
    }
    public String  toString(){
        return this.bt;
    }
}
