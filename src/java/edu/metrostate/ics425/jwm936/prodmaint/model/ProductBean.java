/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.metrostate.ics425.jwm936.prodmaint.model;

import edu.metrostate.ics425.prodmaint.model.Product;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author josephmunson
 */
public class ProductBean implements Product, Serializable {

    private int productId;
    private String code;
    private String description;
    private Double price;
    private LocalDate releaseDate;

    /**
     * Gets the product ID
     *
     * @return the product ID
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the product ID
     *
     * @param productId Id of the product
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the product code
     *
     * @return the product code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Gets the product description
     *
     * @return product description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the product price
     *
     * @return product price
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * Gets the date when the product was or will be released
     *
     * @return product release date
     */
    @Override
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the number of whole years since the product was released
     *
     * @return Number of years since product release, or -1 if release date is
     * in the future or -2 if releaseDate is null
     *
     */
    @Override
    public int getYearsReleased() {
        //check for null
        if (releaseDate == null) {
            return UNSET_RELEASE_DATE;
        }

        //check for future date
        LocalDate today = LocalDate.now();
        if (releaseDate.isAfter(today)) {
            return FUTURE_RELEASE_DATE;
        }

        //calculate difference in years
        int difference = today.getYear() - releaseDate.getYear();

        if (difference == 0) {
            return 0;
        } else {
            //if today's date is before the anniversary of the release, 
            //it does not count as a full year, so subtract 1 year
            if (today.getDayOfYear() < releaseDate.getDayOfYear()) {
                return difference - 1;
            } else {
                return difference;
            }
        }
    }

    /**
     * Sets the value of the product code
     *
     * @param code product code
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the product description
     *
     * @param description product description
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the product's price
     *
     * @param price price of product
     */
    @Override
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Sets the date when the product was (or will be) released
     *
     * @param date release date of product
     */
    @Override
    public void setReleaseDate(LocalDate date) {
        this.releaseDate = date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductBean other = (ProductBean) obj;

        if (this.code == null || other.code == null) {
            if (this.code == other.code) {
                return true;
            } else {
                return false;
            }
        }
        if (!Objects.equals(this.code.toLowerCase(), other.code.toLowerCase())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Code= " + code + ", Description= " + description + ", Price= " + price + ", ReleaseDate= " + releaseDate;
    }

}
