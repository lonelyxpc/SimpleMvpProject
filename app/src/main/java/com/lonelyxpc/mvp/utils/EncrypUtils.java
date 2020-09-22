package com.lonelyxpc.mvp.utils;

import com.lonelyxpc.mvp.utils.encryp.MD5Hex;

public class EncrypUtils {

    private static final int AES = 20001;
    private static final int MD5  = 20002;
    private static final int RSA  = 20003;

    public void hanlder(String content,int type){

        switch (type){
            case AES:
                break;
            case RSA:
                break;
            case MD5:
                  MD5Hex.md5bykey(content);
                break;
        }
    }


}
