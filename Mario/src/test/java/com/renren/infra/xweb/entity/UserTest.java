package com.renren.infra.xweb.entity;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 测试User的validator
 * 
 * @author yong.cao
 * @create-time 2013-12-2
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class UserTest {

    static Validator validator;

    Set<ConstraintViolation<User>> constraintViolations;

    User user;

    @BeforeClass
    public static void before() throws Exception {
        Locale.setDefault(Locale.CHINESE);
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
    }

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setLoginName("tester");
        user.setName("tester");
        user.setEmail("test.test@reren-inc.com");
    }

    @Test
    public void shouldFailValidationWithNullForLoginNameField() {
        constraintViolations = validator.validateValue(User.class, "loginName", null);

        assertEquals(1, constraintViolations.size());
        assertEquals("登录名不能为空", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWithNullForNameField() {
        constraintViolations = validator.validateValue(User.class, "name", null);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldFailValidationWithWrongEmailField() {
        constraintViolations = validator.validateValue(User.class, "email", "renren-inc");

        assertEquals(1, constraintViolations.size());
        assertEquals("not a well-formed email address", constraintViolations.iterator().next()
                .getMessage());
    }

    @Test
    public void testHappyPathValidationForWholeObject() {
        constraintViolations = validator.validate(user);
        assertEquals(0, constraintViolations.size());
    }

}
