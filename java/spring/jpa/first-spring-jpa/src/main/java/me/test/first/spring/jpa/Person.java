package me.test.first.spring.jpa;

import java.util.Collection;

import org.springframework.stereotype.Repository;

@Repository
public class Person {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Collection loadProductsByCategory(String category) {
		return entityManager
				.createQuery("from Product p where p.category = :category")
				.setParameter("category", category).getResultList();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
