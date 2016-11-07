package com.scottwseo.commons.rest.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.scottwseo.commons.util.LogUtil.map;

/**
 * Created by sseo on 9/19/16.
 */
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {

    @Override
    public Response toResponse(UnrecognizedPropertyException exception) {
        return Response.status(400).entity(map("json.mapping.error", exception.getMessage())).build();
    }
}
