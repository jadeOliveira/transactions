package br.com.pismo.transactions.business.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDTO {

  private Long id;
  private String documentNumber;

}
