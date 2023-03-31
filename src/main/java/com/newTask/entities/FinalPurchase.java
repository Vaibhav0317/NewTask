package com.newTask.entities;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FinalPurchase {
    private String BuyerName;
    private List<Entity> PurchaseList;
}
