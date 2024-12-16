package com.shine.security.authorization;

/**
 * @author huihui
 * @date 2024/11/22 13:19
 * @description Principal
 *
 * 注意：因为使用反射获取属性以及对应值，不能读取到父类属性值，所以实现类不可被继承，请使用final修饰
 */
public interface Principal {

    Long getId();

    String getUsername();

    String getPassword();

}
