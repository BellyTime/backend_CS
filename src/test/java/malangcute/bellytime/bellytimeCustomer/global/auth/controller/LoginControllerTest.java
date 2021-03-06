package malangcute.bellytime.bellytimeCustomer.global.auth.controller;

import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.global.auth.TokenProvider;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.FirstLoginDto;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.LoginResultWithIdPass;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.LoginWithIdAndPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RefreshAndAccessTokenResponse;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.RegisterWithIdPassRequest;
import malangcute.bellytime.bellytimeCustomer.global.auth.service.LoginService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("?????? ????????? ????????? ")
@Slf4j
class LoginControllerTest extends TestSupport {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    private LoginService loginService;

    @MockBean
    private UserService userService;


    private static final RefreshAndAccessTokenResponse refreshAndAccessTokenResponse =
            new RefreshAndAccessTokenResponse("accessToken", "refreshToken");

    public static final LoginResultWithIdPass loginResultWithIdPass =
            new LoginResultWithIdPass(1L, "userNickName", refreshAndAccessTokenResponse);

    private static final FirstLoginDto firstLoginDto =
            new FirstLoginDto(1L, "userNickName", "accessToken");

    private static final MockMultipartFile USER_IMG =
            new MockMultipartFile("profileImg", "cats.JPEG", MediaType.IMAGE_JPEG_VALUE, "cats".getBytes());

    public static final LoginWithIdAndPassRequest LOGIN_REQUEST =
           new LoginWithIdAndPassRequest("runnz@gmail.com", "test");

    public static final RegisterWithIdPassRequest REGISTER =
            RegisterWithIdPassRequest.builder()
                .email("runnz121@gmail.com")
                .password("test")
                .name("??????")
                .nickname("????????????")
                .phoneNumber("010-222-2222")
                .profileImg(USER_IMG)
                .build();


    @DisplayName("????????? ???????????? ?????? ??????")
    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(
                get("/"))
                .andExpect(status().isOk());
    }

    @DisplayName("????????? ???????????? ???????????? ????????? ????????? ???????????? ????????? ??????")
    @Test
    void loginWithIdController() throws Exception {

        // given
        given(loginService.validUser(any())).willReturn(loginResultWithIdPass);

        // when, then
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonToString(LOGIN_REQUEST))
                        )
                .andExpect(status().isOk())
                .andDo(
                        document("login-with-id-pass",
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("????????? ?????????"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????? ????????????")
                                ),
                                responseHeaders(
                                        headerWithName("Set-Cookie").description("?????????????????? ??????")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("????????? ????????? ??????"),
                                        fieldWithPath("userNickName").type(JsonFieldType.STRING).description("????????? ????????? ??????")
                                )
                        ));
    }

    @DisplayName("????????? ?????? ?????? ??????(????????? ?????? ??????)")
    @Test
    void registerNewUser() throws Exception {

        // given
        loginService.registerNewUser(REGISTER);

        // when -> void??? willdonothing  https://yhmane.tistory.com/198
        willDoNothing().given(loginService).registerNewUser(REGISTER);

        // then
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/join")
                                .file(USER_IMG)
                                .param("email", REGISTER.getEmail())
                                .param("password",REGISTER.getPassword())
                                .param("name", REGISTER.getName())
                                .param("nickname",REGISTER.getNickname())
                                .param("phoneNumber", REGISTER.getPhoneNumber())
                        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        document("register-with-form",
                                requestParameters(
                                        parameterWithName("email").description("????????? ???????????? ????????? (?????????)"),
                                        parameterWithName("password").description("????????? ????????? ???????????? ????????????"),
                                        parameterWithName("name").description("????????? ??????"),
                                        parameterWithName("nickname").description("????????? ????????? (?????????)"),
                                        parameterWithName("phoneNumber").description("????????? ????????? ??????")
                                ),
                                requestParts(
                                        partWithName("profileImg").description("????????? ????????? ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("complete").type(JsonFieldType.BOOLEAN).description("??????????????? true ??????"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("??????????????? '?????????????????????' ???????????????")
                                )
                        )
                );
    }


    @DisplayName("?????? ???????????? ??????(???????????? ?????? ?????? ???)")
    @Test
    void logOutUSer() throws Exception {

        // given
        ?????????_??????(1L).setRefreshToken("refreshToken");
        String token = tokenProvider.createAccessToken(?????????_??????(1L).getEmail().getEmail());

        // when
        loginService.userLogOut(?????????_??????(1L));

        // then
        mockMvc.perform(
                        delete("/log-out")
                                .header("Authorization","Bearer " + token)
                                .header("Cookie", "refreshToken=" + token)

                )
                .andExpect(status().isNoContent())
                .andDo(
                        document("logout-with-token",
                                requestHeaders(
                                        headerWithName("Cookie").description("????????? ??????????????????"),
                                        headerWithName("Authorization").description("????????? ????????? ??????")
                                )
                        )
                );
    }
}