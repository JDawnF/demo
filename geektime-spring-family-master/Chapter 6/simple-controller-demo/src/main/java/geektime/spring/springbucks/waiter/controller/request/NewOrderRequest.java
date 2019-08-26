package geektime.spring.springbucks.waiter.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

//请求参数的映射
@Getter
@Setter
@ToString
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}
