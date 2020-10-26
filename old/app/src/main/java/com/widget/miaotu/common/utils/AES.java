package com.widget.miaotu.common.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Administrator
 *
 */
public class AES {
    static String cKey = "sdfghhrdfgfdsaw:";

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

//        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return Base64.encodeToString(encrypted, Base64.DEFAULT);//Android自身的BASE64方法
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    /** 
     * 生成密钥 
     * 自动生成base64 编码后的AES128位密钥 
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */  
    public static String getAESKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256  
        SecretKey sk = kg.generateKey();
        byte[] b = {0x37, 0x63, 0x68, 0x23, 0x54, 0x23, 0x2E, 0x21, 0x32, 0x3E, 0x3C, 0x12, 0x3D, 0x13, 0x44, 0x65};;
        return parseByte2HexStr(b);
    }  
    /**将二进制转换成16进制 
     * @param buf 
      * @return 
      */   
     public static String parseByte2HexStr(byte buf[]) {
             StringBuffer sb = new StringBuffer();
             for (int i = 0; i < buf.length; i++) {   
                     String hex = Integer.toHexString(buf[i] & 0xFF);
                     if (hex.length() == 1) {   
                             hex = '0' + hex;   
                     }   
                     sb.append(hex.toUpperCase());   
             }   
             return sb.toString();   
     }   
     
    public static void main(String[] args) throws Exception {
        String a = "192.168.2.132";
        String b = "18205810166";
        String c = a+b;
        System.out.println(jiami(c));
        System.out.println(c.substring(0,c.length()-11));
    }
    
    
    
    
    public static String jiami(String cSrc){
    	
    	String enString="";
    	try {
    		enString = AES.Encrypt(cSrc, cKey);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return enString;
    }
    
    
    public static String jiemi(String cSrc){
    	
    	String enString="";
    	try {
    		enString = AES.Decrypt(cSrc, cKey);;
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return enString;
    }
    
}