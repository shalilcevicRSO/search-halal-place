package com.selma.halal.food.project.services.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Produces;

public class PersistenceProducer {

    @PersistenceContext(unitName = "search-halal-place-jpa")
    private EntityManager entityManager;

    @Produces
    @ApplicationScoped
    public EntityManager entityManager(){
        return entityManager;
    }
}
