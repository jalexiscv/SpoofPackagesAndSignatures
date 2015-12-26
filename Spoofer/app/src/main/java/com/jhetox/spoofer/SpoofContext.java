package com.jhetox.spoofer;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/12/15.
 */
public class SpoofContext extends ContextWrapper {

    private SpoofPackageManager pm = null;
    private Context parentContext = null;
    private String packageToSpoof = "";

    public SpoofContext(Context context, String packageToSpoof){
        super(context);
        this.parentContext = context;
        this.packageToSpoof = packageToSpoof;
        pm = new SpoofPackageManager(context, packageToSpoof);
        if(packageToSpoof == null || packageToSpoof.isEmpty()) throw new NullPointerException("Package is empty");
    }

    @Override
    public String getPackageName() {
        return packageToSpoof;
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    @Override
    public Context getBaseContext() {
        return this;
    }

    @Override
    public PackageManager getPackageManager() {
        return pm;
    }

    @Override
    public Resources getResources() {
        Resources resources = null;
        try{
            PackageManager manager = getPackageManager();
            resources = manager.getResourcesForApplication(packageToSpoof);
        }catch(Exception e){
            Log.e("getResources", e.toString());
        }
        return resources;
    }

    public static List<String>  getPermissions(Context context){
        List<String> perms = new ArrayList();
        try{
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if(pi.requestedPermissions != null && pi.requestedPermissions.length>0){
                for(String perm: pi.requestedPermissions){
                    if(perm != null && !perms.contains(perm)) perms.add(perm);
                }
            }
        }catch(Exception e){
            Log.e("getPermissions", e.toString());
        }
        return perms;
    }

    public static String getPackageName(Context context){
        String packageName = "";
        try{
            packageName = context.getPackageName();
        }catch(Exception e){
            Log.e("getPackageName", e.toString());
        }
        return packageName;
    }

    public static String getSignature(Context context, boolean onHex){
        String signture = "";
        try{
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature sig : pi.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(sig.toByteArray());
                signture = (onHex)?toHex(md.digest()):toBase64(md.digest());
            }
        }catch(Exception e){
            Log.e("getSignature", e.toString());
        }
        return signture;
    }

    public static String toHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b).toUpperCase());
        }
        return builder.toString();
    }

    public static String toBase64(byte[] in) {
        return new String(Base64.encode(in, 0));
    }

    //https://developers.google.com/maps/documentation/android-api/

    //To get debug signatures
    //keytool -list -v -keystore  ~/.android/debug.keystore
}
