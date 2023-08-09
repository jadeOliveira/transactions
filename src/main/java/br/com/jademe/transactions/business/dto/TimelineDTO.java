package br.com.jademe.transactions.business.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@Data
public class TimelineDTO {

  private Long accountId;

  public TimelineDTO() {
  }

  public TimelineDTO(Long accountId) {
    this.accountId = accountId;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }
//private String data;

}
