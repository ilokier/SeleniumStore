package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Product {
    String name;
    String quantity;
    String price;

}
