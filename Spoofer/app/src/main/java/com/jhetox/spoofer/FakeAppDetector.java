package com.jhetox.spoofer;

import android.content.Context;

/**
 * Created by root on 26/12/15.
 */
public class FakeAppDetector {

    private FakeAppDetector(){}

    public static boolean isFakeApp(Context context, String packageName, String signature){
        boolean fake = true;
        try{
            if(packageName.equals(SpoofContext.getPackageName(context))){
                if(signature.equals(SpoofContext.getSignature(context, true))){
                    fake = false;
                }
            }
        }catch(Exception e){}
        return fake;
    }
}
