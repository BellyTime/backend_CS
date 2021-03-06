package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.*;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

@Getter
@Setter
@AllArgsConstructor
public class ShopSearchResultListResponse {

    private Long shopId;

    private String shopName;

    private String profileImg;

    private int reviewCount;

    private Long score;

    private String address;

    private boolean status;

    private int followerCount;

    private boolean follow;

    public static ShopSearchResultListResponse of(Shop shop, int reviewCount, int followCount, boolean status, boolean follow) {
        return new ShopSearchResultListResponse(
            shop.getId(),
            shop.getName(),
            shop.getImage(),
            reviewCount,
            shop.getBellscore(),
            shop.getAddress(),
            status,
            followCount,
            follow
        );
    }

   // private List<MenuListDto> menu; -> 상세장보 반환

    //private List<String> menu;

    //private List<MenuListDto> menu; -> 인터페이스 렙퍼용



    // public static ShopSearchResultListWithMenuResponse of (Shop shop, int followerCount) {
    //
    //
    //     // 메뉴 스트링 리스트로 반환
    //     List<String> menus = shop.getShopId()
    //             .stream()
    //             .map(ShopMenu::getMenu)
    //             .collect(Collectors.toList());
    //
    //     return new ShopSearchResultListWithMenuResponse(shop.getId(), shop.getName(), shop.getImage(), shop.getBellscore(),
    //     shop.getAddress(), shop.getRuntime(), shop.getLongitude(),  shop.getLatitude(), followerCount, menus);
    //}



}



