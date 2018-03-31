package com.mmall.common;

public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN= 1;
    }

    public interface RedisCacheExtime{
        int REDIS_SESSION_EXTIME = 60 * 30;//30分钟
    }
}
