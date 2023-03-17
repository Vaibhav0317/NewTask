package com.newTask.repo;

import com.newTask.entities.BuyerProducts;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuyerRepository extends MongoRepository<BuyerProducts,Integer> {

}
