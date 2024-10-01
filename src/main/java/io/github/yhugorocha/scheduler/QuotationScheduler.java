package io.github.yhugorocha.scheduler;

import io.github.yhugorocha.service.QuotationService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {

    @Inject
    QuotationService quotationService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Scheduled(every = "35s", identity = "task-job")
    void getQuotation(){
        LOG.info("-- Buscando cotacao --");
        quotationService.getCurrencyPrice();
    }
}
