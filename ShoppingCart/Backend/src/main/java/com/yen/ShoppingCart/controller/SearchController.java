package com.yen.ShoppingCart.controller;

import com.yen.ShoppingCart.model.Product;
import com.yen.ShoppingCart.model.dto.product.ProductDto;
import com.yen.ShoppingCart.service.CategoryService;
import com.yen.ShoppingCart.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

//    @GetMapping("/{keyWord}")
//    public ResponseEntity<List<ProductDto>> searchProducts(@PathVariable("keyWord") String keyWord) {
//        log.info(">>>> keyWord = {}", keyWord);
//        List<ProductDto> body = productService.listProducts();
//        return new ResponseEntity<List<ProductDto>>(body, HttpStatus.OK);
//    }

    @GetMapping("/api")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam("query") String query) {
        log.info(">>>> query = {}", query);
        //return productService.searchProducts(query);
        List<ProductDto> body = productService.listProducts();
        return new ResponseEntity<List<ProductDto>>(body, HttpStatus.OK);
    }

}
