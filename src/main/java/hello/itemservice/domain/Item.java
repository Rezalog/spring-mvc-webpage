package hello.itemservice.domain;

import lombok.Data;

@Data // 실무에서는 @Getter @Setter 등으로 분리하는 것이 좋음, 위험함
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // wrapper class for nullable
    private Integer quantity; // wrapper class for nullable

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
