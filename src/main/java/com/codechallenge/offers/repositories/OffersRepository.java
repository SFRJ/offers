package com.codechallenge.offers.repositories;

import com.codechallenge.offers.domain.Offer;

import java.util.Optional;
import java.util.UUID;

public interface OffersRepository {

    UUID createOffer(Offer offer);
    Optional<Offer> readOffer(UUID uuid);
    void cancelOffer(Offer offer);

}
