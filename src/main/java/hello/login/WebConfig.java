package hello.login;

import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //인터셉터에 조건 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor()) //LoginCheckInterceptor 에 조건 설정
                .addPathPatterns("/**") //모든 URL 에 대해 인터셉터가 실행되는데
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"); //명시된 URL 패턴에 해당하는 요청은 인터셉터를 거치지 않음
    }
}
