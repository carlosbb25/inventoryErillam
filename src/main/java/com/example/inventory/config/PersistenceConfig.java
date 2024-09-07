// config/PersistenceConfig.java
package com.example.inventory.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PersistenceConfig {
    @PersistenceContext(unitName = "inventoryPU")
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
