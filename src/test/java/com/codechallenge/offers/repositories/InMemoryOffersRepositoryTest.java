package com.codechallenge.offers.repositories;

import com.codechallenge.offers.domain.Offer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryOffersRepositoryTest {

    private Map database;
    private InMemoryOffersRepository inMemoryOffersRepository;

    @Before
    public void setUp() {
        database = Mockito.mock(Map.class);
        inMemoryOffersRepository = new InMemoryOffersRepository(database);
    }

    @Test
    public void shouldInsertInDatabase() {

        Offer someOffer = Offer.builder().identifier(UUID.randomUUID()).build();
        UUID offerId = inMemoryOffersRepository.createOffer(someOffer);

        Mockito.verify(database).put(someOffer.getIdentifier(), someOffer);
        assertThat(someOffer.getIdentifier()).isEqualTo(offerId);
    }

    @Test
    public void shouldReadOffer() {

        UUID offerId = UUID.randomUUID();
        Mockito.when(database.get(offerId)).thenReturn(Offer.builder().identifier(offerId).build());

        Optional<Offer> offer = inMemoryOffersRepository.readOffer(offerId);

        assertThat(offer.get().getIdentifier()).isEqualTo(offerId);
    }

    @Test
    public void shouldCancelOffer() {

        Offer someOffer = Offer.builder().identifier(UUID.randomUUID()).build();

        inMemoryOffersRepository.cancelOffer(someOffer);

        Mockito.verify(database).replace(someOffer.getIdentifier(), someOffer);
    }
}