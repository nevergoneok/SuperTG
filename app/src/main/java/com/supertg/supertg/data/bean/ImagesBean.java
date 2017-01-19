package com.supertg.supertg.data.bean;

import java.util.List;

/**
 * Created by xiongxing on 2017/1/18.
 */

public class ImagesBean{

    private List<ErrDesc> errDesc ;

    public void setErrDesc(List<ErrDesc> errDesc){
        this.errDesc = errDesc;
    }
    public List<ErrDesc> getErrDesc(){
        return this.errDesc;
    }

    public class ErrDesc {
        private String a;

        private String b;

        public void setA(String a){
            this.a = a;
        }
        public String getA(){
            return this.a;
        }
        public void setB(String b){
            this.b = b;
        }
        public String getB(){
            return this.b;
        }

    }
}
