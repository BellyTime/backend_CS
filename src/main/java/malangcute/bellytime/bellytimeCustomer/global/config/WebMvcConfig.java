package malangcute.bellytime.bellytimeCustomer.global.config;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.auth.DummyLoginArgResolver;
import malangcute.bellytime.bellytimeCustomer.global.auth.LoginArgumentResolver;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenAuthentication;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenAuthentication tokenAuthentication;
    private final TokenProvider tokenProvider;
    private final UserService userService;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    //커스텀 리졸버 등록
    //더미 로그인 리졸버는 db에서 test 유저만 항상 반환한다!
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //resolvers.add(createLoginArgumentResolve());
        resolvers.add(createDummyLoginArgResolver());
    }

    @Bean
    public LoginArgumentResolver createLoginArgumentResolve() {
        return new LoginArgumentResolver(tokenAuthentication, tokenProvider, userService);
    }

    @Bean
    public DummyLoginArgResolver createDummyLoginArgResolver() {
        return new DummyLoginArgResolver(userService);
    }
}
