package malangcute.bellytime.bellytimeCustomer.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.config.TestSupport;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalDayList2;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalTodayFoodList3;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCheckRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FindMyFriendSearchRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowFriendsRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.FollowShopRequest;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFollowShopResponse;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendListResponse;
import malangcute.bellytime.bellytimeCustomer.follow.dto.MyFriendSearchResponse;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.reservation.service.ReservationService;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;

@DisplayName("?????? ???????????? ?????????")
class UserControllerTest extends TestSupport {

    @MockBean
    private UserService userService;

    @MockBean
    private FollowService followService;

    @MockBean
    private ShopService shopService;

    @MockBean
    private CoolTimeService coolTimeService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private CommentService commentService;


    private static final MockMultipartFile ??????_?????????_????????? =
        new MockMultipartFile("profileImg", "cats.JPEG", MediaType.IMAGE_JPEG_VALUE, "cats".getBytes());

    private static final UserUpdateRequest ??????????????????_?????? =
        new UserUpdateRequest("test@test.com", "??????", ??????_?????????_?????????);

    private static final UserProfileResponse ??????_?????????_?????? =
        new UserProfileResponse(?????????.getProfileImg(), ?????????.getNickname().getNickName());

    private static final List<MyFollowShopResponse> ????????????_????????? = Arrays.asList(
        new MyFollowShopResponse(1L, "?????????","img1.jpg", 23, 4L, "?????????", true, 244, true),
        new MyFollowShopResponse(2L, "????????????","img2.jpg", 23, 3L, "?????????", true, 244, true)
    );

    private static final List<FollowShopRequest> ?????????_??????_???_????????? = Arrays.asList(
        new FollowShopRequest(1L),
        new FollowShopRequest(2L)
    );

    private static final List<MyFriendListResponse> ??????_??????_?????? = Arrays.asList(
        new MyFriendListResponse(1L,"??????","img1.jpg"),
        new MyFriendListResponse(2L,"??????","img2.jpg")
    );

    private static final FindMyFriendSearchRequest ??????_??????_????????? =
        new FindMyFriendSearchRequest("test@test.com");

    private static final MyFriendSearchResponse ??????_??????_?????? =
        new MyFriendSearchResponse(1L, "??????", "img1.jpg", true);

    private static final List<FollowFriendsRequest> ?????????_?????????_??????_????????? = Arrays.asList(
        new FollowFriendsRequest(1L), new FollowFriendsRequest(2L)
    );

    private static final CoolTimeCalRequest ?????????_?????????_?????? =
        new CoolTimeCalRequest(2022L, 3L);


    private static final List<CoolTimeCalFoodList3> ?????????_??????_???_??????_1 = Arrays.asList(
        new CoolTimeCalFoodList3("?????????", 1L,"banana.jpg"),
        new CoolTimeCalFoodList3("??????", 2L,"apple.jpg")
    );

    private static final List<CoolTimeCalFoodList3> ?????????_??????_???_??????_2 = Arrays.asList(
        new CoolTimeCalFoodList3("??????", 3L,"strawberry.jpg"),
        new CoolTimeCalFoodList3("??????", 4L,"watermelone.jpg")
    );

    private static final List<CoolTimeCalDayList2> ?????????_??????_???_?????? = Arrays.asList(
        new CoolTimeCalDayList2(1, ?????????_??????_???_??????_1),
        new CoolTimeCalDayList2(2, ?????????_??????_???_??????_2)
    );

    private static final List<CoolTimeCalTodayFoodList3> ????????????_???????????? = Arrays.asList(
        new CoolTimeCalTodayFoodList3("?????????", 1L,"banana.jpg", true),
        new CoolTimeCalTodayFoodList3("??????", 2L,"apple.jpg", false)
    );

    private static final CoolTimeCalListResponse1 ?????????_??????_???????????? =
        new CoolTimeCalListResponse1(?????????_??????_???_??????, ????????????_????????????);

    private static final List<CoolTimeCheckRequest> ?????????_??????_????????? = Arrays.asList(
        new CoolTimeCheckRequest(1L, true),
        new CoolTimeCheckRequest(2L, false)
    );


    private static final FieldDescriptor[] ???????????? = new FieldDescriptor[] {
        fieldWithPath("foodName").type(JsonFieldType.STRING).description("????????? ??????"),
        fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("????????? ?????????(?????????)"),
        fieldWithPath("foodImg").type(JsonFieldType.STRING).description("????????? ?????????(S3)")
    };

