package com.scottwseo.dvdstore.resources;

import com.google.inject.Inject;
import com.scottwseo.dvdstore.service.CategoryService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by sseo on 9/6/16.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private CategoryService categoryService;

    @Inject
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GET
    @Path("/categories")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<String> categories() {
        return categoryService.getCategories();
    }

}