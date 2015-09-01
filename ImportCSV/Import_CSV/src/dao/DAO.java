/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import command.ServiceModule;

/**
 * @author ����������� ����, 
 * 27.08.2015
 * ����� ��� ������ � �� Import_CSV
 *
 */
public class DAO {
	private static DAO sessionDAO;
	private Connection connection;
	
	
	private DAO(){
		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Error loading driver: " +cnfe); 
			}
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Import_csv?characterEncoding=utf8","root","");
			
		} catch (SQLException e) {
			System.out.println("Can not get connection to the database");
			//e.printStackTrace();
		}		
		
	}
	
	 public static DAO startDAO(){
        if(sessionDAO == null)
            sessionDAO = new DAO();

        if (sessionDAO==null) //ooops...
            System.out.println("Can not open database");

        return sessionDAO;
    }

	 /**
	  * ����� ��� �������� �� ����������� ������������ � ����� ������� � ��
	  */	
	private boolean isUserExist(String login) throws SQLException{  //��� �������� �����
		boolean result=false;

		String sqlQuery="select * from users  where login = ?";

		PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
		pStatement.setString(1, login);
		ResultSet rs = pStatement.executeQuery();
		result = rs.first();
	
		return result;
	}
	
	 /**
	  * ����� ��� ������� ������ � ��
	  */			
	 private void createUser(User currentUser) throws SQLException{
		 String sqlUpdate="insert into users (name,surname,login,email,phone) values (?,?,?,?,?)";

		 PreparedStatement pStatement = connection.prepareStatement(sqlUpdate);
		 pStatement.setString(1, currentUser.getName());
		 pStatement.setString(2, currentUser.getSurname());
		 pStatement.setString(3, currentUser.getLogin());
		 pStatement.setString(4, currentUser.getEmail());
		 pStatement.setString(5, currentUser.getPhone());
		 
		 int result=pStatement.executeUpdate();
	 }

	 /**
	  * ����� ��� ���������� ������ ��� ������������ ������
	  */		 
	 private void updateUser(User currentUser) throws SQLException{
		 String sqlUpdate="update users set name=?, surname=?, email=?, phone=? where login=?";

		 PreparedStatement pStatement = connection.prepareStatement(sqlUpdate);
		 pStatement.setString(1, currentUser.getName());
		 pStatement.setString(2, currentUser.getSurname());
		 pStatement.setString(3, currentUser.getEmail());
		 pStatement.setString(4, currentUser.getPhone());
		 pStatement.setString(5, currentUser.getLogin());
		 
		 int result=pStatement.executeUpdate();
	 }	 

	 /**
	  * ����� ��� ������� ������
	  */
	 public void importUsers(ArrayList<User> listUsers, int update){
		 if (update==1) {			//��������� �� ��� �������. ���� ����� ����� ��-���� ����� - �������� ��������� ��������� ��� ��������� ���������� (���� login - NOT NULL)
			 
			 for (User currentUser:listUsers){
				 try{
					 createUser(currentUser); 
				 }catch(SQLException sqlException){
					 System.out.println("Can not insert into the database user: "+currentUser.toString());
					 //sqlException.printStackTrace();	
					 }
			 }
		 }else if (update==2){		//����� ��������� ������ ������ �� ������� � ����. ����� � ����� ������� - ������� ��� ����, ����� ������ 
			 for (User currentUser:listUsers){
				 try{
					 if (isUserExist(currentUser.getLogin())){	//���� ����� ��� - �������
						 updateUser(currentUser);
					 }else {
						 createUser(currentUser);				//���� ��� - �������� ������
					 }
				 }catch(SQLException sqlException){
					 System.out.println("Can not insert a user into the database: "+currentUser.toString());
					 //sqlException.printStackTrace();
				 }
			 }
			 
		 }
	 }

	 /**
	  * ����� ��� ��������� ������ �� ��
	  */
	 public List showUsers(String sortVariant, int pageNumber){
		 ArrayList<User> listUsers=new ArrayList<User>();
		 int rowsOnPage=ServiceModule.ROWS_ON_PAGE;
		 
		 try {
				Statement statement = connection.createStatement();
				String strQuery="select * from (select * from users order by name Limit "+(pageNumber*rowsOnPage)+", "+rowsOnPage
					+") AS C order by "+sortVariant;
					//����������� ��� ������ �� name. ����� ������� ������ � ��������. � ����� ����������� ��������� �� ������� ������������.
					//����� ��� ���������� ������� �� �������� �� ������� ������ ������ �� �������� �� �������� 
				
				ResultSet rs = statement.executeQuery(strQuery);
				while(rs.next()){	
					User currentUser=new User(rs.getString("name"), rs.getString("surname"), rs.getString("login")
								, rs.getString("email"), rs.getString("phone"));
					listUsers.add(currentUser);
				}
			} catch (SQLException e) {
				System.out.println("Can not get user from the database");
				//e.printStackTrace();
			}
		 
		 return listUsers;
	 }
 
	 /**
	  * ����� ��� ��������� ���������� ������� � ������� Users
	  */	 
	 public int getNumberOfUsers(){
		 int result=0;
		 try {
				Statement statement = connection.createStatement();
				String strQuery="select count(*) from users"; 
				ResultSet rs = statement.executeQuery(strQuery);
				if(rs.next()){
					result=rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		 
		 return result;
	 }	 

	 /**
	  * ����� ��� ������� ������� Users. ������������� ��� ������
	  */	 	 
	public void deleteUsers(){  //��� �������� �����
		 try {
				Statement statement = connection.createStatement();
				String strQuery="delete from users"; 
				statement.executeUpdate(strQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}		
		
	}
			 
	 
}
