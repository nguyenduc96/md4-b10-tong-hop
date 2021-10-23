package com.nguyenduc.repository.product;

import com.nguyenduc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameContaining(Pageable pageable, String name);

    @Query("select p from Product p where p.category.id = ?1")
    Page<Product> findAllByCategoryContaining(@Param("1") Long categoryId, Pageable pageable);
}
