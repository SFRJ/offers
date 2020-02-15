package com.codechallenge.offers.web;

import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.services.OfferValidator;
import com.codechallenge.offers.services.OfferManagementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OffersController {

    private final OfferManagementService offerManagementService;
    private final OfferValidator offerValidator;
    private final ModelMapper modelMapper;

    @RequestMapping(value = "/offers/create", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<UUID> createOffer(@RequestBody CreateOfferRequest createOfferRequest) {

        offerValidator.validate(createOfferRequest);

        return ResponseEntity.ok(offerManagementService
                .createOffer(createOfferRequest.getDescription(), createOfferRequest.getPrice(), createOfferRequest.getExpiration()));
    }

    @RequestMapping(value = "/offers/{offerId}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getOffer(@PathVariable("offerId") UUID offerId) {

        offerValidator.validate(offerId);

        return ResponseEntity.ok(modelMapper.map(offerManagementService.getOffer(offerId), Offer.class));
    }

    @RequestMapping(value = "/offers/{offerId}/cancel", method = RequestMethod.GET)
    public ResponseEntity<Offer> cancelOffer(@PathVariable("offerId") UUID offerId) {

        offerValidator.validate(offerId);

        return ResponseEntity.ok(modelMapper.map(offerManagementService.cancelOffer(offerId), Offer.class));
    }
}
