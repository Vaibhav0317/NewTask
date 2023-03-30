package com.newTask.services;

import com.newTask.entities.BuyerProducts;
import com.newTask.repo.BuyerRepository;
import com.newTask.repo.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

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
    public List<BuyerProducts> showPurches()
    {
        String details=this.userLoginDetails.details();
        List<BuyerProducts> list=buyerRepository.findAll();
        System.out.println("list all buy product >==="+list);
        list=list.stream().filter(e->e.getBuyerName().equals(details)).collect(Collectors.toList());
        System.out.println("list of particular user>==="+list);


       return list;

    }


}
