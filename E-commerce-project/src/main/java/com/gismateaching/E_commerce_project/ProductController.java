package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping ("/Product")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get")
    public List<Product> getProducts() {
        return productService.getProduct();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductService.NewProductRequest request) {
        return productService.addProduct(request);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer productId, @RequestBody ProductService.NewProductRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId, @RequestBody ProductService.NewProductRequest request) {
        return productService.deleteProduct(productId);
    }
}
