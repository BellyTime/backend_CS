package malangcute.bellytime.bellytimeCustomer.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import malangcute.bellytime.bellytimeCustomer.food.service.FoodService;
import malangcute.bellytime.bellytimeCustomer.global.domain.CacheElements;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchResultList;
import malangcute.bellytime.bellytimeCustomer.search.dto.SearchShopRequest;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResponse;
import malangcute.bellytime.bellytimeCustomer.shop.dto.ShopSearchResultListDto;
import malangcute.bellytime.bellytimeCustomer.shop.repository.elastic.CustomShopSearchRepository;
import malangcute.bellytime.bellytimeCustomer.shop.service.ShopService;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.cache.annotation.Cacheable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final ShopService shopService;

    private final FoodService foodService;

    private final RedisTemplate<String, String> redisTemplate;


    //food, shop 전체조회
    @Transactional(readOnly = true)
   // public List<String> searching(User user, String name) {
    public SearchResultList searching(User user, String name) {

        List<String> food = foodService.searchByName(name);
        List<String> shop = shopService.searchByName(name);

        // redis에 저장
        saveRecentSearch(user, name);


        List<String> result = listAssemble(food, shop);
        Set<String> recent = recentSearch(user);
        SearchResultList list = SearchResultList.of(recent, result);
        System.out.println("totlalist"+list);
        System.out.println("recent" + recent);


       // return listAssemble(food, shop);
        return list;
    }

    //리스트 합치기
    private List<String> listAssemble(List<String> A, List<String> B) {
        return Stream.of(A, B)
                .flatMap(Collection::stream)//x -> x.stream()
                .collect(Collectors.toList());
    }

    //최근 검색어 조회
    //@Cacheable(value = CacheElements.RECENT_SEARCH, key = "#user.getId()")
    public Set<String> recentSearch(User user) {
        String key = CacheElements.USER_ID + user.getId();
        SetOperations<String, String> recentList = redisTemplate.opsForSet();
        Set<String> lists = recentList.members(key);
        return lists;
    }

    //최근 검색어 저장
    private void saveRecentSearch(User user, String name) {
        String key = CacheElements.USER_ID + "::" + user.getId();
        SetOperations<String, String> savelist = redisTemplate.opsForSet();
        savelist.add(key, name);

    }

//    ListOperations<String, PostDto.ListResponse> listOperations = redisTemplate.opsForList();
//    Post post = postRepository.findById(postIdx)
//            .orElseThrow(() -> new EntityNullException(ErrorInfo.POST_NULL));
//    PostDto.ListResponse postDto = postMapper.postToListResponse(post, userIdx);
//    String key = "userIdx::"+ userIdx;
//        listOperations.leftPush(key, postDto);
//        redisTemplate.expireAt(key, Date.from(ZonedDateTime.now().plusDays(7).toInstant())); // 유
//

//    @Cacheable(value = CacheElements.RECENT_SEARCH, key = "#name")
//    public List<String> recentSearch(String name) {
//
//    }



    //이름과 정렬 기준으로 shop 찾기
    public List<ShopSearchResultListDto> specificSearch(SearchShopRequest request) {
        return shopService.searchBySpecificName(request.getName(), request.getSortBy());
    }

}
