package com.scottwseo.dvdstore.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * ProductCreate
 */
public class ProductCreate {

    private Long prodId = null;

    private Long category = null;

    private String title = null;

    private String actor = null;

    private BigDecimal price = null;

    private Long commonProdId = null;

    private Map error = null;

    public <T> T error(Map error) {
        this.error = error;
        return (T) this;
    }

    public Map error() {
        return this.error;
    }

    public ProductCreate prodId(Long prodId) {
        this.prodId = prodId;
        return this;
    }

    /**
     * Get prodId
     *
     * @return prodId
     **/
    @JsonProperty("prod_id")
    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public ProductCreate category(Long category) {
        this.category = category;
        return this;
    }

    /**
     * Get category
     *
     * @return category
     **/
    @JsonProperty("category")
    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public ProductCreate title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get title
     *
     * @return title
     **/
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductCreate actor(String actor) {
        this.actor = actor;
        return this;
    }

    /**
     * Get actor
     *
     * @return actor
     **/
    @JsonProperty("actor")
    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public ProductCreate price(BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Get price
     *
     * @return price
     **/
    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCreate commonProdId(Long commonProdId) {
        this.commonProdId = commonProdId;
        return this;
    }

    /**
     * Get commonProdId
     *
     * @return commonProdId
     **/
    @JsonProperty("common_prod_id")
    public Long getCommonProdId() {
        return commonProdId;
    }

    public void setCommonProdId(Long commonProdId) {
        this.commonProdId = commonProdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCreate productCreate = (ProductCreate) o;
        return Objects.equals(this.prodId, productCreate.prodId) &&
                Objects.equals(this.category, productCreate.category) &&
                Objects.equals(this.title, productCreate.title) &&
                Objects.equals(this.actor, productCreate.actor) &&
                Objects.equals(this.price, productCreate.price) &&
                Objects.equals(this.commonProdId, productCreate.commonProdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodId, category, title, actor, price, commonProdId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ProductCreate {\n");

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

