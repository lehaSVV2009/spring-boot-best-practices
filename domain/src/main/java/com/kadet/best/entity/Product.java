package com.kadet.best.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String productId;
  private String name;
  private String description;
  private String category;
  private double price;
  private byte[] imageData;
  private String imageMimeType;

}
