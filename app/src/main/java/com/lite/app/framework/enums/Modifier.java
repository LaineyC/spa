package com.lite.app.framework.enums;

public enum Modifier {
    /**
     * 公开的，任何人都可以访问。
     */
    PUBLIC,

    /**
     * 受保护的，内部管理员都可以访问。
     */
    PROTECTED,

    /**
     * 私有的，只有指定了权限才可以访问。
     */
    PRIVATE,

    /**
     * 拒绝，任何情况下都不能访问。
     */
    DENIED;

}