package io.github.yhugorocha.service;

import io.github.yhugorocha.client.CurrencyPriceClient;
import io.github.yhugorocha.dto.CurrencyPriceDTO;
import io.github.yhugorocha.dto.QuotationDTO;
import io.github.yhugorocha.entity.QuotationEntity;
import io.github.yhugorocha.message.KafkaEvents;
import io.github.yhugorocha.repository.QuotationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;

    public void getCurrencyPrice(){

        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        if(updateCurrencyInfoPrice(currencyPriceInfo)){
            kafkaEvents.sendNewKafkaEvent(QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                    .date(LocalDateTime.now())
                    .build());
        }
    }

    private Boolean updateCurrencyInfoPrice(CurrencyPriceDTO currencyPriceInfo){
        Optional<QuotationEntity> quotation = quotationRepository.findAll().stream().max(Comparator.comparing(QuotationEntity::getCurrencyPrice));

        BigDecimal newValue = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());

        if(quotation.isEmpty() || newValue.floatValue() > quotation.get().getCurrencyPrice().floatValue()){
            this.saveQuotation(currencyPriceInfo);
            return true;
        }

        return false;
    }

    @Transactional
    public void saveQuotation(CurrencyPriceDTO currencyPriceInfo){
        var quotationEntity = new QuotationEntity();
        quotationEntity.setCurrencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()));
        quotationEntity.setDate(LocalDateTime.now());
        quotationEntity.setPair(currencyPriceInfo.getUSDBRL().getCode().concat("-").concat(currencyPriceInfo.getUSDBRL().getCodein()));
        quotationEntity.setPctChange(currencyPriceInfo.getUSDBRL().getPctChange());

        quotationRepository.persist(quotationEntity);
    }
}
