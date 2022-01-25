package malangcute.bellytime.bellytimeCustomer.global.auth;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.controller.LoginController;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.CustomUserService;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.config.SecurityProperties;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoCookieException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NoTokenException;
import malangcute.bellytime.bellytimeCustomer.global.exception.NotValidTokenException;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Slf4j
public class TokenAuthentication extends OncePerRequestFilter {

//public class TokenAuthentication {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER = "Bearer ";
    public static final String REFRESH = "refreshToken";

    private final TokenProvider tokenprovider ;
    private final CustomUserService userDetailsService;
//    private final LoginService loginService;
//    private final LoginController loginController;


    // 헤더에서 유효토큰 있는지 확인
    public String getJwtFromRequest(HttpServletRequest request){
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER)){
            return token.substring(BEARER.length(), token.length());
        }
        return null;
    }

    // 쿠키에서 리프레시 토큰값을 반환
    public String getRefreshFromRequest(HttpServletRequest request) {
        Optional<Cookie> cookie = CookieUtils.getCookie(request, REFRESH);
       if (cookie.isPresent()){
            Cookie refreshCookie = cookie.orElseThrow(() -> new NotValidTokenException("리프레시토큰이 없습니다"));
            return refreshCookie.getValue();
      }
     return null;
    }

    // 토큰으로 부터 아이디 찾아서(이메일) 권한 부여
    public Authentication getAuthentication(String token){
        String userEmail = tokenprovider.getUserIdFromToken(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    //권한 갖고와서 securitycontextholder에 저장(유저 인증 정보 저장) -> accesstoken처리
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("here?1");
        String refreshToken = getRefreshFromRequest(request);

        System.out.println("here2?");
        String accessToken = getJwtFromRequest(request);

        System.out.println("here3?");


        // 리프레시 토큰도 없고, 유효하지도 않은 경우
        if (refreshToken != null && !tokenprovider.validateRefreshToken(refreshToken)) {
            log.trace("dofilter TokenAuthentication : " , refreshToken);
            System.out.println("111");
            throw new NotValidTokenException("리프레시토큰이 만료되었음으로 다시 로그인 해주세요");
        }
//        else if (!tokenprovider.validateAccessToken(refreshToken, accessToken)){
//            try {
//
//            } catch (ExpiredJwtException ex) {
//
//            }
//        }


        // 리프레시 토큰은 유효한데, 엑세스 토큰이 유효하지 않은 경우
//        else if(refreshToken != null && tokenprovider.validateRefreshToken(refreshToken))
//            if (!tokenprovider.validateAccessToken(accessToken, refreshToken)) {
//            String regenerateToken = tokenprovider.createAccessToken(tokenprovider.getUserIdFromToken(refreshToken),refreshToken);
//            tokenprovider.validateAccessToken(regenerateToken, refreshToken);
//                System.out.println(regenerateToken);
//                System.out.println("222");
//                System.out.println(tokenprovider.validateAccessToken(accessToken, refreshToken));
//        }
        // 둘다 유효한 토큰이면 인증객체 저장
        //else if (refreshToken != null && StringUtils.hasText(accessToken) && tokenprovider.validateAccessToken(accessToken, refreshToken)){
        else if (refreshToken != null && StringUtils.hasText(accessToken)){
                Authentication authentication = getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        System.out.println("here4");
        filterChain.doFilter(request, response);
    }
}