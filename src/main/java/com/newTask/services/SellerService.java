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
import java.util.UUID;

@Component
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserLoginDetails userLoginDetails;

    @Autowired
    private BuyerService buyerService;


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
        String details=this.userLoginDetails.details();
        product.setSellerName(details);
        UUID uuid = UUID.randomUUID();
        int positiveInt = Math.abs(uuid.hashCode());
        product.setId(positiveInt);
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
        String details=this.userLoginDetails.details();
        Query query = new Query();
        query.addCriteria(Criteria.where("sellerName").is(details));
        mongoTemplate.findAllAndRemove(query,Products.class);
    }


    public Products modify(Products product,int id)
    {

        Products p=this.sellerRepository.findById(id).get();

        Products temp=new Products();
        System.out.println("update product>=="+p);

        if(product.getId()==0)
        {
            temp.setId(p.getId());
        }
        else {
            temp.setId(product.getId());
        }
        if(product.getName()==null)
        {
            temp.setName(p.getName());
        }
        else {
            temp.setName(product.getName());
        }
        if(product.getPrice()==null)
        {
            temp.setPrice(p.getPrice());
        }
        else {
            temp.setPrice(product.getPrice());
        }

        if(product.getStatus()==null)
        {
            temp.setStatus(p.getStatus());
        }
        else {
            temp.setStatus(product.getStatus());
        }
        if(product.getNoOfProduct()==0)
        {
            temp.setNoOfProduct(p.getNoOfProduct());
        }
        else {
            temp.setNoOfProduct(product.getNoOfProduct());
        }

        if(product.getSellerName()==null)
        {
            temp.setSellerName(p.getSellerName());
        }
        else {
            temp.setSellerName(product.getSellerName());
        }




        /*Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        if(product.getName()==null)
        {
            update.set("productName",p.getName());
        }
        else {
            update.set("productName",product.getName());
        }
        if(product.getPrice()==null)
        {
            update.set("productPrice",p.getPrice());
        }
        else {
            update.set("productPrice",product.getPrice());
        }

        if(product.getStatus()==null)
        {
            update.set("productStatus",p.getStatus());
        }
        else {
            update.set("productStatus",product.getStatus());
        }
        if(product.getNoOfProduct()==0)
        {
            update.set("noOfProduct",p.getNoOfProduct());
        }
        else {
            update.set("noOfProduct",product.getNoOfProduct());
        }

        System.out.println("Updated product>=="+product);
        mongoTemplate.findAndModify(query, update, Products.class);*/


        System.out.println("Updated product>=="+temp);
       return temp;
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
