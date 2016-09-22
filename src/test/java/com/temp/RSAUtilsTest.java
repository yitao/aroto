package com.temp;

import com.aroto.util.RSAUtils2;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yitao on 2016/9/8.
 */
public class RSAUtilsTest {
    List<String> cardIds = new ArrayList<>();
    String seed = "777777";
    KeyPair keyPair = null;
    String charset = "gb2312";
    @Before
    public void init(){
        cardIds.add("56fe26aa50026663a0edb958");
    }

    @Test
    public void testGenKeyPair(){
        System.out.println(new String(keyPair.getPrivate().getEncoded()));
        System.out.println(new String(keyPair.getPublic().getEncoded()));
    }

    @Test
    public void testEnctry() throws Exception{
        keyPair =  RSAUtils2.getKeyPair(seed);
        for(String id :cardIds ) {
//            byte[] res = RSAUtils.decrypt(keyPair.getPrivate(), id.getBytes(Charset.forName(charset)));
//            System.out.println(id+"  -  "+new String(res,0,res.length,Charset.forName(charset)) + " - ");
            byte[] res = RSAUtils2.encrypt(keyPair.getPublic(), id.getBytes(Charset.forName(charset)));
            byte[] tar =  RSAUtils2.decrypt(keyPair.getPrivate(),res);
            System.out.println(id);
            System.out.println("-----------------");
            System.out.println(res.length);
            System.out.println(new String(Hex.encodeHex(res)));
            System.out.println("================");
            System.out.println(tar.length);
            System.out.println(new String(tar));
            System.out.println(new String(Hex.encodeHex(tar)));
            System.out.println("0b0b997c8135ed4738e902642ca21d827ab05ee67b20b7d7acb5aba36e2c806059565fccfb68745e37b2454def60b0ed8d4048eee31787ac1089ea1bd25c0a00effccc07d06db62c2d63378d84384228dd4ce02fce6c1206de44506a58f7c4e03db27007c09fb1cc692fd663d739745870e3395835d23e0701a8cd41f60547bc".length());
            System.out.println("");
//            System.out.println(id+"  -  "+new String(res,0,res.length,Charset.forName(charset)) + " - " +new String(tar,0,tar.length,Charset.forName(charset)));
        }
    }

    @Test
    public void testRSA() throws  Exception{
        String charset = "utf-8";
        String id = "570de4885002661ec4871365";
        KeyPair keyPair =  RSAUtils2.getKeyPair();
        byte[] res = RSAUtils2.encrypt(keyPair.getPublic(), id.getBytes(Charset.forName(charset)));
        byte[] tar =  RSAUtils2.decrypt(keyPair.getPrivate(),res);
        System.out.println(id);
        System.out.println("-----------------");
        System.out.println(res.length);
        System.out.println(new String(Hex.encodeHex(res)));
        System.out.println("================");
        System.out.println(tar.length);
        System.out.println(new String(tar));
        System.out.println(new String(Hex.encodeHex(tar)));
        System.out.println("0b0b997c8135ed4738e902642ca21d827ab05ee67b20b7d7acb5aba36e2c806059565fccfb68745e37b2454def60b0ed8d4048eee31787ac1089ea1bd25c0a00effccc07d06db62c2d63378d84384228dd4ce02fce6c1206de44506a58f7c4e03db27007c09fb1cc692fd663d739745870e3395835d23e0701a8cd41f60547bc".length());
        System.out.println("");
        System.out.println("---------");
    }

    public String printBytes(byte[] bs){
        for(byte b : bs){
            System.out.print(b);
        }
        System.out.println();
        return "";
    }

}
