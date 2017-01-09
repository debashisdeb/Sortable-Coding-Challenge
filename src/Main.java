package com.m;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String, List<Product>> manufacturerToProductMap = parseProductFile("./challenge_data/products.txt");
        Map<String, List<Listing>> matchedList = matchListings("./challenge_data/listings.txt", manufacturerToProductMap);
        writeToJson(matchedList);
    }

    private static Map<String, List<Product>> parseProductFile(String filepath) {
        Map<String, List<Product>> manufacturerToProductMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            File productFile = new File(filepath);
            try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
                String product;
                while ((product = br.readLine()) != null) {
                    Product p = mapper.readValue(product, Product.class);
                    if (!manufacturerToProductMap.containsKey(p.getManufacturer())) {
                        manufacturerToProductMap.put(p.getManufacturer(), new ArrayList<>());
                    }
                    List<Product> productListForManufacturer = manufacturerToProductMap.get(p.getManufacturer());
                    productListForManufacturer.add(p);
                    manufacturerToProductMap.put(p.getManufacturer(), productListForManufacturer);
                }
            }
        } catch (JsonGenerationException ex) {
            ex.printStackTrace();
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return manufacturerToProductMap;
    }

    private static Map<String, List<Listing>> matchListings(String filepath, Map<String, List<Product>> manufacturerToProductMap) {
        Set<String> manufacturers = manufacturerToProductMap.keySet();
        Map<String, List<Listing>> matchedResults = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            File listingFile = new File(filepath);
            try (BufferedReader br = new BufferedReader(new FileReader(listingFile))) {
                String listing;
                while ((listing = br.readLine()) != null) {
                    Listing l = mapper.readValue(listing, Listing.class);
                    String[] manufacturerTokens = l.getManufacturer().split(" ");

                    for (String manufacturerFromListing : manufacturerTokens) {
                        if (manufacturers.contains(manufacturerFromListing)) {
                            List<Product> productsForManufacturer = manufacturerToProductMap.get(manufacturerFromListing);
                            for (Product p : productsForManufacturer) {
                                String productModel = p.getModel();
                                String[] listingTitleTokens = l.getTitle().split(" ");
                                for (String listingModel : listingTitleTokens) {
                                    if (listingModel.toLowerCase().equals(productModel.toLowerCase())) {
                                        String productName = p.getName();
                                        if (!matchedResults.containsKey(productName)) {
                                            matchedResults.put(productName, new ArrayList<>());
                                        }
                                        List<Listing> matchedListingsForProduct = matchedResults.get(productName);
                                        matchedListingsForProduct.add(l);
                                        matchedResults.put(productName, matchedListingsForProduct);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (JsonGenerationException ex) {
            ex.printStackTrace();
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return matchedResults;
    }

    private static void writeToJson(Map<String, List<Listing>> object) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<String> toWrite = new ArrayList<>();
        for (String key : object.keySet()) {
            Result r = new Result();
            r.setProductName(key).setMatchedListings(object.get(key));
            try {
                String json = ow.writeValueAsString(r);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                JsonNode df = mapper.readValue(json, JsonNode.class);
                toWrite.add(df.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path file = Paths.get("./challenge_data/result.txt");
        try {
            Files.write(file, toWrite, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
