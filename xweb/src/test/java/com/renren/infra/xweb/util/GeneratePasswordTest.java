package com.renren.infra.xweb.util;

import junit.framework.Assert;

import org.junit.Test;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

/**
 * 测试password生成类
 * @author Administrator
 * @create-time 2013-9-26
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class GeneratePasswordTest {

    public static final String HASH_ALGORITHM = "SHA-1";

    public static final int HASH_INTERATIONS = 1024;

    private String code = "7efbd59d9741d34f";

    private String password = "admin";

    private String exceptPassword = "691b14d79bf0fa2215f155235df5e670b64394cc";


    /**
     * 测试生成password
     * 规则如下，
     * 1、获得随机的SALT_SIZE位byte[]字符，然后使用decodeHex获取salt字符，保存salt字符到数据库中，
     * 2、使用sha1算法，生成密码，调用enecodeHex，生成最后的password，保存password到数据库中
     */
    @Test
    public void testSalt() {
        byte[] salt = Encodes.decodeHex(code);

        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        String actualPassword = Encodes.encodeHex(hashPassword);

        Assert.assertEquals(exceptPassword, actualPassword);

    }

}
