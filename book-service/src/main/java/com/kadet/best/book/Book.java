package com.kadet.best.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Simple book metadata.
 */
@Data
@Accessors(chain = true)
@Entity
public class Book {

  /**
   * Unique book id like '1'.
   */
  @Id
  @GeneratedValue
  private String id;

  /**
   * Book display name like 'Harry Potter'.
   * No limit.
   */
  private String name;

  /**
   * Book notes.
   * No limit.
   */
  private String description;
}
