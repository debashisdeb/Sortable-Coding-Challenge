package com.m;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.io.Serializable;

/**
 * Created by m on 9/3/16.
 */
public class Result implements Serializable {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("listings")
    private List<Listing> matchedListings;

    public String getProductName() {
        return productName;
    }

    public Result setProductName(String product_name) {
        this.productName = product_name;
        return this;
    }

    public List<Listing> getMatchedListings() {
        return matchedListings;
    }

    public Result setMatchedListings(List<Listing> matched_listings) {
        this.matchedListings = matched_listings;
        return this;
    }
}
