package io.github.yhugorocha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPriceDTO {

    public USDBRL USDBRL;
}
