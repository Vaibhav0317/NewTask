package com.newTask.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Seller")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Products {

   /* @Transient
    public static final String SEQUENCE_NAME="user_sequence";
*/
    @Id
    private int id;
    private String name;
    private String price;
    private String status;
    private int noOfProduct;
    private String sellerName;
}
