package com.kadet.best.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Cart {

  private List<CartLine> lineCollection = new ArrayList<>();

  public void addItem(Product prod, int quantity) {
    CartLine line = lineCollection.stream()
        .filter(p -> p.getProduct().getProductId().equals(prod.getProductId()))
        .findFirst()
        .orElse(null);
    if (line == null) {
      lineCollection.add(new CartLine(prod, quantity));
    } else {
      line.setQuantity(line.getQuantity() + 1);
    }
  }

  public void removeLine(Product prod) {
    lineCollection.removeIf(p -> p.getProduct().getProductId().equals(prod.getProductId()));
  }

  public double ComputeTotalValue() {
    return lineCollection.stream().mapToDouble(line -> line.getProduct().getPrice() * line.getQuantity()).reduce(0, Double::sum);
  }

  public void Clear() {
    lineCollection.clear();
  }

  public List<CartLine> lines() {
    return lineCollection;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class CartLine {
    private Product product;
    private int quantity;
  }

}
