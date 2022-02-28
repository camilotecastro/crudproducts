package com.example.crudbasico.service;

import com.example.crudbasico.model.Product;
import com.example.crudbasico.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getOneProductById(long id){
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProductById(long id){
        productRepository.deleteById(id);
    }

    public boolean existById(long id){
        return productRepository.existsById(id);
    }

    public boolean existByName(String name){
        return productRepository.existsProductByName(name);
    }

}
