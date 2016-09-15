package com.scottwseo.dvdstore.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Products
 */
public class Products {

    private Pagination pagination = null;

    private List<Product> items = new ArrayList<Product>();

    public Products pagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    /**
     * Get pagination
     *
     * @return pagination
     **/
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Products items(List<Product> items) {
        this.items = items;
        return this;
    }

    public Products addItemsItem(Product itemsItem) {
        this.items.add(itemsItem);
        return this;
    }

    /**
     * Get items
     *
     * @return items
     **/
    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Products products = (Products) o;
        return Objects.equals(this.pagination, products.pagination) &&
                Objects.equals(this.items, products.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pagination, items);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Products {\n");

        sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
        sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

