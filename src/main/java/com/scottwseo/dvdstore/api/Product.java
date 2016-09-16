package com.scottwseo.dvdstore.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;


/**
 * Product
 */
public class Product {

    private Long prodId = null;

    private Long category = null;

    private String title = null;

    private String actor = null;

    private BigDecimal price = null;

    private boolean special;

    private Long commonProdId = null;

    private Map error = null;

    public <T> T error(Map error) {
        this.error = error;
        return (T) this;
    }

    public Product prodId(Long prodId) {
        this.prodId = prodId;
        return this;
    }

    /**
     * Get prodId
     *
     * @return prodId
     **/
    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Product category(Long category) {
        this.category = category;
        return this;
    }

    /**
     * Get category
     *
     * @return category
     **/
    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Product title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get title
     *
     * @return title
     **/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product actor(String actor) {
        this.actor = actor;
        return this;
    }

    /**
     * Get actor
     *
     * @return actor
     **/
    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Get price
     *
     * @return price
     **/
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product commonProdId(Long commonProdId) {
        this.commonProdId = commonProdId;
        return this;
    }

    /**
     * Get commonProdId
     *
     * @return commonProdId
     **/
    public Long getCommonProdId() {
        return commonProdId;
    }

    public void setCommonProdId(Long commonProdId) {
        this.commonProdId = commonProdId;
    }


    @JsonProperty("special")
    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public Product special(boolean special) {
        this.special = special;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(this.prodId, product.prodId) &&
                Objects.equals(this.category, product.category) &&
                Objects.equals(this.title, product.title) &&
                Objects.equals(this.actor, product.actor) &&
                Objects.equals(this.price, product.price) &&
                Objects.equals(this.commonProdId, product.commonProdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodId, category, title, actor, price, commonProdId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Product {\n");

        sb.append("    prodId: ").append(toIndentedString(prodId)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    actor: ").append(toIndentedString(actor)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    commonProdId: ").append(toIndentedString(commonProdId)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

