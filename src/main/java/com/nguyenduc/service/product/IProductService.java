package com.nguyenduc.service.product;

import com.nguyenduc.model.Product;
import com.nguyenduc.service.general.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IProductService extends IGeneralService<Product> {
    Page<Product> findAllByNameContaining(Pageable pageable, String name);

    Page<Product> findAllByCategoryContaining(Long categoryId, Pageable pageable);

    Optional<Product> findOne(Long id);
}
