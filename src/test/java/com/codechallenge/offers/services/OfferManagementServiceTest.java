package com.codechallenge.offers.services;

import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.domain.OfferStatus;
import com.codechallenge.offers.domain.exceptions.OfferNotFoundException;
import com.codechallenge.offers.repositories.OffersRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferManagementServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OffersRepository offersRepository;
    private OfferManagementService offerManagementService;

    @Before
    public void setUp() {

        offersRepository = Mockito.mock(OffersRepository.class);
        offerManagementService = new OfferManagementService(offersRepository);
    }

    @Test
    public void shouldCreateOffer() {

        UUID someIdentifier = UUID.randomUUID();
        Mockito.when(offersRepository.createOffer(Mockito.any())).thenReturn(someIdentifier);
        UUID offerId = offerManagementService.createOffer(null, 0D, null);

        Mockito.verify(offersRepository).createOffer(Mockito.any());
        assertThat(offerId).isEqualTo(someIdentifier);
    }

    @Test
    public void shouldGetOffer() {

        UUID someIdentifier = UUID.randomUUID();
        Offer offer = Offer.builder().identifier(someIdentifier).build();
        Mockito.when(offersRepository.readOffer(someIdentifier)).thenReturn(Optional.of(offer));

        assertThat(offerManagementService.getOffer(someIdentifier)).isEqualTo(offer);
    }

    @Test
    public void shouldNotGetOffer() {

        UUID offerId = UUID.randomUUID();
        Mockito.when(offersRepository.readOffer(Mockito.any())).thenReturn(Optional.empty());

        expectedException.expect(OfferNotFoundException.class);
        expectedException.expectMessage("Unable to find offer with id " + offerId);

        offerManagementService.getOffer(offerId);
    }

    @Test
    public void shouldCancelOffer() {

        UUID offerId = UUID.randomUUID();
        Offer offer = Offer.builder().identifier(offerId).offerStatus(OfferStatus.ACTIVE).build();
        Mockito.when(offersRepository.readOffer(offerId)).thenReturn(Optional.of(offer));

        Offer canceledOffer = offerManagementService.cancelOffer(offerId);

        assertThat(canceledOffer.getIdentifier()).isEqualTo(offerId);
        assertThat(canceledOffer.getOfferStatus()).isEqualTo(OfferStatus.CANCELLED);
    }
}
