package com.user12043.fxrate.dto;


/**
 * The DTO object to respond for exchange rate request
 *
 * @param base
 * @param target
 * @param rate
 */
public record RateResponse(String base, String target, double rate) {
}
