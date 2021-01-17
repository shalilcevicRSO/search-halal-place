package com.selma.halal.food.project.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.selma.halal.food.project.lib.SearchHalalPlaceMetadata;
import com.selma.halal.food.project.services.beans.SearchPlaceBean;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/searchplace")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class SearchHalalPlaceResource {

    private Logger log = Logger.getLogger(SearchHalalPlaceResource.class.getName());

    @Inject
    private SearchPlaceBean searchPlaceBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getSearchHalalPlaceMetadata() {
        List<SearchHalalPlaceMetadata> searchHalalPlaceMetadata = searchPlaceBean.getSearchHalalPlaceMetadataFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(searchHalalPlaceMetadata).build();

    }

    @GET
    @Path("/{id}")
    public Response getSearchHalalPlaceMetadata(@PathParam("id") Integer id) {

        SearchHalalPlaceMetadata searchHalalPlaceMetadata = searchPlaceBean.getSearchHalalPlaceMetadata(id);

        if (searchHalalPlaceMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(searchHalalPlaceMetadata).build();
    }

    // gets data from halal-place-catalog
    @GET
    @Path("/{city}/{country}")
    public String getSearchHalalPlaceMetadata(
            @PathParam("city") String city,
            @PathParam("country") String country) {

        String halalPlaceMetadata = searchFromHalalPlaceCatalogService(city, country);

        if (halalPlaceMetadata == null) {
            throw new NotFoundException();
        }

        return halalPlaceMetadata;
    }


    @POST
    public Response createSearchHalalPlaceMetadata(SearchHalalPlaceMetadata searchHalalPlaceMetadata) {

        if ((searchHalalPlaceMetadata.getName() == null || searchHalalPlaceMetadata.getCity() == null || searchHalalPlaceMetadata.getCountry() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else if (searchHalalPlaceMetadata.getCity() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            searchHalalPlaceMetadata = searchPlaceBean.createSearchHalalPlaceMetadata(searchHalalPlaceMetadata);

        }

        return Response.status(Response.Status.OK).entity(searchHalalPlaceMetadata).build();

    }

    @PUT
    @Path("/{id}")
    public Response putSearchHalalPlaceMetadata(@PathParam("id") Integer id,
                                          SearchHalalPlaceMetadata searchHalalPlaceMetadata) {

        searchHalalPlaceMetadata = searchPlaceBean.putSearchHalalPlaceMetadata(id, searchHalalPlaceMetadata);

        if (searchHalalPlaceMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSearchHalalPlaceMetadata(@PathParam("id") Integer id) {

        boolean deleted = searchPlaceBean.deleteSearchPlaceMetadata(id);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Connects to the halal-place-catalog service
    private String searchFromHalalPlaceCatalogService(String city, String country) {
        HttpResponse<String> response = null;
        try{
            Unirest.setTimeouts(0, 0);
            response =  Unirest.get("http://localhost:8080/v1/places/" + city + "/" + country)
                    .asString();

        }
        catch (UnirestException e) {
            e.printStackTrace();
        }
        return response.getBody();
    }


}
