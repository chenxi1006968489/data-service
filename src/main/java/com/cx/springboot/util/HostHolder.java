package com.cx.springboot.util;

import com.cx.springboot.vo.UserVo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author chenxi
 * @Package:
 * @Description:
 * @create 2021·11
 */

@Component
public class HostHolder {

    private ThreadLocal<UserVo> users = new ThreadLocal<>();

    public void setUser(UserVo user) {
        users.set(user);
    }

    public UserVo getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}