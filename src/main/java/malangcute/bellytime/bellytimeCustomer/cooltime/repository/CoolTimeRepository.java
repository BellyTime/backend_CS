package malangcute.bellytime.bellytimeCustomer.cooltime.repository;

import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.cooltime.dto.GetMyCoolTimeListIF;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoolTimeRepository extends JpaRepository<CoolTime, Long> {


//    @Query("SELECT f.id, f.name, f.image, c.gauge, c.startDate, c.endDate  FROM Food f, CoolTime c "
//    + "WHERE c.userId=:kk ")
//    List<Object[]> findMyCoolTime(@Param("kk") User userId);

    //N+1 문제로 join fetch로 변경
//    @Query("SELECT f.id AS foodId, f.name AS foodName, f.image AS foodImg, c.gauge AS gauge, c.startDate AS startDate, c.endDate AS endDate  FROM Food f , CoolTime c "
//            + "WHERE c.userId=:kk ")
//    List<GetMyCoolTimeListIF> findMyCoolTime(@Param("kk") User userId);

    @Query("SELECT f.id AS foodId, f.name AS foodName, f.image AS foodImg, c.gauge AS gauge, c.startDate AS startDate, c.endDate AS endDate FROM CoolTime c LEFT JOIN FETCH Food f ON c.foodId.id = f.id "
            + "WHERE c.userId=:kk ")
    List<GetMyCoolTimeListIF> findMyCoolTime(@Param("kk") User userId);


    // 삭제하기
    @Modifying
    @Query("DELETE FROM CoolTime c WHERE c.userId.id=:userId AND c.foodId.id=:foodId ")
    void deleteByUserId(@Param("userId") Long userId, @Param("foodId") Long foodId);

    Optional<CoolTime> findByUserIdAndFoodId(User userId, Food foodId);
}
