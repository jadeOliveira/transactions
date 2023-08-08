package br.com.jademe.transactions.business.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "account", schema = "jademe")
@Builder
@AllArgsConstructor
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //@NotNull
  @Column(name = "document_number")
  private String documentNumber;

  @Column(name = "available_credit_limit")
  private BigDecimal availableCreditLimit;

  public Account() {
  }
}