package com.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Running JPA entities");

		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("test");
		
		EntityManager entityManager = (EntityManager) factory.createEntityManager();

		entityManager.getTransaction().begin();

		User user = new User();

		user.setEmail("email");

		user.setFullname("fullname");

		user.setPassword("password");

		user.setName("name");

		entityManager.persist(user);

		entityManager.getTransaction().commit();

		entityManager.close();
		
		factory.close();
	}
}
