package com.newTask.controller;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Products;
import com.newTask.repo.BuyerRepository;
import com.newTask.repo.SellerRepository;
import com.newTask.services.BuyerService;
import com.newTask.services.SellerService;
import com.newTask.services.UserLoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> buyProduct(@RequestBody BuyerProducts product)
    {
        List<Products> list=this.sellerService.getAll();
        boolean isPresent=this.sellerService.isContains(list,product.getBuyerProductId());
        if(isPresent==true)
        {
            Products p = this.sellerRepository.findById(product.getBuyerProductId()).get();
            if (p.getProductStatus().equals("Available")) {
                this.sellerRepository.deleteById(product.getBuyerProductId());
                product.setBuyerProduct(p);
                this.buyerService.addBuyProduct(product);
                return new ResponseEntity<>("Product purchase Successfully", statusOk);
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
        List<BuyerProducts> list=this.buyerService.showPurches();
        if(list.size()<=0)
        {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
        return  ResponseEntity.of(Optional.of(list));
    }

    @PutMapping("/rating/{id}")
    public ResponseEntity<?> rating(@RequestBody BuyerProducts product,@PathVariable int id)
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
            this.buyerService.modifyRating(product,id);
            return new ResponseEntity<>(MsgTrue, statusOk);

        }
        else
        {
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
    }



}
