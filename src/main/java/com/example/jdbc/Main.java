package com.example.jdbc;

public class Main{


	public static void main(String s[]) {

		UserDAO userDAO = new UserDAOImpl();

		//System.out.println(userDAO.selectById(3));
		System.out.println(userDAO.selectAll());

		/*User user = new User("Dawood", 56);

		userDAO.saveUser(user);

		user = new User(10, "Laden", 96);
		userDAO.updateUser(user);
		userDAO.deleteUser(4);*/

		System.out.println("Done!");

		//System.exit(0);
	}


}