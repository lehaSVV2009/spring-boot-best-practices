package com.kadet.best.dto;

import com.kadet.best.entity.Cart;
import lombok.Data;

@Data
public class CartIndexViewModel {

  private Cart cart;
  private String returnUrl;

}
