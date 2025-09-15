package AdminDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import AdminApplicationModel.PaymentTypeTable;
import AdminApplicationModel.SalesTypeTable;
import AdminApplicationModel.TransactionConfigurationTypePOJO;
import commonGUI.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionConfigurationDAO {
	 private Connection connection;
	
	 public TransactionConfigurationDAO() {
			try {
			  connection = DatabaseConnection.getInstance().getConnection();
	            if (connection == null) {
	                System.err.println("Database connection failed.");
	            }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
/*************************************************************************** 
 *   Transaction Configuration Main Screen
 *   
 * Transaction Configuration Type Table Data Fetching Method
 ***************************************************************************/
		public ObservableList<SalesTypeTable> GetSalesTypeTableData() {
			
			ObservableList<SalesTypeTable> SalesTypeTableData = null;
			List<SalesTypeTable> SalesTypeTableList = new ArrayList<>();
	  		
			String query 				= "SELECT SALESTYPE_ID,SALESTYPE_NAME, SALESTYPE_STATUS,"
											+ "SALESTYPE_DESC FROM \"Admin_Schema\".sales_type " ;
			
			if (connection == null) return SalesTypeTableData ; // Prevent null connection error
			
			try (PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery()) {
			
				while (rs.next()) {
					SalesTypeTableList.add(new SalesTypeTable(
						rs.getInt("SALESTYPE_ID"),
						rs.getString("SALESTYPE_NAME"),
						rs.getString("SALESTYPE_DESC"),
						rs.getString("SALESTYPE_STATUS").equals("t") ?"Active" : "Inactive"				
					));
				}
				
				if (SalesTypeTableList.isEmpty()) {
					System.out.println("No data found in Sales Type table.");
					return FXCollections.observableArrayList(); // Return empty list if no data
				}
				SalesTypeTableData 		= FXCollections.observableArrayList(SalesTypeTableList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return SalesTypeTableData ;
		}
		
		public ObservableList<PaymentTypeTable> GetPaymentTypeTableData() {
			
			ObservableList<PaymentTypeTable> PaymentTypeTableData = null;
			List<PaymentTypeTable> PaymentTypeTableList = new ArrayList<>();
	  		
			String query 				= "SELECT PAYMENTTYPE_ID,PAYMENTTYPE_NAME,PAYMENTTYPE_STATUS,"
											+ "	PAYMENTTYPE_DESC FROM \"Admin_Schema\".payment_type";
			
			if (connection == null) return PaymentTypeTableData ; // Prevent null connection error
			
			try (PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery()) {
			
				while (rs.next()) {
					PaymentTypeTableList.add(new PaymentTypeTable(
						rs.getInt("PAYMENTTYPE_ID"),
						rs.getString("PAYMENTTYPE_NAME"),
						rs.getString("PAYMENTTYPE_DESC"),
						rs.getString("PAYMENTTYPE_STATUS").equals("t") ? "Active" : "Inactive"						
					));
				}
				
				if (PaymentTypeTableList.isEmpty()) {
					System.out.println("No data found in Payment Type table.");
					return FXCollections.observableArrayList(); // Return empty list if no data
				}
				PaymentTypeTableData 		= FXCollections.observableArrayList(PaymentTypeTableList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return PaymentTypeTableData ;
		}
	 
	 
/***************************************************************************
 * Add or Modify Transaction Configuration Type Screen
 * 
 * this method is used to save or modify the transaction configuration type
 ***************************************************************************/ 
	  
	public Integer saveOrModifyTransactionConfigurationType(Integer TransactionTypeID,
								TransactionConfigurationTypePOJO transConfigTypePOJO,int mode,String ScreenName) {
		try {
			connection.setAutoCommit(false);
			
			 String INSERT_QUERY		= null;
			 String SequenceID_Query	= null; 

			if(ScreenName.equals("Sales Type")) {
				SequenceID_Query		= "SELECT nextval('\"Admin_Schema\".sales_type_salestype_id_seq'::regclass)";
				
				INSERT_QUERY  			= "insert into \"Admin_Schema\".SALES_TYPE ("
											+ " SALESTYPE_ID, SALESTYPE_NAME, SALESTYPE_DESC,"
											+ " SALESTYPE_STATUS ) values(?,?,?,?)";
	
			}else if(ScreenName.equals("Payment Type")) {
				SequenceID_Query		= "SELECT nextval('\"Admin_Schema\".payment_type_paymenttype_id_seq'::regclass)";
				
				INSERT_QUERY  			= "insert into \"Admin_Schema\".payment_type ( "
											+ "PAYMENTTYPE_ID, PAYMENTTYPE_NAME, PAYMENTTYPE_DESC,"
											+ " PAYMENTTYPE_STATUS ) values(?,?,?,?)";
					
			}	
			
			if(TransactionTypeID	==0) {
				TransactionTypeID 	= getSeqTransactionConfigID(connection,SequenceID_Query);
			}
			// If TransactionID is still 0, it means sequence generation failed, roll back and return 0
					if(TransactionTypeID	==0) {	
						connection.rollback();
						return 0;	
					 }
			 		
			TransactionTypeID 		=	Insert_intoTrasactionType_Table(TransactionTypeID,transConfigTypePOJO, mode,INSERT_QUERY);
			
				if(TransactionTypeID	==0) {
					connection.rollback();
					return 0;
				}
			
			} catch (SQLException e) {
			    try {
			        connection.rollback(); // failure
			    } catch (SQLException rollbackEx) {
			        rollbackEx.printStackTrace();
			    }
			    e.printStackTrace();
			    return 0;
			
			} finally {
			    try {
			        connection.setAutoCommit(true);
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			}
		return TransactionTypeID;
		}
	
/*in this method the sequence id is generated for
 *  the transaction types (Sales Type, Payment Type)
 *   the query for the types are taken dynamically
 *    by the screen selection*/
	private int getSeqTransactionConfigID(Connection con,String SequenceID_Query) throws SQLException {

	    try (PreparedStatement ps 	= con.prepareStatement(SequenceID_Query);
	    		ResultSet rs 		= ps.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    }
	    return 0;//nothing goes correct then return 0
	}
	
	
	private Integer Insert_intoTrasactionType_Table(Integer TransactionTypeID,
							TransactionConfigurationTypePOJO transConfigTypePOJO,
							int mode, String INSERT_QUERY) {
	  
				PreparedStatement ps 	= null;
				
			try {
				ps 					= connection.prepareStatement(INSERT_QUERY);
				
				ps.setInt(1, TransactionTypeID);
				ps.setString(2, transConfigTypePOJO.getTransactionTypeName());
				ps.setString(3, transConfigTypePOJO.getTransactionDescription());
				ps.setBoolean(4, transConfigTypePOJO.getTransactionStatus());
			
				
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			} finally {
				try {
					if (ps != null) ps.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		return TransactionTypeID;
	}

 /* Modify Transaction Type Tables
 * this method is to modify the selected by delete 
 * and insert into  the Transaction type according
 *  to the selected screen */

	public Integer ModifyTransactionTypeTables(Integer TransactionTypeID,
			TransactionConfigurationTypePOJO transConfigTypePOJO,int mode,String ScreenName) {

		try {
			connection.setAutoCommit(false);
			
			deleteTransactionConfiguration(TransactionTypeID, ScreenName);
			TransactionTypeID			=	saveOrModifyTransactionConfigurationType(
											TransactionTypeID, transConfigTypePOJO, mode,ScreenName);
			
			return TransactionTypeID;
		} catch (SQLException e) {
			try {
			    connection.rollback(); // failure
			} catch (SQLException rollbackEx) {
			    rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			return 0;
			
		} finally {
			try {
			    connection.setAutoCommit(true);
			} catch (SQLException e) {
			    e.printStackTrace();
			
			}
		}
	}
	
	
 /***************************************************************************
 * Delete Transaction Configuration Type Screen
 * this method is used to delete the transaction configuration type
 ***************************************************************************/
	public Integer deleteTransactionConfiguration(int TransactionID, String ScreenName) {
	    PreparedStatement ps = null;
	    try {
	        connection.setAutoCommit(false);

	      if(ScreenName.equals("Sales Type")) {
		        String deleteSalesTypeSQL 	= "delete from \"Admin_Schema\".SALES_TYPE where salestype_id= ? ";
		        ps 							= connection.prepareStatement(deleteSalesTypeSQL);
	    
	      } else if(ScreenName.equals("Payment Type")) {
	        	String deletePaymentTypeSQL = "delete from \"Admin_Schema\".payment_type where paymenttype_id=? ";
	        	ps 							= connection.prepareStatement(deletePaymentTypeSQL);
	      }
	      	ps.setInt(1, TransactionID);
	        ps.executeUpdate();
	        ps.close();
	        
	        connection.commit();
	        return TransactionID;

	    } catch (SQLException e) {
	        try {
	            connection.rollback(); // rollback on error
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	        return 0;

	    } finally {
	        try {
	            if (ps != null) ps.close();
	            connection.setAutoCommit(true); // restore default
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	

 /***************************************************************************
  * Transaction Configuration Log
  ***************************************************************************/
	public boolean TransactionConfiguration_Log(Integer TransactionTypeID,
							TransactionConfigurationTypePOJO transConfigTypePOJO,
							int mode,String ScreenName) {
			
			String insertLogSQL			= "insert into \"Admin_Schema\".TRANSACTION_CONFIG_LOG"
											+ " (username, user_ip, TransTypeID ,TRANS_TYPE_MODE )"
											+ "values(?,?,?,?)";
			
			try (PreparedStatement stmt 		= connection.prepareStatement(insertLogSQL)) {
			
			stmt.setString(1,	transConfigTypePOJO.getUserName());
			stmt.setString(2, 	transConfigTypePOJO.getUserIPAddress());
			stmt.setInt(3, 		TransactionTypeID);
			stmt.setString(4,	transConfigTypePOJO.getTransactionMode());

			
			int rowsInserted 				= stmt.executeUpdate();
			return rowsInserted > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
