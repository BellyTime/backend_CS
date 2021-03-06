package malangcute.bellytime.bellytimeCustomer.user.controller;


import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentDto;
import malangcute.bellytime.bellytimeCustomer.comment.dto.CommentWriteRequest;
import malangcute.bellytime.bellytimeCustomer.comment.service.CommentService;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalListResponse1;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCalRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.CoolTimeCheckRequest;
import malangcute.bellytime.bellytimeCustomer.cooltime.service.CoolTimeService;
import malangcute.bellytime.bellytimeCustomer.follow.dto.*;
import malangcute.bellytime.bellytimeCustomer.follow.service.FollowService;
import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.exception.exceptionDetail.FailedToConvertImgFileException;
import malangcute.bellytime.bellytimeCustomer.reservation.dto.ReservationShopInfoResponse;
import malangcute.bellytime.bellytimeCustomer.reservation.service.ReservationService;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserProfileResponse;
import malangcute.bellytime.bellytimeCustomer.user.dto.UserUpdateRequest;
import malangcute.bellytime.bellytimeCustomer.user.repository.UserRepository;
import malangcute.bellytime.bellytimeCustomer.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final FollowService followService;

    private final ShopService shopService;

    private final CoolTimeService coolTimeService;

    private final ReservationService reservationService;

    private final CommentService commentService;

    // ?????? ?????? ????????????
    @PostMapping("/update")
    public ResponseEntity update(@RequireLogin User user, @ModelAttribute UserUpdateRequest userUpdateRequest)
        throws FailedToConvertImgFileException {
        userService.userUpdate(userUpdateRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //?????? ????????? ????????? ?????? ????????????
    @GetMapping("/myprofile")
    public ResponseEntity<UserProfileResponse> getMyProfile(@RequireLogin  User user) {
        UserProfileResponse result = userService.userProfile(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Follow service
     */

    //????????? ???????????? ?????? ????????????
    @GetMapping("/follow/shop")
    public ResponseEntity<List<MyFollowShopResponse>> myFollowShopList(@RequireLogin User user, Pageable pageable) {
        List<MyFollowShopResponse> list = shopService.myFollowShop(user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //????????? ???????????? ?????? ???????????? ?????????
    @PostMapping("/follow/shop")
    public ResponseEntity toFollowShop(@RequireLogin User user,
                                       @RequestBody List<FollowShopRequest> requests) {
        followService.toFollowShop(user, requests);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // ????????? ????????? ????????? ??????(????????? ??????????????? ?????????)
    @DeleteMapping("/follow/shop")
    public ResponseEntity toUnFollowShop(@RequireLogin User user,
                                         @RequestBody List<FollowShopRequest> requests) {
        followService.toUnFollowShop(user, requests);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // ????????? ????????? ????????????
   @GetMapping("/friends/list")
    public ResponseEntity<List<MyFriendListResponse> > myFriendList(@RequireLogin User user) {
        List<MyFriendListResponse> list = followService.getMyFriends(user);
        return ResponseEntity.status(HttpStatus.OK).body(list);
   }

   //???????????? ????????? ??????
   @PostMapping("/friends/search")
    public ResponseEntity<MyFriendSearchResponse> findMyFriend(@RequireLogin User user,
                                                              @RequestBody FindMyFriendSearchRequest request) {
        MyFriendSearchResponse result = userService.findUserByNickname(user, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
   }

   //?????? ?????????, ??????????????????
    @PostMapping("/follow/friends")
    public ResponseEntity followMyFriends(@RequireLogin User user,
                                             @RequestBody List<FollowFriendsRequest> request) {
        followService.toFollowFriend(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    //????????? ?????????
    @DeleteMapping("/follow/friends")
    public ResponseEntity<?> unFollowMyFriend(@RequireLogin User user,
                                              @RequestBody List<FollowFriendsRequest> request) {
        followService.toUnFollowFriend(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /*
     * cooltime cal ?????????
     *
     */

    //?????? ????????? ???????????? -> year, month ?????? ???????????? ????????? ?????? ??????
    @PostMapping("/cal")
    public ResponseEntity<CoolTimeCalListResponse1> myCoolTimeCalender(@RequireLogin User user,
                                                                       @RequestBody CoolTimeCalRequest request) {
        CoolTimeCalListResponse1 list = coolTimeService.selected(user, request.getMonth(), request.getYear(), "me");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    //????????? ?????? ?????? ??????
    @PostMapping("/cal/check")
    public ResponseEntity checkMyCoolTime(@RequireLogin User user,
                                          @RequestBody List<CoolTimeCheckRequest> requests) {
        coolTimeService.updateCoolTimeEatCheck(user, requests);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /*
     * reservaiton ?????????
     *
     */
    @GetMapping("/reservation/list")
    public ResponseEntity<List<ReservationShopInfoResponse>> myReservationList(@RequireLogin User user) {
        List<ReservationShopInfoResponse> list = reservationService.myReservationList(user);
        return ResponseEntity.ok(list);
    }


    /**
     * comment ?????????
     */

    //?????? ????????? ?????? ?????? ????????????
    @GetMapping("/comment/list")
    public ResponseEntity<List<CommentDto>> getMyCommentList(@RequireLogin User user) {
        List<CommentDto> list = commentService.getMyComment(user);
        return ResponseEntity.ok(list);
    }

    //?????? ????????????
    @PostMapping("/comment/write")
    public ResponseEntity writeComment(@RequireLogin User user,
                                       @ModelAttribute CommentWriteRequest request) {
        commentService.updateMyComment(user, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