    private static final FieldDescriptor[] ???????????? = new FieldDescriptor[] {
        fieldWithPath("foodName").type(JsonFieldType.STRING).description("????????? ??????"),
        fieldWithPath("foodId").type(JsonFieldType.NUMBER).description("????????? ?????????(?????????)"),
        fieldWithPath("foodImg").type(JsonFieldType.STRING).description("????????? ?????????(S3)"),
        fieldWithPath("eat").type(JsonFieldType.BOOLEAN).description("????????? ??????????????? ??????")
    };

    private static final FieldDescriptor[] ???_????????? = new FieldDescriptor[]{
        fieldWithPath("shopId").type(JsonFieldType.NUMBER).description("?????? ?????? id"),
        fieldWithPath("shopName").type(JsonFieldType.STRING).description("?????? ??????"),
        fieldWithPath("profileImg").type(JsonFieldType.STRING).description("?????? ??????????????????(s3)"),
        fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
        fieldWithPath("score").type(JsonFieldType.NUMBER).description("?????? ???????????? ??????"),
        fieldWithPath("address").type(JsonFieldType.STRING).description("?????? ??????"),
        fieldWithPath("status").type(JsonFieldType.BOOLEAN).description("???????????? ?????? ?????? ??????"),
        fieldWithPath("followerCount").type(JsonFieldType.NUMBER).description("??? ????????? ????????? ?????? ?????? ?????? ???"),
        fieldWithPath("follow").type(JsonFieldType.BOOLEAN).description("?????? ??? ????????? ????????? ?????????????????? ??????")
    };


