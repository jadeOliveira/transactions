package br.com.jademe.transactions.business.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TimelineDTO {

  private String accountId;
  private String data;

}
