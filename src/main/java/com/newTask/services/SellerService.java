package com.newTask.services;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Products;
import com.newTask.repo.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserLoginDetails userLogingDetails;

    public boolean isPresent(int id)
    {
        boolean flag=this.sellerRepository.existsById(id);
        return flag;
    }

    //get all database
    public List<Products> getAll()
    {
        List<Products> list = this.sellerRepository.findAll();
        System.out.println("All Database>--- "+list);
        return list;
    }

    //add product in to database
    public Products addProduct(Products product)
    {
        String details=this.userLogingDetails.details();
        product.setSellerName(details);
        this.sellerRepository.save(product);
        return product;
    }

    //get by id
    public Products getById(int id){
        Products p= this.sellerRepository.findById(id).get();
        System.out.println("get by id === "+p);
        return p;
    }

    public Products deleteProductById(int id)
    {
        Products p=this.sellerRepository.findById(id).get();
        this.sellerRepository.deleteById(id);
        return p;
    }

    public void deleteAllProduct()
    {
        String details=this.userLogingDetails.details();
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(details));
        mongoTemplate.findAllAndRemove(query,Products.class);
    }


    public Products modify(Products product,int id)
    {

        Products p=this.sellerRepository.findById(id).get();
        System.out.println("update product>=="+p);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        if(product.getProductName()==null)
        {
            update.set("productName",p.getProductName());
        }
        else {
            update.set("productName",product.getProductName());
        }
        if(product.getProductPrice()==null)
        {
            update.set("productPrice",p.getProductPrice());
        }
        else {
            update.set("productPrice",product.getProductPrice());
        }

        if(product.getProductStatus()==null)
        {
            update.set("productStatus",p.getProductStatus());
        }
        else {
            update.set("productStatus",product.getProductStatus());
        }

        System.out.println("Updated product>=="+product);
        mongoTemplate.findAndModify(query, update, Products.class);

       return product;
    }


    public boolean isContains(List<Products> list,int id) {
        boolean isPresent = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                isPresent = true;
                break;
            } else {
                isPresent = false;
            }
        }
        return isPresent;
    }

    public boolean isContainsProduct(List<BuyerProducts> list, int id) {
        boolean isPresent = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                isPresent = true;
                break;
            } else {
                isPresent = false;
            }
        }
        return isPresent;
    }


}
