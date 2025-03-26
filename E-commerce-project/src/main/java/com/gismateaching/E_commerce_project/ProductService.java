package com.gismateaching.E_commerce_project;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public static record NewProductRequest(Integer product_id, String product_name, String category, Double price, Integer stock, Integer supplier_id) {}

    public ResponseEntity<String> addProduct(NewProductRequest request) {
        Product product = new Product();
        product.setProduct_name(request.product_name);
        product.setCategory(request.category);
        product.setPrice(request.price);
        product.setStock(request.stock);
        product.setSupplier_id(request.supplier_id);
        productRepository.save(product);
        return ResponseEntity.ok("Product added");
    }

    public ResponseEntity<String> updateProduct(Integer product_id, NewProductRequest request ) {
        if (!productRepository.existsById(product_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + product_id + " not found");
        }
        Product product = productRepository.findById(product_id).get();
        product.setProduct_name(request.product_name);
        product.setCategory(request.category);
        product.setPrice(request.price);
        product.setStock(request.stock);
        product.setSupplier_id(request.supplier_id);
        productRepository.save(product);
        return ResponseEntity.ok("Product with ID " + product_id + " was updated");
    }

    public ResponseEntity<String> deleteProduct(Integer product_id) {
        if (!productRepository.existsById(product_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + product_id + " not found");
        }
        productRepository.deleteById(product_id);
        return ResponseEntity.ok("Product with ID " + product_id + " was deleted");
    }
}
