package com.example.jdbc;

import java.util.List;

public interface UserDAO {

	public abstract List<User> selectAll();

	public abstract User selectById(int id);

	public abstract void deleteUser(int id);

	public abstract void updateUser(User user);

	public abstract void saveUser(User user);

}