package com.nguyenduc.service.product;

import com.nguyenduc.model.Product;
import com.nguyenduc.repository.product.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Iterable<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAllByNameContaining(Pageable pageable, String name) {
        return productRepository.findAllByNameContaining(pageable, name);
    }

    @Override
    public Page<Product> findAllByCategoryContaining(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryContaining(categoryId, pageable);
    }
}
