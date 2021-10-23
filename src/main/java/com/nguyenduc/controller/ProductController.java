package com.nguyenduc.controller;

import com.nguyenduc.model.Category;
import com.nguyenduc.model.Product;
import com.nguyenduc.model.ProductForm;
import com.nguyenduc.service.category.ICategoryService;
import com.nguyenduc.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(name = "q", required = false) String name,
                             @RequestParam(name = "categoryId", required = false) Long categoryId,
                             Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        Page<Product> products;
        if ((name == null || name.equals("")) && categoryId == null) {
            products = (Page<Product>) productService.findAll(pageable);
            return modelAndView.addObject("products", products);
        } else if (categoryId != null) {
            products = productService.findAllByCategoryContaining(categoryId, pageable);
            return modelAndView.addObject("products", products);
        } else {
            products = productService.findAllByNameContaining(pageable, name);
            return modelAndView.addObject("products", products);
        }
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        ProductForm productForm =  new ProductForm();
        return modelAndView.addObject("productForm", productForm);
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute(name = "productForm") ProductForm productForm, BindingResult bindingResult) throws IOException {
        ModelAndView modelAndView = new ModelAndView("product/create");
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
        Product product = new Product();
        product.setId(productForm.getId());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        product.setCategory(productForm.getCategory());
        product.setImage(fileName);
        productService.save(product);
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Created");
        return modelAndView;
    }
}
