package org.common.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtil {

    public static Object getBean(String name) {
        WebApplicationContext mac = ContextLoader.getCurrentWebApplicationContext();
        Object object = mac.getBean(name);
        return object;
    }
}