    @DisplayName("?????? ?????? ????????????")
    @Test
    void update() throws Exception {

        willDoNothing().given(userService).userUpdate(??????????????????_??????);

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/user/update")
                            .file(??????_?????????_?????????)
                            .param("email","runnz121@gmail.com")
                            .param("nickname","??????")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)

                        )
                .andExpect(status().isOk())
                .andDo(
                        document("update-user",
                            requestParameters(
                                parameterWithName("email").description("????????? ????????? ?????? ?????? ?????????"),
                                parameterWithName("nickname").description("????????? ????????? ???????????? ?????????")
                            ),
                            requestParts(
                                partWithName("profileImg").description("????????? ????????? ???????????? ??????????????????")
                            )
                        )
                );
    }

    @DisplayName("?????? ?????????????????? ????????????")
    @Test
    void getMyProfile() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        given(userService.userProfile(any())).willReturn(??????_?????????_??????);

        mockMvc.perform(
                        get("/user/myprofile")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(??????_?????????_??????)))
            .andDo(
                    document("return-user-profile",
                        requestHeaders(
                            headerWithName("Cookie").description("????????? ??????????????????"),
                            headerWithName("Authorization").description("????????? ????????? ??????")
                        ),
                        responseFields(
                            fieldWithPath("profileImg").type(JsonFieldType.STRING).description("????????? ????????? ?????????"),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("????????? ?????????")
                        )
                    )
            );
    }

    @DisplayName("????????? ???????????? ?????? ????????? ????????????")
    @Test
    void myFollowShopList() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        given(shopService.myFollowShop(any(), any())).willReturn(????????????_?????????);

        mockMvc.perform(
                        get("/user/follow/shop")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(????????????_?????????)))
            .andDo(
                    document("return-follow-shop-list",
                        requestHeaders(
                            headerWithName("Cookie").description("????????? ??????????????????"),
                            headerWithName("Authorization").description("????????? ????????? ??????")
                        ),
                        responseFields(
                            fieldWithPath("[]").type(JsonFieldType.ARRAY).description("????????? ???????????? ?????? ?????????"))
                            .andWithPrefix("[]", ???_?????????)
                        )
            );
    }

    @DisplayName("????????? ????????? ??? ?????? ????????? ????????????")
    @Test
    void toFollowShop() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        willDoNothing().given(followService).toFollowShop(any(), any());

        mockMvc.perform(
                        post("/user/follow/shop")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(?????????_??????_???_?????????))
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                       )
            .andExpect(status().is2xxSuccessful())
            .andDo(
                document("follow-shop-list",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("[].shopId").type(JsonFieldType.NUMBER).description("?????? ?????????(?????????)"))
                )
            );
    }

    @DisplayName("????????? ????????? ????????? ?????? ????????? ??????")
    @Test
    void toUnFollowShop() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        willDoNothing().given(followService).toUnFollowShop(any(), any());

        mockMvc.perform(
                        delete("/user/follow/shop")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(?????????_??????_???_?????????))
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andDo(
                document("unfollow-shop-list",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("[].shopId").type(JsonFieldType.NUMBER).description("?????? ?????????(?????????)"))
                )
            );
    }

    @DisplayName("????????? ????????? ?????? ?????? ?????? ????????? ????????????")
    @Test
    void myFriendList() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        given(followService.getMyFriends(any())).willReturn(??????_??????_??????);

        mockMvc.perform(
                        get("/user/friends/list")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(??????_??????_??????)))
            .andDo(
                document("my-follow-list",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    responseFields(
                        fieldWithPath("[].contactId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                        fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("????????? ?????????"),
                        fieldWithPath("[].profileImg").type(JsonFieldType.STRING).description("????????? ??????????????????"))
                )
            );
    }

    @DisplayName("?????????????????? ?????? ?????? ??????(?????????)")
    @Test
    void findMyFriend() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        given(userService.findUserByNickname(any(), any(FindMyFriendSearchRequest.class))).willReturn(??????_??????_??????);

        mockMvc.perform(
                        post("/user/friends/search")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(??????_??????_?????????))
                        )
            .andExpect(status().isOk())
            .andDo(
                document( "find-user-with-email",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ????????? ?????????")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????(?????????)"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                        fieldWithPath("profileImg").type(JsonFieldType.STRING).description("?????? ????????? ????????? ??????(S3)"),
                        fieldWithPath("follow").type(JsonFieldType.BOOLEAN).description("?????? ????????? ???????????????????????? ??????")
                    )
                )
            );
    }

    @DisplayName("????????? ???????????? ?????? ???????????? ??????")
    @Test
    void followMyFriends() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        willDoNothing().given(followService).toFollowFriend(any(), any());

        mockMvc.perform(
                        post("/user/follow/friends")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(?????????_?????????_??????_?????????))
                        )
            .andExpect(status().isOk())
            .andDo(
                document("to-follow-friends-list",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("[].friendId").type(JsonFieldType.NUMBER).description("???????????? ?????? ID(?????????)")
                    )
                )
            );
    }

    @DisplayName("????????? ??????????????? ?????? ????????? ??????")
    @Test
    void unFollowMyFriend() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        willDoNothing().given(followService).toUnFollowFriend(any(), any());

        mockMvc.perform(
                delete("/user/follow/friends")
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonToString(?????????_?????????_??????_?????????))
            )
            .andExpect(status().isOk())
            .andDo(
                document("to-unfollow-friends-list",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("[].friendId").type(JsonFieldType.NUMBER).description("??????????????? ?????? ID(?????????)")
                    )
                )
            );
    }

    @DisplayName("????????? ????????? ????????????")
    @Test
    void myCoolTimeCalender() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        given(coolTimeService.selected(any(), any(Long.class), any(Long.class), any(String.class)))
            .willReturn(?????????_??????_????????????);

        mockMvc.perform(
                        post("/user/cal")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(?????????_?????????_??????))
                        )
            .andExpect(status().isOk())
            .andExpect(content().json(jsonToString(?????????_??????_????????????)))
            .andDo(
                document("return-my-cooltime-list",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("year").type(JsonFieldType.NUMBER).description("????????? ?????? ??????"),
                        fieldWithPath("month").type(JsonFieldType.NUMBER).description("????????? ?????? ???")
                    ),
                    responseFields(
                        fieldWithPath("dateList.[].day").type(JsonFieldType.NUMBER).description("???????????? ?????? ?????????"))
                        .andWithPrefix("dateList.[].data.[]", ????????????)
                        .andWithPrefix("today.[]", ????????????)
                    )
            );
    }

    @DisplayName("????????? ????????? ????????? ?????? ??????")
    @Test
    void checkMyCoolTime() throws Exception {

        given(userService.findUserByEmail(tokenProvider.getUserIdFromToken(ACCESS_TOKEN))).willReturn(?????????);
        willDoNothing().given(coolTimeService).updateCoolTimeEatCheck(any(), any());

        mockMvc.perform(
                        post("/user/cal/check")
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .header("Cookie", "refreshToken=" + REFRESH_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToString(?????????_??????_?????????))
                        )
            .andExpect(status().isOk())
            .andDo(
                document("check-my-cooltime",
                    requestHeaders(
                        headerWithName("Cookie").description("????????? ??????????????????"),
                        headerWithName("Authorization").description("????????? ????????? ??????")
                    ),
                    requestFields(
                        fieldWithPath("[].foodId").type(JsonFieldType.NUMBER).description("????????? ?????? ?????????(?????????)"),
                        fieldWithPath("[].eat").type(JsonFieldType.BOOLEAN).description("???????????? ?????? ??????")
                    )
                )
            );
    }

    //
    // @DisplayName("?????? ?????? ????????????")
    // @Test
    // void myReservationList() {
    // }
    //
    // @DisplayName("????????? ????????? ?????? ????????? ????????????")
    // @Test
    // void getMyCommentList() {
    // }
    //
    // @DisplayName("????????? ????????? ??????")
    // @Test
    // void writeComment() {
    // }
}