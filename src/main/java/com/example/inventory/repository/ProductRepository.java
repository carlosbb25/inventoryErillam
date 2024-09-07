// repository/ProductRepository.java
package com.example.inventory.repository;

import com.example.inventory.entity.Product;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductRepository {
    @PersistenceContext(unitName = "inventoryPU")
    private EntityManager entityManager;

    public void create(Product product) {
        entityManager.persist(product);
    }

    public Product update(Product product) {
        return entityManager.merge(product);
    }

    public void delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> findAll(int page, int size) {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Product> findByNameAndCategory(String name, String category) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name AND p.category = :category", Product.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("category", category)
                .getResultList();
    }
}
