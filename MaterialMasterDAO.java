package AdminDAO;

import java.sql.Connection;
import java.sql.SQLException;

import commonGUI.DatabaseConnection;

public class MaterialMasterDAO {
	
	 private Connection connection;
		
	 public MaterialMasterDAO() {
		    try {
	            connection 		= DatabaseConnection.getInstance().getConnection();
	            if (connection 	== null) {
	                System.err.println("Database connection failed.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }	

}
