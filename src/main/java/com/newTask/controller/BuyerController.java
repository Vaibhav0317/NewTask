package com.newTask.controller;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.FinalPurchase;
import com.newTask.entities.Products;
import com.newTask.repo.BuyerRepository;
import com.newTask.repo.SellerRepository;
import com.newTask.services.BuyerService;
import com.newTask.services.SellerService;
import com.newTask.services.UserLoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BuyerController {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;


    @Autowired
    private UserLoginDetails userLoginDetails;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;




    String MsgFalse = "There are no Product in your List";
    HttpStatus statusNotOk = HttpStatus.NOT_FOUND;
    HttpStatus statusOk = HttpStatus.OK;

    @GetMapping("/showProducts")
    public ResponseEntity<?> showAll()
    {
            List<Products> list=this.sellerRepository.findAll();
            if(list.size()<=0)
            {
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                return new ResponseEntity<>(MsgFalse, statusNotOk);
            }
            return  ResponseEntity.of(Optional.of(list));

    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyProduct(@Valid @RequestBody BuyerProducts product)
    {
        int buyProduct=product.getPurchaseProduct();
        List<Products> list=this.sellerService.getAll();
        boolean isPresent=this.sellerService.isContains(list,product.getProductId());
        if(isPresent==true)
        {
            Products p = this.sellerRepository.findById(product.getProductId()).get();
            System.out.println("product is avaliable >=="+p);
            if (p.getStatus().equals("Available")) {
                //this.sellerRepository.deleteById(product.getBuyerProductId());
                int updateRemainingProduct=p.getNoOfProduct()-buyProduct;

                if(p.getNoOfProduct()>=buyProduct&&p.getNoOfProduct()!=0) {
                    Query query = new Query(Criteria.where("id").is(product.getProductId()));
                    Update update = new Update();
                    update.set("noOfProduct", updateRemainingProduct);
                    mongoTemplate.updateFirst(query, update, Products.class);

                    product.setProductName(p.getName());
                    product.setPrice(p.getPrice());
                    product.setStatus(p.getStatus());
                    product.setSellerName(p.getSellerName());
                    this.buyerService.addBuyProduct(product);
                    return new ResponseEntity<>("Product purchase Successfully", statusOk);
                }
                else {
                    return new ResponseEntity<>("Product Not Available", statusNotOk);
                }
            }
            else {
                return new ResponseEntity<>("Product Currently Not Available", statusNotOk);
            }
        }
        else
        {
            return new ResponseEntity<>("Product Not Found", statusNotOk);
        }
    }
    @GetMapping("/showPurches")
    public ResponseEntity<?> showPurchesedProduct()
    {
       // List<BuyerProducts> list=this.buyerService.showPurches();
        FinalPurchase list=this.buyerService.showPurches();

        System.out.println("list of purchase product >== "+list);
        /*if(list.size()<=0)
        {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }*/
        return  ResponseEntity.of(Optional.of(list));
    }

    @PutMapping("/rating/{id}")
    public ResponseEntity<?> rating(@Valid @RequestBody BuyerProducts product,@PathVariable int id)
    {
        String MsgTrue = "Product Id "+id+" Found Product Update Successfully";
        String MsgFalse = "Product Id "+id+" Not Found please enter correct Product Id";
        String details=userLoginDetails.details();
        List<BuyerProducts> list=this.buyerRepository.findAll();
        list=list.stream().filter(e->e.getBuyerName().equals(details)).collect(Collectors.toList());
        boolean isPresent=this.sellerService.isContainsProduct(list,id);
        System.out.println("inside rating");

        if(isPresent==true)
        {
            Query query = new Query(Criteria.where("id").is(id));
            Update update = new Update();
            update.set("productRating",product.getProductRating());
            update.set("sellerRating",product.getSellerRating());
            mongoTemplate.findAndModify(query, update, BuyerProducts.class);
            return new ResponseEntity<>(MsgTrue, statusOk);

        }
        else
        {
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
    }



}
