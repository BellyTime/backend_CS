package malangcute.bellytime.bellytimeCustomer.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.AccessTokenResponseDto;
import malangcute.bellytime.bellytimeCustomer.global.auth.util.CookieUtils;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.NotValidTokenException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class JWTExceptionFilter extends OncePerRequestFilter {

    public static final String REFRESH = "refreshToken";
    private final TokenProvider tokenprovider ;
    private final ObjectMapper objectMapper;


    // 쿠키에서 리프레시 토큰값을 반환
    public String getRefreshFromRequest(HttpServletRequest request) {
        Optional<Cookie> cookie = CookieUtils.getCookie(request, REFRESH);
        if (cookie.isPresent()){
            Cookie refreshCookie = cookie.orElseThrow(() -> new NotValidTokenException("리프레시토큰이 없습니다"));
            return refreshCookie.getValue();
        }
        return null;
    }


    //실제 배포시 사용할 메소드
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String refreshToken = getRefreshFromRequest(request);
        String userPk = null;
        if (refreshToken != null){
            userPk = tokenprovider.getUserIdFromToken(refreshToken);
        }
        try {
            //TokenAuthentication 필터로 진행
            filterChain.doFilter(request, response);
            //유효토큰 만료 예외처리 발생시 -> 컨트롤러로 토큰 보내서 엑세스 토큰 반환
        } catch(JwtException ex) {
            String newAccess = tokenprovider.createAccessToken(userPk);
            sendNewToken(response, newAccess);
        }
    }


    // 엑세스 토큰 생성 및 응답
    public void sendNewToken(HttpServletResponse res, String token) throws IOException {
        res.setContentType("application/json; charset=UTF-8");
        AccessTokenResponseDto acc = new AccessTokenResponseDto(token);
        String result = objectMapper.writeValueAsString(acc);
        res.getWriter().write(result);
    }
}
