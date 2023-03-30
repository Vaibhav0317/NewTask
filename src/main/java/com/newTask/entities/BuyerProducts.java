package com.newTask.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Buyer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuyerProducts {
    private int id;
    private String buyerName;
    private int productId;
    private  String productName;
    private String price;
    private String status;
    private int purchaseProduct;
    private int productRating;
    private int sellerRating;
}
