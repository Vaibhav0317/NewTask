package com.newTask.services;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Products;
import com.newTask.repo.BuyerRepository;
import com.newTask.repo.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuyerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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
        buyerRepository.save(product);
        return product;
    }
    public List<BuyerProducts> showPurches()
    {
        String details=this.userLoginDetails.details();
        List<BuyerProducts> list=buyerRepository.findAll();
        System.out.println("list all buy product >==="+list);
        list=list.stream().filter(e->e.getBuyerName().equals(details)).collect(Collectors.toList());
        System.out.println("list of particular user>==="+list);


       return list;

    }

    public BuyerProducts modifyRating(BuyerProducts product, int id)
    {

        //Products p=this.sellerRepository.findById(id).get();
        BuyerProducts p=this.buyerRepository.findById(id).get();



        System.out.println("update for rating update product>=="+p);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();

        /*System.out.println("buyer name >==="+product.getBuyerName());
        System.out.println("buyer product >==="+product.getBuyerProduct());
        System.out.println("buyer product rating >==="+product.getProductRating());
        System.out.println("buyer seller rating >==="+product.getSellerRating());*/

        if(product.getBuyerName()==null)
        {
            System.out.println("name is null update value>==="+p.getBuyerName());
            update.set("buyerName",p.getBuyerName());
        }
        else {
            update.set("buyerName",product.getBuyerName());
        }

        if(product.getBuyerProduct()==null)
        {
            update.set("buyerProduct",p.getBuyerProduct());
        }
        else {
            update.set("buyerProduct",product.getBuyerProduct());
        }

        if(product.getProductRating()==0)
        {
            update.set("productRating",p.getProductRating());
        }
        else {
            update.set("productRating",product.getProductRating());
        }
        if(product.getSellerRating()==0)
        {
            update.set("sellerRating",p.getSellerRating());
        }
        else {
            update.set("sellerRating",product.getSellerRating());
        }


        System.out.println("Updated product>=="+product);
        mongoTemplate.findAndModify(query, update, BuyerProducts.class);

        return product;
    }

}
