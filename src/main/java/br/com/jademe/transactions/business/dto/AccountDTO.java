package br.com.jademe.transactions.business.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDTO {

  private Long id;
  private String documentNumber;
  private BigDecimal availableCreditLimit;

}
