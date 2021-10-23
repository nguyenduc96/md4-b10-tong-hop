package com.nguyenduc.service.general;

import com.nguyenduc.model.Product;
import org.springframework.data.domain.Pageable;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Iterable<T> findAll(Pageable pageable);

    T save(T t);

    void delete(Long id);
}
