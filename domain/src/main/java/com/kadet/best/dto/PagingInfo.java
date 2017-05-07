§package com.kadet.best.dto;

import lombok.Data;

@Data
public class PagingInfo {

  private int totalItems;
  private int itemsPerPage;
  private int currentPage;
  private int totalPages;

  public int getTotalPages() {
    return (int) Math.ceil((double) totalItems / itemsPerPage);
  }

}
