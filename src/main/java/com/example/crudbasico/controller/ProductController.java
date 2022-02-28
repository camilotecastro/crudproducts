package com.example.crudbasico.controller;

import com.example.crudbasico.dto.Message;
import com.example.crudbasico.dto.ProductDto;
import com.example.crudbasico.model.Product;
import com.example.crudbasico.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProduct(){
        List<Product> list = productService.listProducts();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> getOneProductById(@PathVariable("id") long id){
        if (!productService.existById(id))
            return new ResponseEntity(new Message("No exist"), HttpStatus.NOT_FOUND);
        Product product = productService.getOneProductById(id).get();
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @GetMapping("/detailName{name}")
    public ResponseEntity<List<Product>> getOneProductByName(@PathVariable("name") String name){
        if (!productService.existByName("name"))
            return new ResponseEntity(new Message("No exist"), HttpStatus.NOT_FOUND);
        Product product = productService.getProductByName(name).get();
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto){
        if(productDto.getName().isBlank())
            return new ResponseEntity(new Message("Product name is required"), HttpStatus.BAD_REQUEST);
        if(productDto.getPrice() == null || productDto.getPrice() < 0)
            return new ResponseEntity(new Message("Product price must be greater than zero"), HttpStatus.BAD_REQUEST);
        if(productService.existByName(productDto.getName()))
            return new ResponseEntity(new Message("Product name exist"), HttpStatus.BAD_REQUEST);
        Product product = new Product(productDto.getName(), productDto.getPrice());
        productService.saveProduct(product);
        return new ResponseEntity(new Message("Product created"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDto productDto){
        if (!productService.existById(id))
            return new ResponseEntity(new Message("The product does not exist"), HttpStatus.NOT_FOUND);
        if(productDto.getName().isBlank())
            return new ResponseEntity(new Message("Product name is required"), HttpStatus.BAD_REQUEST);
        if(productService.existByName(productDto.getName()) && productService.getProductByName(productDto.getName()).get().getId() != id)
            return new ResponseEntity(new Message("Product name exist"), HttpStatus.BAD_REQUEST);
        if(productDto.getPrice() == null || productDto.getPrice() < 0)
            return new ResponseEntity(new Message("Product price must be greater than zero"), HttpStatus.BAD_REQUEST);

        Product product = productService.getOneProductById(id).get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productService.saveProduct(product);
        return new ResponseEntity(new Message("Product update"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProductById(@PathVariable long id){
        if (!productService.existById(id))
            return new ResponseEntity(new Message("No exist"), HttpStatus.NOT_FOUND);
        productService.deleteProductById(id);
        return new ResponseEntity(new Message("Product delete"), HttpStatus.OK);
    }

}
