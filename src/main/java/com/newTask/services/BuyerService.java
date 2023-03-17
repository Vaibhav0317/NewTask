package com.newTask.services;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Products;
import com.newTask.repo.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private UserLogingDetails userLogingDetails;



    public BuyerProducts addBuyProduct(BuyerProducts product)
    {
        List<BuyerProducts> list=buyerRepository.findAll();
        int id=list.size()+1;
        product.setId(id);
        String details=this.userLogingDetails.details();
        product.setBuyerName(details);
        buyerRepository.save(product);
        return product;
    }
    public List<BuyerProducts> showPurches()
    {
        String details=this.userLogingDetails.details();
        List<BuyerProducts> list=buyerRepository.findAll();
        list=list.stream().filter(e->e.getBuyerName().equals(details)).collect(Collectors.toList());
       return list;

    }

}
