package com.jdriven.product_catalog.infrastructure.controller;

import com.jdriven.product_catalog.application.service.PriceSynchronizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.SyncApi;
import org.openapitools.model.PriceUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UpdateProductController implements SyncApi {

    private final PriceSynchronizeService priceSynchronizeService;

    @Override
    public ResponseEntity<Void> syncPricesPost(List<PriceUpdate> priceUpdate) {
        log.info("Incoming sync prices request");
        priceSynchronizeService.updatePrices(PresentationPriceUpdateMapper.INSTANCE
                .presentationPricesToPriceUpdates(priceUpdate));
        return ResponseEntity.noContent().build();
    }
}
