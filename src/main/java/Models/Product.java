package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class Product {
    String name;
    String quantity;
    String price;

}
