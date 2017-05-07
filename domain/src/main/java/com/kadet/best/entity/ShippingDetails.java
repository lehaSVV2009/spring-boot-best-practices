package com.kadet.best.entity;

import lombok.Data;

@Data
public class ShippingDetails {

  private String name;
  private String line1;
  private String line2;
  private String line3;
  private String city;
  private String country;
  private boolean giftWrap;

}
