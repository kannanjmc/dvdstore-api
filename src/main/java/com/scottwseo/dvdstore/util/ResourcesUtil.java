package com.scottwseo.dvdstore.util;

import com.scottwseo.dvdstore.api.Pagination;

/**
 * Created by seos on 9/16/16.
 */
public class ResourcesUtil {

    public static Pagination paginate(String uri, long startPage, long pageSize, long totalRecords) {
        if (startPage == 0) {
            startPage = 1;
        }

        boolean hasMore = (totalRecords > (startPage * pageSize));

        Long previousPage = (startPage > 1) ? (startPage - 1) : null;
        String prev = previousPage == null ? null : uri + "?" + "start=" + previousPage + "&size=" + pageSize;

        Long nextPage = (hasMore) ? startPage + 1 : null;
        String next = nextPage == null ? null : uri + "?" + "start=" + nextPage + "&size=" + pageSize;

        return new Pagination()
                .start(startPage)
                .next(next)
                .prev(prev)
                .total(totalRecords)
                .resultSize(pageSize)
                .totalPages(totalRecords / pageSize + ((totalRecords % pageSize) == 0 ? 0 : 1));
    }

}
