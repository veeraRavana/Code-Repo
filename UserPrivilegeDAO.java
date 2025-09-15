package AdminDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import AdminApplicationModel.SelectUserPopupPOJO;
import AdminApplicationModel.UserPrivilegeInsertionDataPOJO;
import AdminApplicationModel.UserPrivilegeTable;
import AdminApplicationModel.UserprivAllnodetable;
import AdminApplicationModel.UserprivilegePOJO;
import LoginPageModel.ModuleNode;
import commonGUI.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserPrivilegeDAO {
	
	 private Connection connection;
	 
	public UserPrivilegeDAO() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            if (connection == null) {
                System.err.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	/*public List<> loaduserPrivilegeMainTable(){
		
		String loadquery 				="select udc.\"userseqID\", udc.userid,udc.username,udc.user_privilege,"
											+ " 'Chairman' AS dept  from user_data_config udc  where udc.\"userseqID\" "
											+ "	in ( select distinct userseqid from user_node_privileges  )";
	}*/
		

/***********************************************************************
 * this is to load the data for the main table in the user privilege
 * main screen  
 ***********************************************************************/
	public List<UserPrivilegeTable> getAllUsersWithPrivilege() {
		
	    List<UserPrivilegeTable> userList = new ArrayList<>();

	    String query = "SELECT udc.\"userseqID\", udc.userid, udc.username, udc.user_privilege, "
	                 + "'Chairman' AS dept "
	                 + "FROM \"CompanyAndLogin\".user_data_config udc "
	                 + "WHERE udc.\"userseqID\" IN (SELECT DISTINCT userseqid FROM \"CompanyAndLogin\".user_node_privileges)";

	    try (PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs 			= stmt.executeQuery()) {

	        while (rs.next()) {
	            userList.add(new UserPrivilegeTable(
	                rs.getInt("userseqID"),
	                rs.getString("userid"),
	                rs.getString("username"),
	                rs.getString("user_privilege"),
	                rs.getString("dept")
	            ));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userList;
	}

		public List<UserprivAllnodetable> getUserNodesByUserseqID(int userSeqID) {
	    List<UserprivAllnodetable> Userlist = new ArrayList<>();
	    	String sql 				= "SELECT mn.module_id, mn.module_name, nn.node_id, nn.node_name FROM \"CompanyAndLogin\".user_node_privileges unp " +
						                 "JOIN \"CompanyAndLogin\".nodes_names nn ON nn.node_id = unp.node_id " +
						                 "JOIN \"CompanyAndLogin\".modules_name mn ON mn.module_id = nn.module_id " +
						                 "WHERE unp.userseqid = ? order by module_id asc";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	      
	    	stmt.setInt(1, userSeqID);
	        ResultSet rs			= stmt.executeQuery();
	        while (rs.next()) {
	        	Userlist.add(new UserprivAllnodetable(
	                rs.getInt("module_id"),
	                rs.getString("module_name"),
	                rs.getInt("node_id"),
	                rs.getString("node_name"),
	                false
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return Userlist;
	}
		
/***********************************************************************
 * this is to load the data for the left and right table
 * in the user privilege add user privilege screen
 ***********************************************************************/	
	public List<UserprivAllnodetable> getAllmodulesandNodes(String loadTable,int userseqid) {
    	
        List<UserprivAllnodetable> modulnodes 	= new ArrayList<>();
        String query 							= null;
       
       if(loadTable.equals("Left")) {
    	   if(userseqid == 0) {
    	        query 					= "select mn.module_id,mn.module_name,nn.node_id, nn.node_name, false as isselected"
    	        							+ " from \"CompanyAndLogin\".modules_name mn ,\"CompanyAndLogin\".nodes_names nn "
    	        							+ "where mn.module_id= nn.module_id order by mn.module_id asc";
    	   }else {
    		   query					="select mn.module_id,mn.module_name,nn.node_id,nn.node_name, false as isselected from"
    		   								+ " \"CompanyAndLogin\".modules_name mn ,\"CompanyAndLogin\".nodes_names nn "
    		   								+ " where mn.module_id= nn.module_id and nn.node_id not in(select node_id from"
    		   								+ " \"CompanyAndLogin\".user_node_privileges where userseqid="+userseqid+")"
    		   								+ "order by mn.module_id asc";
    	   }
           
       }else if (loadTable.equals("Right")) {
        	 query 						="SELECT mn.module_id,mn.module_name, nn.node_id, nn.node_name ,false as isselected "
        	 								+ "FROM \"CompanyAndLogin\".user_node_privileges up JOIN "
        	 								+ "\"CompanyAndLogin\".nodes_names nn ON up.node_id = nn.node_id JOIN "
        	 								+ "\"CompanyAndLogin\".modules_name mn ON nn.module_id = mn.module_id "
        	 								+ "WHERE up.userseqid = "+userseqid+" ";
        }
        
        if (connection == null) return modulnodes; // Prevent null connection error

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	modulnodes.add(new UserprivAllnodetable(
                    rs.getInt("module_id"),
                    rs.getString("module_name"),
                    rs.getInt("node_id"),
                    rs.getString("node_name"),
                    rs.getBoolean("isselected")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modulnodes;
    }
	
	public List<SelectUserPopupPOJO> getAllUsernames() {
		 
	        List<SelectUserPopupPOJO> usersDetails 	= new ArrayList<>();
	        String query 						= "select \"userseqID\",userid,username,user_privilege"
	        										+ " from \"CompanyAndLogin\".user_data_config order by userid asc";
	        
	        if (connection == null) return usersDetails; // Prevent null connection error

	        try (PreparedStatement stmt  = connection.prepareStatement(query);
	             ResultSet rs			 = stmt.executeQuery()) {
	        	
		            while (rs.next()) {
		            	usersDetails.add(new SelectUserPopupPOJO(
		            			rs.getInt("userseqID"),
		                        rs.getString("userid"),
		                        rs.getString("username"),
		                        rs.getString("user_privilege")
		       
		                    ));
		                
		            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return usersDetails;
	    }
	
/***************************************
 * Add Modify User Privilege Insertion
 ***************************************/	
	
	public boolean save_user_node_privileges(UserprivilegePOJO[] userpojo,int UserseqID ) {
	/* this method is to delete the existing data 
	 * and insert the new values(delete insert to
	 * prevent the error)*/
		try {
			connection.setAutoCommit(false);
				
				boolean isDeleted	=	deleteExisting_privilege(UserseqID);
				
				boolean isInserted	= Insert_into_user_node_privileges(userpojo);
				
				if(isInserted==false) {
					connection.rollback();
					return false;
				}
				
				connection.commit(); // success
				return true;
				
			} catch (SQLException e) {
		        try {
		            connection.rollback(); // failure
		        } catch (SQLException rollbackEx) {
		            rollbackEx.printStackTrace();
		        }
		        e.printStackTrace();
		        return false;
		
		    } finally {
		        try {
		            connection.setAutoCommit(true);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        
		        }
		    }
	}
	
	private Boolean deleteExisting_privilege(int UserseqID) {
		  PreparedStatement ps 	= null;
		try {
		
			
			String deletequery 	= "delete from \"CompanyAndLogin\".user_node_privileges where userseqid= ?";
			ps 					= connection.prepareStatement(deletequery);
	        ps.setInt(1, UserseqID);
	        ps.executeUpdate();
//	        ps.close();
	      
	        return true;
	        
		} catch (SQLException e) {
		    System.out.println("Could not delete user_node_privileges for " + UserseqID);
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (ps != null) ps.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
		
	}

	private boolean Insert_into_user_node_privileges(UserprivilegePOJO[] userpojo) {
		
	    String INSERT_QUERY 	= "INSERT INTO \"CompanyAndLogin\".user_node_privileges"
	    							+ " (userseqid, node_id) VALUES (?, ?)";
	    PreparedStatement preparedStatement 	= null;
	    try  {
	    	preparedStatement 					= connection.prepareStatement(INSERT_QUERY);
	        
	    	for (UserprivilegePOJO pojo : userpojo) {
	            preparedStatement.setInt(1, pojo.getUserseqID()	);
	            preparedStatement.setInt(2, pojo.getNodeId()	);
	            preparedStatement.addBatch(); // Add to batch
	        }

	    	preparedStatement.executeBatch(); // Execute batch
	        return true;

	    } catch (Exception e) {
	    	System.out.println("Could not insert into user_node_privileges");
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	  public ObservableList<ModuleNode> setModuleNodeTableData() {
		  
      ObservableList<ModuleNode> ModuleNamedata = null;
      List <ModuleNode> ModuleNameList 			= new ArrayList<>();
      String query 								= "select module_id, module_name from \"CompanyAndLogin\".modules_name"
      												+ " order by module_id asc" ;

      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
          ResultSet resultSet 				   = preparedStatement.executeQuery();
        
          while (resultSet.next()) {
        	  
        	  ModuleNameList.add( new ModuleNode(
            		  resultSet.getInt("module_id"),
            		  resultSet.getString("module_name")
            		  ));
          }
          
          ModuleNamedata	= FXCollections.observableArrayList(ModuleNameList); 
      } catch (Exception e) {
          e.printStackTrace();
      }

      return ModuleNamedata;
  }
	  
/***************************************
 * New Screen Name 
 ***************************************/
	 public Integer saveNewScreenNameIntoTable(int ModuleID,String screenName) {
		  int nodeID		= 0;
		  try {
			  
			  connection.setAutoCommit(false);
			  
			  nodeID		=  getSeqNodeID(connection);
				  if(nodeID	==0) {
					  connection.rollback();
					  return 0;
				  }
			  
			  nodeID		= addNewScreenNameIntoTable(nodeID,ModuleID,screenName);
				  if(nodeID	==0) {
					  connection.rollback();
					  return 0;
				  }
			  connection.commit();
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
		  return nodeID;
	  }
	  
	 private int getSeqNodeID(Connection con) throws SQLException {
			String sql 		= "SELECT nextval('\"CompanyAndLogin\".nodes_names_node_id_seq'::regclass)";

		    try (PreparedStatement ps 	= con.prepareStatement(sql);
		    		ResultSet rs 		= ps.executeQuery()) {
		        if (rs.next()) {
		            return rs.getInt(1);
		        }
		    }
		    return 0;//nothing goes correct then return 0
		}
	  
	private Integer addNewScreenNameIntoTable(int Nodeid, int ModuleID,String screenName) {

		String Insertquery 			= "INSERT INTO	\"CompanyAndLogin\".NODES_NAMES (NODE_ID, NODE_NAME, MODULE_ID,"
										+ " \"isNode_enabled\") VALUES	(?,?,?,true)";
		  PreparedStatement ps 		= null;

		    try {
		        ps 					= connection.prepareStatement(Insertquery);
		        ps.setInt(1, Nodeid);
		        ps.setString(2, screenName);
		        ps.setInt(3, ModuleID);
			        
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
		    return ModuleID;
	}
	
/***************************************
 * common methods that is used repeated
 ***************************************/
	
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

	public boolean UserPrivilegeLog(UserPrivilegeInsertionDataPOJO UserIntData, Integer userSeqid) {
		String insertLogSQL				= "INSERT INTO \"CompanyAndLogin\".USER_PRIVILEGE_LOG"
												+ " (USER_NODE_ID, USERNAME, USER_IP, TRANSACTION_TYPE)"
												+ " VALUES	(?, ?, ?, ?)";
		try (PreparedStatement stmt 	= connection.prepareStatement(insertLogSQL)) {
			
			stmt.setString(1,	UserIntData.getUserNodeIDs()	);
			stmt.setString(2, 	UserIntData.getUserName()	);
			stmt.setString(3, 	UserIntData.getUserIPAddress()	);
			stmt.setString(4, 	UserIntData.getTransactionName()	);

			int rowsInserted 			= stmt.executeUpdate();
			return rowsInserted > 0;
			
		} catch (Exception e) {
		e.printStackTrace();
		return false;
		}
	}


	
}
