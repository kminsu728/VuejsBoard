package com.mskim.demo.base.config;

//import com.opennaru.khan.session.filter.InfinispanHotRodSessionFilter;
//import com.opennaru.khan.session.listener.SessionListener;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import com.opennaru.khan.session.filter.Constants;
//
//import javax.servlet.DispatcherType;
//import javax.servlet.http.HttpSessionListener;
//import java.util.Arrays;

//@Configuration
public class OpenmaruIspnConfiguration implements WebMvcConfigurer {

//    @Bean
//    public FilterRegistrationBean getFilterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new InfinispanHotRodSessionFilter());
//        registrationBean.setOrder(Integer.MIN_VALUE);
//
//        registrationBean.addInitParameter(Constants.INFINISPAN_CONFIGFILE_KEY, "hotrod.properties");
//        registrationBean.addInitParameter(Constants.INFINISPAN_CACHE_KEY, "KHAN_SESSION");
//        registrationBean.addInitParameter(Constants.INFINISPAN_LOGIN_CACHE_KEY, "KHAN_SESSION_LOGIN");
//        registrationBean.addInitParameter(Constants.SESSION_ID, "__KSMSID__");
//        registrationBean.addInitParameter(Constants.DOMAIN, "");
//        registrationBean.addInitParameter(Constants.PATH, "/");
//        registrationBean.addInitParameter(Constants.SECURE, "false");
//        registrationBean.addInitParameter(Constants.HTTP_ONLY, "false");
//        registrationBean.addInitParameter(Constants.SESSION_TIMEOUT, "30"); // minute
//        registrationBean.addInitParameter(Constants.SESSION_SAVE_DELAY, "5");
//        registrationBean.addInitParameter(Constants.EXCLUDE_REG_EXP, "/.+\\.(html|jpg|jpeg|png|gif|js|css|swf)");
//        registrationBean.addInitParameter(Constants.ALLOW_DUPLICATE_LOGIN, "false");
//        registrationBean.addInitParameter(Constants.DUPLICATE_LOGIN_POLICY, "legacy");
//        registrationBean.addInitParameter(Constants.DUPLICATE_LOGIN_EXCLUSTION_TYPE, "");
//        registrationBean.addInitParameter(Constants.INVALIDATE_DUPLICATE_LOGIN, "true");
//        registrationBean.addInitParameter(Constants.LOGOUT_URL, "/error");
//        registrationBean.addInitParameter(Constants.ENABLE_IMMEDIATED_SAVE, "true");
//        registrationBean.addInitParameter(Constants.ENABLE_STATISTICS, "true");
//        registrationBean.addInitParameter(Constants.ENABLE_MEMORY_STATISTICS, "false");
//        registrationBean.addInitParameter(Constants.LICENSE_KEY,
//                ""
//        );
//
//        registrationBean.setUrlPatterns(Arrays.asList("/*"));
//        registrationBean.setDispatcherTypes(DispatcherType.ERROR, DispatcherType.INCLUDE, DispatcherType.FORWARD, DispatcherType.REQUEST);
//        return registrationBean;
//    }
//
//    @Bean
//    public HttpSessionListener httpSessionListener() {
//        SessionListener sessionListener = new SessionListener();
//        System.out.println("SessionListener started.");
//        return sessionListener;
//    }
}
