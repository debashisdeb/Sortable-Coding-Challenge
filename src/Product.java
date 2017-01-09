package com.m;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by m on 9/3/16.
 */
public class Product {
    @JsonProperty("product_name")
    private String name;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("family")
    private String family;
    @JsonProperty("model")
    private String model;
    @JsonProperty("announced-date")
    private String announced_date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAnnounced_date() {
        return announced_date;
    }

    public void setAnnounced_date(String announced_date) {
        this.announced_date = announced_date;
    }
}
