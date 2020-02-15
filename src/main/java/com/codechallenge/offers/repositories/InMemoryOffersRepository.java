package com.codechallenge.offers.repositories;

import com.codechallenge.offers.domain.Offer;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class InMemoryOffersRepository implements OffersRepository {

    private final Map<UUID, Offer> offers;

    @Override
    public UUID createOffer(Offer offer) {

        offers.put(offer.getIdentifier(), offer);
        return offer.getIdentifier();
    }

    @Override
    public Optional<Offer> readOffer(UUID uuid) {

        return Optional.ofNullable(offers.get(uuid));
    }

    @Override
    public void cancelOffer(Offer offer) {

        offers.replace(offer.getIdentifier(), offer);
    }

}
