package io.github.yhugorocha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDTO {
    private static final long serialVersionUID = 1L;
    private LocalDateTime date;
    private BigDecimal currencyPrice;
}
