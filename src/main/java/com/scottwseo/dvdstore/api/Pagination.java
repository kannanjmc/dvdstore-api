package com.scottwseo.dvdstore.api;

import java.util.Objects;


public class Pagination {

    private Long start = null;

    private Long resultSize = null;

    private String next = null;

    private String prev = null;

    private Long total = null;

    private Long totalPages = null;



    public Pagination start(Long start) {
        this.start = start;
        return this;
    }

    /**
     * requested starting index of page
     *
     * @return start
     **/
    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Pagination resultSize(Long resultSize) {
        this.resultSize = resultSize;
        return this;
    }

    /**
     * number of items per page
     *
     * @return resultSize
     **/
    public Long getResultSize() {
        return resultSize;
    }

    public void setResultSize(Long resultSize) {
        this.resultSize = resultSize;
    }

    public Pagination next(String next) {
        this.next = next;
        return this;
    }

    /**
     * url to next page
     *
     * @return next
     **/
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Pagination prev(String prev) {
        this.prev = prev;
        return this;
    }

    /**
     * url to previous page
     *
     * @return prev
     **/
    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public Pagination total(Long total) {
        this.total = total;
        return this;
    }

    /**
     * total number of items
     *
     * @return total
     **/
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Pagination totalPages(Long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * total number of pages
     *
     * @return totalPages
     **/
    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pagination pagination = (Pagination) o;
        return Objects.equals(this.start, pagination.start) &&
                Objects.equals(this.resultSize, pagination.resultSize) &&
                Objects.equals(this.next, pagination.next) &&
                Objects.equals(this.prev, pagination.prev) &&
                Objects.equals(this.total, pagination.total) &&
                Objects.equals(this.totalPages, pagination.totalPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, resultSize, next, prev, total, totalPages);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Pagination {\n");

        sb.append("    start: ").append(toIndentedString(start)).append("\n");
        sb.append("    resultSize: ").append(toIndentedString(resultSize)).append("\n");
        sb.append("    next: ").append(toIndentedString(next)).append("\n");
        sb.append("    prev: ").append(toIndentedString(prev)).append("\n");
        sb.append("    total: ").append(toIndentedString(total)).append("\n");
        sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
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

