package br.com.jademe.transactions.business.entity;

import com.sun.istack.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "account", schema = "jademe")
@Builder
@AllArgsConstructor
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "document_number")
  private String documentNumber;

  @Column(name = "available_credit_limit")
  private BigDecimal availableCreditLimit;

  public Account() {
  }
}