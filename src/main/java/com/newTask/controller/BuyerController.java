package com.newTask.controller;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Products;
import com.newTask.repo.SellerRepository;
import com.newTask.services.BuyerService;
import com.newTask.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BuyerController {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;



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



}
