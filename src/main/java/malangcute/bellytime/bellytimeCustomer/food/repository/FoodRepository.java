package malangcute.bellytime.bellytimeCustomer.food.repository;

import malangcute.bellytime.bellytimeCustomer.cooltime.dto.SearchResultResponse;
import malangcute.bellytime.bellytimeCustomer.food.domain.Food;
import malangcute.bellytime.bellytimeCustomer.food.dto.FoodResultDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<FoodResultDto> findByNameContaining(String findFood);

    Optional<Food> findByName(String foodName);
}