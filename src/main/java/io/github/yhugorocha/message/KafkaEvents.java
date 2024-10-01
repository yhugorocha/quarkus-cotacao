package io.github.yhugorocha.message;

import io.github.yhugorocha.dto.QuotationDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationDTOEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation){
        LOG.info("-- Enviando Cotacao para Topico kafka --");
        quotationDTOEmitter.send(quotation).toCompletableFuture().join();
    }
}
