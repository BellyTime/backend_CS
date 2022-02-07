package malangcute.bellytime.bellytimeCustomer.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import malangcute.bellytime.bellytimeCustomer.shop.domain.Shop;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultListDto {

    private List<String> resultList;

    public static ResultListDto of (Shop resultList) {
        return new ResultListDto();
    }
}
