package com.newTask.services;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Entity;
import com.newTask.entities.FinalPurchase;
import com.newTask.entities.Products;
import com.newTask.repo.BuyerRepository;
import com.newTask.repo.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuyerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private static MongoTemplate mongoTemplate;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private UserLoginDetails userLoginDetails;

    public BuyerProducts addBuyProduct(BuyerProducts product)
    {
        List<BuyerProducts> list=buyerRepository.findAll();
        int id=list.size()+1;
        product.setId(id);
        String details=this.userLoginDetails.details();
        product.setBuyerName(details);
        System.out.println("Buyer product >==="+product);
        buyerRepository.save(product);
        return product;
    }
    public FinalPurchase showPurches()
    {
        String details=this.userLoginDetails.details();
        List<BuyerProducts> list=buyerRepository.findAll();
        System.out.println("list all buy product >==="+list);
        list=list.stream().filter(e->e.getBuyerName().equals(details)).collect(Collectors.toList());
        System.out.println("list of particular user>==="+list);

       // List<Products> UserList=new ArrayList<>();
        List<Entity> UserList1=new ArrayList<>();

        /*for(int i=0;i<list.size();i++)
        {
            Products p=new Products();
            p.setId(list.get(i).getId());
            p.setName(list.get(i).getProductName());
            p.setPrice(list.get(i).getPrice());
            p.setStatus(list.get(i).getStatus());
            p.setNoOfProduct(list.get(i).getPurchaseProduct());
            p.setSellerName(list.get(i).getSellerName());
            System.out.println("product>========"+p);
            UserList.add(p);
        }*/

        for(int i=0;i<list.size();i++)
        {
            Entity p=new Entity();
            p.setId(list.get(i).getId());
            p.setName(list.get(i).getProductName());
            p.setPrice(list.get(i).getPrice());
            p.setStatus(list.get(i).getStatus());
            p.setPurchaseProduct(list.get(i).getPurchaseProduct());
            p.setSellerName(list.get(i).getSellerName());
            p.setProductRating(list.get(i).getProductRating());
            p.setSellerRating(list.get(i).getSellerRating());
            System.out.println("product>========"+p);
            UserList1.add(p);
        }

        System.out.println("list of product buy by user >=="+UserList1);
        FinalPurchase finalPurchase=new FinalPurchase();
        finalPurchase.setBuyerName(details);
        finalPurchase.setPurchaseList(UserList1);


       return finalPurchase;

    }


}
