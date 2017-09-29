package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements UserDAO {

	Connection connection=null;
	
	public Connection createConnection() throws ClassNotFoundException,SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/training","root","root");
	}

	public List<User> selectAll(){
		PreparedStatement statement=null;
		ResultSet resultSet = null;
		List<User> list=new ArrayList<User>();
		
		try{
			connection=this.createConnection();
			statement=connection.prepareStatement("select * from users");
			resultSet=statement.executeQuery();
			while(resultSet.next()){
				User user=new User();
				user.setId(resultSet.getInt(1));
				user.setName(resultSet.getString(2));
				user.setAge(resultSet.getInt(3));
				list.add(user);
			}
			return list;
		}catch(ClassNotFoundException exception){
			exception.printStackTrace();
		}catch(SQLException exception){
			exception.printStackTrace();
		}finally{
		
			try{
				if(resultSet != null){
					resultSet.close();
					resultSet=null;
				}
				if(statement != null){
					statement.close();
					statement=null;
				}
				if(connection != null){
					connection.close();
					connection=null;
				}
			}catch(SQLException exception){
			}
		}
		return list;
	}

	
	public User selectById(int id){
		PreparedStatement statement=null;
		ResultSet resultSet = null;
		User user=null;
		try{
			connection=this.createConnection();
			statement=connection.prepareStatement("select * from users where id=?");
			statement.setInt(1, id);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				user=new User();
				user.setId(resultSet.getInt(1));
				user.setName(resultSet.getString(2));
				user.setAge(resultSet.getInt(3));
			}
			return user;
		}catch(ClassNotFoundException exception){
			exception.printStackTrace();
		}catch(SQLException exception){
			exception.printStackTrace();
		}finally{
		
			try{
				if(resultSet != null){
					resultSet.close();
					resultSet=null;
				}
				if(statement != null){
					statement.close();
					statement=null;
				}
				if(connection != null){
					connection.close();
					connection=null;
				}
			}catch(SQLException exception){
			}
		}
		return user;
	}

	
	public void deleteUser(int id){
		PreparedStatement statement=null;
		
		try{
			connection=this.createConnection();
			statement=connection.prepareStatement("delete from users where id=?");
			statement.setInt(1,id);
			statement.executeUpdate();
		}catch(ClassNotFoundException exception){
			exception.printStackTrace();
		}catch(SQLException exception){
			exception.printStackTrace();
		}finally{
		
			try{
				if(statement != null){
					statement.close();
					statement=null;
				}
				if(connection != null){
					connection.close();
					connection=null;
				}
			}catch(SQLException exception){
			}
		}
	}


	public void updateUser(User user){
		PreparedStatement statement=null;
		
		try{
			connection=this.createConnection();
			statement=connection.prepareStatement("update users set name=?,age=? where id=?");
			statement.setString(1, user.getName());
			statement.setInt(2, user.getAge());
			statement.setInt(3, user.getId());
			statement.executeUpdate();
		}catch(ClassNotFoundException exception){
			exception.printStackTrace();
		}catch(SQLException exception){
			exception.printStackTrace();
		}finally{
		
			try{
				if(statement != null){
					statement.close();
					statement=null;
				}
				if(connection != null){
					connection.close();
					connection=null;
				}
			}catch(SQLException exception){
			}
		}
	}
	
	public void saveUser(User user){
		PreparedStatement statement=null;
		
		try{
			connection=this.createConnection();
			statement=connection.prepareStatement("insert into users(name,age) values(?,?)");
			statement.setString(1, user.getName());
			statement.setInt(2, user.getAge());
			statement.executeUpdate();
		}catch(ClassNotFoundException exception){
			exception.printStackTrace();
		}catch(SQLException exception){
			exception.printStackTrace();
		}finally{
		
			try{
				if(statement != null){
					statement.close();
					statement=null;
				}
				if(connection != null){
					connection.close();
					connection=null;
				}
			}catch(SQLException exception){
			}
		}
	}

}
