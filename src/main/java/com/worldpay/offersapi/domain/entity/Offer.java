package com.worldpay.offersapi.domain.entity;

import java.time.Instant;

public class Offer {

    private int id;
    private Merchant merchant;
    private String description;
    private OfferStatus offerStatus;
    private Price price;
    private Instant expiryTime;

    public Offer(
        int id,
        Merchant merchant,
        String description,
        OfferStatus offerStatus,
        Price price,
        Instant expiryTime
    ) {

        if (merchant == null) {
            throw new IllegalArgumentException("merchant cannot be null.");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description cannot be null or empty.");
        }

        if (offerStatus == null) {
            throw new IllegalArgumentException("offerStatus cannot be null.");
        }

        if (price == null) {
            throw new IllegalArgumentException("price cannot be null.");
        }

        if (expiryTime == null) {
            throw new IllegalArgumentException("expiryTime cannot be null.");
        }

        this.id = id;
        this.merchant = merchant;
        this.description = description;
        this.offerStatus = offerStatus;
        this.price = price;
        this.expiryTime = expiryTime;
    }

    public int getId() {
        return id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public String getDescription() {
        return description;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(
        OfferStatus offerStatus
    ) {

        if (offerStatus == null) {
            throw new IllegalArgumentException("offerStatus cannot be null.");
        }

        this.offerStatus = offerStatus;
    }

    public Price getPrice() {
        return price;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }
}
