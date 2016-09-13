package com.scottwseo.commons.service;

import com.google.inject.Inject;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.util.StringColumnMapper;

import java.util.List;

/**
 * Created by seos on 9/13/16.
 */
public class CategoryServiceImpl implements CategoryService {

    private DBI dbi;

    @Inject
    public CategoryServiceImpl(DBI dbi) {
        this.dbi = dbi;
    }

    public List<String> getCategories() {

        try (Handle h = dbi.open()) {
            String sql = "select * from public.categories";
            Query query = h.createQuery(sql.toString());

            return query.map(StringColumnMapper.INSTANCE).list();

        }
        catch (Exception e) {
            return null;
        }

    }

}
