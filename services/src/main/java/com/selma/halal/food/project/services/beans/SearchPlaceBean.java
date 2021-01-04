package com.selma.halal.food.project.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.selma.halal.food.project.lib.SearchHalalPlaceMetadata;
import com.selma.halal.food.project.models.converters.SearchPlaceMetadataConverter;
import com.selma.halal.food.project.models.entities.SearchPlaceMetadataEntity;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class SearchPlaceBean {

    private Logger log = Logger.getLogger(SearchPlaceBean.class.getName());

    @PersistenceContext(unitName = "search-halal-place-jpa")
    private EntityManager em;

    public List<SearchHalalPlaceMetadata> getSearchHalalPlaceMetadata() {

        TypedQuery<SearchPlaceMetadataEntity> query = em.createNamedQuery(
                "SearchPlaceMetadataEntity.getAll", SearchPlaceMetadataEntity.class);

        List<SearchPlaceMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(SearchPlaceMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<SearchHalalPlaceMetadata> getSearchHalalPlaceMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, SearchPlaceMetadataEntity.class, queryParameters).stream()
                .map(SearchPlaceMetadataConverter::toDto).collect(Collectors.toList());
    }

    public SearchHalalPlaceMetadata getSearchHalalPlaceMetadata(Integer id) {

        SearchPlaceMetadataEntity searchPlaceMetadataEntity = em.find(SearchPlaceMetadataEntity.class, id);

        if (searchPlaceMetadataEntity == null) {
            throw new NotFoundException();
        }

        SearchHalalPlaceMetadata searchHalalPlaceMetadata = SearchPlaceMetadataConverter.toDto(searchPlaceMetadataEntity);
        return searchHalalPlaceMetadata;
    }

    public SearchHalalPlaceMetadata getSearchHalalPlaceMetadata(String city) {

        SearchPlaceMetadataEntity searchPlaceMetadataEntity = em.find(SearchPlaceMetadataEntity.class, city);

        if (searchPlaceMetadataEntity == null) {
            throw new NotFoundException();
        }

        SearchHalalPlaceMetadata searchHalalPlaceMetadata = SearchPlaceMetadataConverter.toDto(searchPlaceMetadataEntity);
        return searchHalalPlaceMetadata;
    }

    public SearchHalalPlaceMetadata createSearchHalalPlaceMetadata(SearchHalalPlaceMetadata searchHalalPlaceMetadata) {

        SearchPlaceMetadataEntity searchPlaceMetadataEntity = SearchPlaceMetadataConverter.toEntity(searchHalalPlaceMetadata);

        try {
            beginTx();
            em.persist(searchPlaceMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (searchPlaceMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return SearchPlaceMetadataConverter.toDto(searchPlaceMetadataEntity);
    }

    public SearchHalalPlaceMetadata putSearchHalalPlaceMetadata(Integer id, SearchHalalPlaceMetadata searchHalalPlaceMetadata) {

        SearchPlaceMetadataEntity c = em.find(SearchPlaceMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        SearchPlaceMetadataEntity updateSearchPlaceMetadataEntity = SearchPlaceMetadataConverter.toEntity(searchHalalPlaceMetadata);

        try {
            beginTx();
            updateSearchPlaceMetadataEntity.setId(c.getId());
            updateSearchPlaceMetadataEntity = em.merge(updateSearchPlaceMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return SearchPlaceMetadataConverter.toDto(updateSearchPlaceMetadataEntity);
    }

    public boolean deleteSearchPlaceMetadata(Integer id) {

        SearchPlaceMetadataEntity  searchPlaceMetadata= em.find(SearchPlaceMetadataEntity.class, id);

        if (searchPlaceMetadata != null) {
            try {
                beginTx();
                em.remove(searchPlaceMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return  false;
        }

        return true;
    }


    private void beginTx() {
        if(!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}
