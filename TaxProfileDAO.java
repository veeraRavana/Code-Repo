package AdminDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import AdminApplicationModel.TaxProfile;
import AdminApplicationModel.TaxProfilePojo;
import commonGUI.DatabaseConnection;

public class TaxProfileDAO {
    private Connection connection;

    public TaxProfileDAO() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            if (connection == null) {
                System.err.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Fetches all tax profiles from the database */
    public List<TaxProfile> getAllTaxProfiles() {
    	
        List<TaxProfile> taxProfiles 	= new ArrayList<>();
        String query 					= "SELECT tax_profile_id, tax_updated_date, version, "
        									+ "tax_profile_name FROM \"Admin_Schema\".tax_profile order by tax_profile_id asc";

        if (connection == null) return taxProfiles; // Prevent null connection error

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                taxProfiles.add(new TaxProfile(
                    rs.getInt("tax_profile_id"),
                    rs.getString("tax_updated_date"),
                    rs.getInt("version"),
                    rs.getString("tax_profile_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxProfiles;
    }

/*this is to save the tax profile into the 
 *tax_profile table */
    
    public boolean Insert_into_tax_profile(TaxProfilePojo taxprofilepojo) {
    	
//    	String INSERT_QUERY 	= "INSERT INTO tax_profile (tax_profile_id, tax_updated_date,"
//    								+ " tax_profile_name, \"version\") VALUES  "
//    								+ " (?, CURRENT_TIMESTAMP, ?, ?)";
    	String INSERT_QUERY 	= "INSERT INTO \"Admin_Schema\".tax_profile (tax_updated_date, tax_profile_name,"
    								+ " \"version\") VALUES (CURRENT_TIMESTAMP, ?, ?)"    ;	
    	
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

//	        	preparedStatement.setInt(1, taxprofilepojo.TaxProfileID);
	        	preparedStatement.setString(1, taxprofilepojo.TaxprofileName);
	        	preparedStatement.setInt(2, taxprofilepojo.TaxVersion);

               int rowsAffected = preparedStatement.executeUpdate();
               if (rowsAffected > 0) {
                   System.out.println("Tax Profile inserted successfully!");
               } else {
                   System.out.println("Insertion failed.");
               }
           } catch (SQLException e) {
				System.out.println("could not insert into tax_profile");
				e.printStackTrace();
				rollbackAndCloseConnection(connection);
				return false;
			
           }
		return true;
    }
    
    
    
    
    
    /** Deletes a tax profile from the database */
    public boolean deleteTaxProfile(int taxProfileId) {
        String query = "DELETE FROM \"Admin_Schema\".tax_profile WHERE tax_profile_id = ?";

        if (connection == null) {
            System.err.println("Database connection is null. Cannot delete.");
            return false;
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, taxProfileId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No tax profile found with ID: " + taxProfileId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
	public void rollbackAndCloseConnection(Connection  con){
		try{
			  if(con != null && !con.isClosed()){
				  con.rollback();
				  con.close();
			  }
		}catch(Exception  e){
			Exception newEx = new Exception("Error at:"+new java.util.Date()+"",e);
		    newEx.printStackTrace();
		}
	}
    
}
