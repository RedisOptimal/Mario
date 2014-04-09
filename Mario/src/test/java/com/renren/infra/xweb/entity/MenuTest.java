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
 * 菜单测试
 * 
 * @author yong.cao
 * @create-time 2013-12-3
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
public class MenuTest {

    static Validator validator;

    Set<ConstraintViolation<User>> constraintViolations;

    Menu menu;

    @BeforeClass
    public static void before() throws Exception {
        Locale.setDefault(Locale.CHINESE);
        ValidatorFactory config = Validation.buildDefaultValidatorFactory();
        validator = config.getValidator();
    }

    @Before
    public void setUp() throws Exception {
        menu = new Menu();
        menu.setName("tester");
    }

    @Test
    public void shouldFailValidationWithNullForNameField() {
        constraintViolations = validator.validateValue(User.class, "name", null);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());
    }
}
