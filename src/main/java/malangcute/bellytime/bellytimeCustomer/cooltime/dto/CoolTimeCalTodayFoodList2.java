package malangcute.bellytime.bellytimeCustomer.cooltime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoolTimeCalTodayFoodList2 {

    private List<CoolTimeCalTodayFoodList3> today = new ArrayList<>();


    public static CoolTimeCalTodayFoodList2 of (List<CoolTimeCalTodayFoodList3> todayList) {
        return new CoolTimeCalTodayFoodList2(todayList);
    }

}
