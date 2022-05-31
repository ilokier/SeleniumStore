package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private String orderReference;
    private List<Product> products;
    private String totalCost;
    private String paymentMethod;
    private String shippingMethod;
    private String shippingCost;
    private String status;
    private String date;

}
