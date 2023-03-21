package com.newTask.controller;

import com.newTask.entities.BuyerProducts;
import com.newTask.entities.Products;
import com.newTask.repo.BuyerRepository;
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
public class HomeController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserLoginDetails userLoginDetails;

    @Autowired
    private BuyerRepository buyerRepository;

    String MsgFalse = "There are no Product in your List";
    HttpStatus statusNotOk = HttpStatus.NOT_FOUND;
    HttpStatus statusOk = HttpStatus.OK;

    @GetMapping("/")
    public ResponseEntity<?> seller()
    {
       // System.out.println("user details >==="+details);
        return new ResponseEntity<>("<h1>Seller</h1>", statusOk);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> get()
    {
        List<Products> list=this.sellerService.getAll();
        if(list.size()<=0)
        {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
        return  ResponseEntity.of(Optional.of(list));

    }

    @GetMapping("/show")
    public ResponseEntity<?> userProduct()
    {
        String details=this.userLoginDetails.details();
        List<Products> list=this.sellerService.getAll();

        list=list.stream().filter(e->e.getSellerName().equals(details)).collect(Collectors.toList());
       // System.out.println("Seller product >=="+list);
        if(list.size()<=0)
        {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
        return  ResponseEntity.of(Optional.of(list));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable int id)
    {

        String details=this.userLoginDetails.details();
        String MsgFalse = "Product Id "+id+" Not Found please enter correct Product Id";
        List<Products> list=this.sellerService.getAll();
        list=list.stream().filter(e->e.getSellerName().equals(details)).collect(Collectors.toList());
        try {
            //Products p = sellerService.getById(id);
            Products p = null;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == id) {
                    System.out.println("if id is present>==" + list.get(i));
                    p = list.get(i);
                }
            }
            if (p == null) {
                return new ResponseEntity<>(MsgFalse, statusNotOk);
            }else {
                return new ResponseEntity<>(p, statusOk);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Products product)
    {
        String MsgTrue1 = "Product Added Successfully";
        String MsgFalse1 = "Product Id already present please use different Product id";
        List<Products> list=this.sellerService.getAll();
        boolean isPresent=false;
        isPresent=this.sellerService.isContains(list,product.getId());


        if(isPresent==true)
        {
            return new ResponseEntity<>(MsgFalse1, statusNotOk);
        }
        else
        {
            Products p=this.sellerService.addProduct(product);
            return new ResponseEntity<>(MsgTrue1, statusOk);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id)
    {
        String MsgTrue = "Product Id "+id+" Found Product Deleted Successfully";
        String MsgFalse1 = "Product Id "+id+" Not Found please enter correct Product Id";
        String details=this.userLoginDetails.details();
        List<Products> list=this.sellerService.getAll();
        list=list.stream().filter(e->e.getSellerName().equals(details)).collect(Collectors.toList());
        boolean isPresent=this.sellerService.isContains(list,id);
        if(isPresent==true)
        {
            Products product=this.sellerService.deleteProductById(id);
            return new ResponseEntity<>(MsgTrue, statusOk);
        }
        else
        {
            return new ResponseEntity<>(MsgFalse1, statusNotOk);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllProducts()
    {
        this.sellerService.deleteAllProduct();
        return new ResponseEntity<>("delete all", statusOk);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Products product, @PathVariable int id)
    {
        String MsgTrue = "Product Id "+id+" Found Product Update Successfully";
        String MsgFalse = "Product Id "+id+" Not Found please enter correct Product Id";
        String details=this.userLoginDetails.details();
        List<Products> list=this.sellerService.getAll();
        list=list.stream().filter(e->e.getSellerName().equals(details)).collect(Collectors.toList());
        boolean isPresent=this.sellerService.isContains(list,id);
        if(isPresent==true)
        {
            this.sellerService.modify(product,id);
            return new ResponseEntity<>(MsgTrue, statusOk);

        }
        else
        {
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
    }


    @GetMapping("/allPurchase")
    public ResponseEntity<?> allBuyProduct()
    {
        List<BuyerProducts> list=this.buyerRepository.findAll();
        if(list.size()<=0)
        {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(MsgFalse, statusNotOk);
        }
        return  ResponseEntity.of(Optional.of(list));
    }

}
