package com.kadet.best.dto;

import com.kadet.best.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductListViewModel {

  private List<Product> products;
  private PagingInfo pagingInfo;
  private String currentCategory;

}
