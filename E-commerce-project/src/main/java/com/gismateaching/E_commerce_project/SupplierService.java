package com.gismateaching.E_commerce_project;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getSupplier() {
        return supplierRepository.findAll();
    }

    public static record NewSupplierRequest(Integer supplier_id, String supplier_name, String supplier_phone, String supplier_email){}
    public ResponseEntity<String> addSupplier(NewSupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setSupplier_name(request.supplier_name);
        supplier.setSupplier_phone(request.supplier_phone);
        supplier.setSupplier_email(request.supplier_email);
        supplierRepository.save(supplier);
        return ResponseEntity.ok("Supplier added successfully");
    }

    public ResponseEntity<String> updateSupplier(Integer supplier_id, NewSupplierRequest request) {
        if (!supplierRepository.existsById(supplier_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Supplier  with ID " + supplier_id + " not found");
        }
        Supplier supplier = new Supplier();
        supplier.setSupplier_name(request.supplier_name);
        supplier.setSupplier_phone(request.supplier_phone);
        supplier.setSupplier_email(request.supplier_email);
        supplierRepository.save(supplier);
        return ResponseEntity.ok("Supplier with ID " + supplier_id + " updated successfully");
    }

    public ResponseEntity<String> deleteSupplier(Integer supplier_id, NewSupplierRequest request) {
        if (!supplierRepository.existsById(supplier_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Supplier  with ID " + supplier_id + " not found");
        }
        supplierRepository.deleteById(supplier_id);
        return ResponseEntity.ok("Supplier with ID " + supplier_id + " deleted successfully");
    }


    //getiing products of one supplier
    @Transactional(readOnly = true)
    public Supplier getSupplierWithProducts(Integer supplier_id) {
            return supplierRepository.findSupplierWithProducts(supplier_id)
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplier_id));
    }
}
