package util;

import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * MD5加盐 手动算法
 */
public class PasswordWithSaltEncode {

    private static final String DIGEST =  "MD5";
    private static final int RANDOM_BYTE_LENGTH = 12;
    private String md5;

    /**
     * MD5加密
     * @param input
     * @return
     */
    private static String md5Encode(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(DIGEST);

            byte[] bytes = input.getBytes();
            //md5加密后为128bit（位）
            //1字节=8bit
            //一个16进制为0xf，一个16进制数为四个二进制数，0x0为0000，0xf为1111，即1个16进制数为4bit
            //digest.length == 128/8 == 16
            byte[] digest = messageDigest.digest(bytes);
            //base64转码后长度为 设字符串长度为n ，长度为 ⌈n/3⌉*4   ⌈⌉ 代表上取整 -> 16/3向上取整6 -> 6*4 == 24
            return new String(Base64Utils.encode(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成md5密码和salt
     * @param password
     * @return
     */
    public static List<String> generate(String password) {
        try {
            //生成salt
            byte [] bytes = new byte[RANDOM_BYTE_LENGTH];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(bytes);

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                int val = ((int)bytes[i]) & 0xff;
                //如果val小于16 则加上16 补足两位数
                val = val < 16 ? val + 16 : val;
                stringBuffer.append(Integer.toHexString(val));
            }
            //最终 salt长度为 bytes.length * 2
            String salt = stringBuffer.toString();
            Assert.isTrue(salt.length() == bytes.length * 2,"长度出问题了！");

            //先普通MD5加密 password.length == 24;
            password = md5Encode(password + salt);
            System.out.println("password.length = " + password.length());
            //cs.length == 48
            char [] cs = new char[password.length() + salt.length()];
            for (int i = 0; i < cs.length; i+=4) {
                //
                cs[i] = password.charAt(i / 2);
                cs[i + 2] = password.charAt(i/2 + 1);

                cs[i + 1] = salt.charAt(i / 2);
                cs[i + 3] = salt.charAt(i/2 + 1);

            }

            return Arrays.asList(new String(cs), salt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证是否相等
     * @param md5
     * @param password
     */
    public static void checkEquals(String md5, String password){
        if (StringUtils.isEmpty(md5)) {
            System.out.println();
        }
//        char [] cs1 = new char[RANDOM_BYTE_LENGTH * 2];
//        //salt
//        char [] cs2 = new char[RANDOM_BYTE_LENGTH * 2];
//
//        for (int i = 0; i < md5.length(); i+=4) {
//            cs1[i / 2] = md5.charAt(i);
//            cs1[i/2 + 1] = md5.charAt(i + 2);
//
//            cs2[i / 2] = md5.charAt(i + 1);
//            cs2[i/2 + 1] = md5.charAt(i + 3);
//        }
//        System.out.println(new String(cs1).equals(md5Encode(password + new String(cs2))));

        char[] chars = md5.toCharArray();
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            //偶数
            if ((i&1) == 0) {
                buffer1.append(chars[i]);
            }
            else {
                buffer2.append(chars[i]);
            }
        }

        System.out.println(buffer1.toString().equals(md5Encode(password + buffer2.toString())));
    }

    public static void main(String[] args) {
    //

        String password = "123456";
        List<String> generate = generate(password);
        generate.stream().forEach(x -> System.out.println(x));
        checkEquals(generate.get(0), password);
    }
}
