package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Supplier")
public class SupplierController {
    private final SupplierService supplierService;
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/get")
    public List<Supplier> getSuppliers() {
        return supplierService.getSupplier();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSupplier(@RequestBody SupplierService.NewSupplierRequest request) {
        return supplierService.addSupplier(request);
    }

    @PutMapping("/update/{supplierId}")
    public ResponseEntity<String> updateSupplier(@PathVariable Integer supplierId, @RequestBody SupplierService.NewSupplierRequest request) {
        return supplierService.updateSupplier(supplierId, request);
    }

    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Integer supplierId, @RequestBody SupplierService.NewSupplierRequest request) {
        return supplierService.deleteSupplier(supplierId, request);
    }

    //getting one suppliers product
    @GetMapping("/products/{supplierId}")
    public ResponseEntity<Supplier> getSupplierWithProducts(@PathVariable("supplierId") Integer supplierId) {
        Supplier supplier = supplierService.getSupplierWithProducts(supplierId);
        return ResponseEntity.ok(supplier);
    }
}
