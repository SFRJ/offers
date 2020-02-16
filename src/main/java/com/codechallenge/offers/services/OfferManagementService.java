package com.codechallenge.offers.services;

import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.domain.exceptions.OfferNotFoundException;
import com.codechallenge.offers.repositories.OffersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.codechallenge.offers.domain.OfferStatus.ACTIVE;
import static com.codechallenge.offers.domain.OfferStatus.CANCELLED;

@Service
@RequiredArgsConstructor
public class OfferManagementService {

    private final OffersRepository offersRepository;

    public UUID createOffer(String description, Double price, LocalDate expiration) {

        return offersRepository.createOffer(Offer.builder()
                .identifier(UUID.randomUUID())
                .descriptions(description)
                .price(price)
                .expirationDate(expiration)
                .offerStatus(ACTIVE)
                .build());
    }

    public Offer getOffer(UUID offerId) {

        return offersRepository.readOffer(offerId).map(offer -> {
            if(LocalDate.now().isAfter(offer.getExpirationDate())) {
                offer.setOfferStatus(CANCELLED);
                offersRepository.cancelOffer(offer);
            }
            return offer;
        })
                .orElseThrow(() -> new OfferNotFoundException("Unable to find offer with id " + offerId));
    }

    public Offer cancelOffer(UUID offerId) {

        Offer toBeCancelled = getOffer(offerId);
        toBeCancelled.setOfferStatus(CANCELLED);
        offersRepository.cancelOffer(toBeCancelled);
        return toBeCancelled;
    }

}
