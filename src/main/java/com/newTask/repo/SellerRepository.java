package com.newTask.repo;

import com.newTask.entities.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SellerRepository extends MongoRepository<Products,Integer> {



}
