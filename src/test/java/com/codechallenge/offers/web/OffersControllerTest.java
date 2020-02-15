package com.codechallenge.offers.web;

import com.codechallenge.offers.domain.Offer;
import com.codechallenge.offers.services.OfferManagementService;
import com.codechallenge.offers.services.OfferValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class OffersControllerTest {

    private OffersController offersController;
    private OfferValidator offerValidator;
    private ModelMapper modelMapper;
    private OfferManagementService offerManagementService;

    @Before
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        offerValidator = Mockito.mock(OfferValidator.class);
        offerManagementService = Mockito.mock(OfferManagementService.class);
        offersController = new OffersController(offerManagementService, offerValidator, modelMapper);
    }

    @Test
    public void shouldRouteCreationRequests() {

        CreateOfferRequest request = new CreateOfferRequest("Some description", 10D,
                "Some currency", LocalDate.now());

        Mockito.when(offerManagementService.createOffer(any(),any(),any())).thenReturn(UUID.randomUUID());

        assertThat(offersController.createOffer(request).getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(offerValidator).validate(request);
    }

    @Test
    public void shouldRouteGetRequests() {

        UUID offerId = UUID.randomUUID();
        Mockito.when(offerManagementService.createOffer(any(),any(),any())).thenReturn(offerId);
        Mockito.when(modelMapper.map(any(),any())).thenReturn(Offer.builder().identifier(offerId).build());

        assertThat(offersController.getOffer(offerId).getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(offerValidator).validate(offerId);
    }

    @Test
    public void shouldRouteCancelRequests() {
        UUID offerId = UUID.randomUUID();
        Mockito.when(offerManagementService.cancelOffer(offerId)).thenReturn(Offer.builder().identifier(offerId).build());
        Mockito.when(modelMapper.map(any(),any())).thenReturn(Offer.builder().identifier(offerId).build());

        assertThat(offersController.cancelOffer(offerId).getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(offerValidator).validate(offerId);
    }
}