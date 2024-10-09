package io.github.yhugorocha.scheduler;

import io.github.yhugorocha.service.QuotationService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {

    @Inject
    QuotationService quotationService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ConfigProperty(name = "quarkus.message", defaultValue = "Buscando cotacao")
    private String message;

    @Scheduled(every = "30s", identity = "task-job")
    void getQuotation(){
        LOG.info("-- "+message+" --");
        quotationService.getCurrencyPrice();
    }
}
