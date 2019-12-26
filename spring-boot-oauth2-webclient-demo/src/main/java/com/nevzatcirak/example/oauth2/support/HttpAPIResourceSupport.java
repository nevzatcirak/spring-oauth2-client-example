package com.nevzatcirak.example.oauth2.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
public abstract class HttpAPIResourceSupport implements EnvironmentAware, ApplicationContextAware {

    private Environment environment;

    private ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    protected <T> T getRequestAttribute(HttpServletRequest request, String key) {
        return (T) request.getAttribute(key);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getSessionAttribute(HttpServletRequest request, String key) {
        return (T) request.getSession().getAttribute(key);
    }

    protected void setRequestAttribute(HttpServletRequest request, String key, Object value) {
        request.setAttribute(key, value);
    }

    protected void setSessionAttribute(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    protected void removeRequestAttribute(HttpServletRequest request, String key) {
        request.removeAttribute(key);
    }

    protected void removeSessionAttribute(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
