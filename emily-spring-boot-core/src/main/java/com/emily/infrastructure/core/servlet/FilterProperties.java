package com.emily.infrastructure.core.servlet;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description :  过滤器配置类
 * @Author :  Emily
 * @CreateDate :  Created in 2023/2/4 10:43 上午
 */
@ConfigurationProperties(prefix = FilterProperties.PREFIX)
public class FilterProperties {
    /**
     * 前缀
     */
    public static final String PREFIX = "spring.emily.filter";
    /**
     * 是否开启过滤器组件, 默认：true
     */
    private boolean enabled = true;
    /**
     * 是否开启全局请求过滤器，默认：true
     */
    private boolean globalSwitch = true;
    /**
     * 是否开启路由重定向过滤器，默认：false
     */
    private boolean routeSwitch;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isGlobalSwitch() {
        return globalSwitch;
    }

    public void setGlobalSwitch(boolean globalSwitch) {
        this.globalSwitch = globalSwitch;
    }

    public boolean isRouteSwitch() {
        return routeSwitch;
    }

    public void setRouteSwitch(boolean routeSwitch) {
        this.routeSwitch = routeSwitch;
    }
}
