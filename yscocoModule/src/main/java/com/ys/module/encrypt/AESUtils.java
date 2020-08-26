package com.ys.module.encrypt;

import com.ys.module.Config;
import com.ys.module.log.LogUtils;
import com.ys.module.utils.DateUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：karl.wei
 * 创建日期： 2017/8/17 0017 10:46
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：AES加密工具
 */
public class AESUtils {
    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static byte[] encrypt(String content) {
        LogUtils.e("加密::原始数据"+content+"");
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(Config.VIPARA.getBytes());
            SecretKeySpec key = new SecretKeySpec(Config.AESPassword.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(content.getBytes());
            return encryptedData; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**解密
     * @param content  待解密内容
     * @return
     */
    public static byte[] decrypt(byte[] content) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(Config.VIPARA.getBytes());
            SecretKeySpec key = new SecretKeySpec(Config.AESPassword.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(content);
            return encryptedData; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    
    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
    /**
     * 生成加密字符串
     * @param  url 网络请求链接
     * @param params 请求参数
     */
    public static String encryptFirsth(String url,String... params){
        String AESString = url+"?";
        if(params!=null){
            for(String param:params){
                AESString+=param+"&";
            }
        }
        //return AESString;
        LogUtils.e("加密::添加时间参数"+AESString+"yscoco="+ DateUtils.TimeInMillsTrasToDate(null,8));
        return AESString+"yscoco="+ DateUtils.TimeInMillsTrasToDate(null,8);
    };
    
    
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
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
                    return null;  
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    }  
    
    public static boolean getBoolean(String json){
    	return  (json != null && json.length()>0);
    }
    

}
