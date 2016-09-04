package com.m;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by m on 9/3/16.
 */
public class Listing {
    @JsonProperty("title")
    private String title;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("price")
    private String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
