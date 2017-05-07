package com.kadet.best.controller;

import com.kadet.best.entity.Product;
import com.kadet.best.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  @GetMapping("/")
  public String list(@RequestParam(required = false) String category,
                     @RequestParam(defaultValue = "1") int page,
                     ModelMap modelMap) {
    val product = new Product();
    product.setName("My Project" + UUID.randomUUID());
    product.setDescription("My Description" + UUID.randomUUID());
    productRepository.save(product);
    modelMap.put("products", productRepository.findAll());
    return "products";
  }

}
