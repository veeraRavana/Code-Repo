package AdminDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import AdminApplicationModel.ADDAddressPOJO;
import AdminApplicationModel.BankdetailsPOJO;
import AdminApplicationModel.Company;
import AdminApplicationModel.CompanyRegisterDataWrapper;
import AdminApplicationModel.CompanyRegisterPOJO;
import common.BankNames;
import common.Countries;
import common.District;
import common.State;
import commonGUI.DatabaseConnection;

public class CompanyRegisterDAO {
	
	 private Connection connection;
	
	 public CompanyRegisterDAO() {
		    try {
	            connection = DatabaseConnection.getInstance().getConnection();
	            if (connection == null) {
	                System.err.println("Database connection failed.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }	
	 
	 
/*******************************
 * Company Register Main Screen -STARTS-
 *******************************/	 
		public List<Company> GetCompanyRegisterTableData() {
        
			List<Company> CompanyRegData 	= new ArrayList<>();
	        String query 					= "SELECT crd.company_id, crd.company_name, crd.company_phone_no,"
	        									+ " crd.gst_no, CONCAT_WS(' , ', ca.address1, ca.address2) AS full_address,"
	        									+ " cu.country_name FROM"
	        									+ " \"Admin_Schema\".company_register_details crd JOIN "
	        									+ "\"Admin_Schema\".company_address ca ON ca.company_id = crd.company_id"
	        									+ " join \"ComGeneral\".countries cu	on cu.country_id =ca.countryid "
	        									+ "ORDER BY crd.company_id ASC";
			
			if (connection == null) return CompanyRegData; // Prevent null connection error
			
			try (PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery()) {
			
				while (rs.next()) {
					CompanyRegData.add(new Company(
						rs.getInt("company_id"),
						rs.getString("company_name"),
						rs.getString("company_phone_no"),
						rs.getString("gst_no"),
						rs.getString("full_address"),
						rs.getString("country_name")
						
					));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return CompanyRegData;
		} 
		
/*this method is to get the data from the Database 
 * and set data in the modification and view  screen  
 * */	
		
		public  CompanyRegisterDataWrapper getDataforModificationViewCompReg(int selectedcompanyID) {
			
			List<Company> CompanyRegData 			= getdatatoSetCompanyRegModandView( selectedcompanyID);
			List<ADDAddressPOJO> CompanyRegAddress 	= getdataofcompanyregisterAddAddress(selectedcompanyID);
			List<BankdetailsPOJO> CompanyRegBankdet =  getdataofcompanyregisterBankdetails(selectedcompanyID);
	
			return new CompanyRegisterDataWrapper(CompanyRegData, CompanyRegAddress, CompanyRegBankdet);
		}
			
		public List<Company> getdatatoSetCompanyRegModandView(int selectedcompanyID){ 
			
			List<Company> CompanyRegData 		= new ArrayList<>();
			
			String companyRegisterDetailsQuery	= "select company_id,company_name,company_phone_no,company_emailaddress,"
													+ "gst_no,shipmenttype from \"Admin_Schema\".COMPANY_REGISTER_DETAILS "
													+ "where company_id="+selectedcompanyID+"";
			
			if (connection == null) return CompanyRegData; // Prevent null connection error
				
				try (PreparedStatement stmt 	= connection.prepareStatement(companyRegisterDetailsQuery);
				ResultSet rs 					= stmt.executeQuery()) {
				
					while (rs.next()) {
						CompanyRegData.add(new Company(
							rs.getString("company_name"),
							rs.getInt("company_id"),
							rs.getString("company_phone_no"),
							rs.getString("company_emailaddress"),
							rs.getString("gst_no"),
							rs.getString("shipmenttype")
						));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			
			return CompanyRegData;			
		}
		
		public List<ADDAddressPOJO> getdataofcompanyregisterAddAddress(int selectedcompanyID) {
			List<ADDAddressPOJO> CompanyRegAddress 		= new ArrayList<>();
			
			String AddressDetailsQuery				= "SELECT	COMPANY_ID,	COMPANYADDRESSID, ADDRESS1,	ADDRESS2, STATEID, s.state_name,"
													+ " DISTRICTID, d.district_name,	COUNTRYID, c.country_name, POSTALCODE, "
													+ "ISHEADOFFICE FROM \"Admin_Schema\".COMPANY_ADDRESS CA join \"ComGeneral\".states s "
													+ "on s.state_id = CA.stateid join \"ComGeneral\".districts d on"
													+ " d.district_id = CA.districtid	join \"ComGeneral\".countries c "
													+ "on c.country_id = CA.countryid where CA.company_id="+selectedcompanyID+" ";
			if (connection == null) return CompanyRegAddress; // Prevent null connection error
			
			try (PreparedStatement stmt 	= connection.prepareStatement(AddressDetailsQuery);
			ResultSet rs 					= stmt.executeQuery()) {
			
				while (rs.next()) {
					ADDAddressPOJO addAddressPOJO= new ADDAddressPOJO();
					addAddressPOJO.CompanyID		= rs.getInt("COMPANY_ID");
					addAddressPOJO.companyAddressID	= rs.getInt("companyAddressID");
					addAddressPOJO.Address1			= rs.getString("ADDRESS1");
					addAddressPOJO.Address2			= rs.getString("ADDRESS2");
					
					addAddressPOJO.stateID			= rs.getInt("STATEID");
					addAddressPOJO.State			= rs.getString("state_name");
					
					addAddressPOJO.DistrictID		= rs.getInt("DISTRICTID");
					addAddressPOJO.District			= rs.getString("district_name");
					
					addAddressPOJO.CountryID		= rs.getInt("COUNTRYID");
					addAddressPOJO.Countryorigin	= rs.getString("country_name");
			
					addAddressPOJO.Postalcode		= rs.getInt("POSTALCODE");
					addAddressPOJO.isHeadOffice		= rs.getBoolean("ISHEADOFFICE");
					
					CompanyRegAddress.add(addAddressPOJO);

					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			return CompanyRegAddress;
		}
		
		public List<BankdetailsPOJO> getdataofcompanyregisterBankdetails(int selectedcompanyID)	{
			List<BankdetailsPOJO> CompanyRegBankdet 	= new ArrayList<>();
			
			String bankdetailQuery						= "SELECT BD.NOBANKSERIALNO, BD.COMPANY_ID, BD.ACCOUNTHOLDER_NAME,"
															+ " BD.BANK_ID, BN.BANK_NAME, BD.ACCOUNTNOORIBAN, BD.IFSCORSWIFTCODE"
															+ " FROM \"Admin_Schema\".BANKACCOUNT_DETAILS BD	JOIN"
															+ " \"ComGeneral\".BANK_NAMES BN ON BN.BANK_ID = BD.BANK_ID "
															+ " where BD.company_id="+selectedcompanyID+"";
		
			if (connection == null) return CompanyRegBankdet; // Prevent null connection error
			
			try (PreparedStatement stmt 		= connection.prepareStatement(bankdetailQuery);
			ResultSet rs 						= stmt.executeQuery()) {
			
				while (rs.next()) {
					
					BankdetailsPOJO bankdetailsPOJO 	= new BankdetailsPOJO();
					bankdetailsPOJO.serialno			= rs.getInt("NOBANKSERIALNO");
					bankdetailsPOJO.accountholderName	= rs.getString("ACCOUNTHOLDER_NAME");
					bankdetailsPOJO.bank_ID				= rs.getInt("BANK_ID");
					bankdetailsPOJO.bankName			= rs.getString("BANK_NAME");
					bankdetailsPOJO.accountNo			= rs.getString("ACCOUNTNOORIBAN");
					bankdetailsPOJO.ifscCode			= rs.getString("IFSCORSWIFTCODE");
					
					CompanyRegBankdet.add(bankdetailsPOJO);
				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return CompanyRegBankdet;
		}
		
		
/*******************************
 * Company Register Main Screen -ENDS-
 ********************************/	 
	 
	 public List<Countries> fetchAllCountries() {
		  List<Countries> Countries = new ArrayList<>();
	        String sql = "select country_id,country_name from \"ComGeneral\".countries";

	        try (
	             PreparedStatement ps = connection.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	            	Countries.add(new Countries(rs.getInt("country_id"),
	            			rs.getString("country_name")));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return Countries;
		}
	 
	 public List<State> fetchAllStates() {
	        List<State> states = new ArrayList<>();
	        String sql = "SELECT state_id, state_name FROM \"ComGeneral\".states";

	        try (
	             PreparedStatement ps = connection.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                states.add(new State(rs.getInt("state_id"), rs.getString("state_name")));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return states;
	    }

	 public List<District> fetchDistrictsByStateId(int stateId) {
	        List<District> districts = new ArrayList<>();
	        String sql = "SELECT district_id, district_name, state_id FROM \"ComGeneral\".districts WHERE state_id = ?";

	        try (PreparedStatement ps = connection.prepareStatement(sql)) {

	            ps.setInt(1, stateId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    districts.add(new District(
	                            rs.getInt("district_id"),
	                            rs.getString("district_name"),
	                            rs.getInt("state_id")));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return districts;
	    }

	 public List<BankNames> fetchBanknames(Boolean isInternationalbank) {
			
			List <BankNames> bankNames	= new ArrayList<>();
			
		     String sql					= isInternationalbank == true ?
		    		 "SELECT bank_id,bank_name,is_international FROM \"ComGeneral\".bank_names WHERE is_international = TRUE"
		    		 : "SELECT bank_id,bank_name,is_international FROM \"ComGeneral\".bank_names WHERE is_international = FALSE";

		        try (PreparedStatement ps = connection.prepareStatement(sql)) {

		             try (ResultSet rs = ps.executeQuery()) {
		                while (rs.next()) {
		                	bankNames.add( new BankNames(
		                            rs.getInt("bank_id"),
		                            rs.getString("bank_name"),
		                            rs.getBoolean("is_international") 
		                            ) );
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

			return bankNames;
		}
		
		
/************************************************************/
		public Integer ModifyCompanyRegister_Tables(int Company_ID,CompanyRegisterPOJO comppoj,
								ADDAddressPOJO addpojo,BankdetailsPOJO[] Bankdet ) {

			try {
				connection.setAutoCommit(false);
			
				Company_ID	=	deleteCompanyRegisterDetails(Company_ID);
				if(Company_ID	==0) {
					connection.rollback();
					return 0;
		    	}
				Company_ID	=	saveInCompanyRegister_Tables(Company_ID,comppoj,addpojo,Bankdet);
				
			   return Company_ID;
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
		
		
		public  int deleteCompanyRegisterDetails(int companyId) {
		    PreparedStatement ps = null;
		    try {
		        connection.setAutoCommit(false);

		        // Step 1: Delete from bankaccount_details
		        String deleteBankSQL = "DELETE FROM \"Admin_Schema\".bankaccount_details WHERE company_id = ?";
		        ps = connection.prepareStatement(deleteBankSQL);
		        ps.setInt(1, companyId);
		        ps.executeUpdate();
		        ps.close();

		        // Step 2: Delete from company_address
		        String deleteAddressSQL = "DELETE FROM \"Admin_Schema\".company_address WHERE company_id = ?";
		        ps = connection.prepareStatement(deleteAddressSQL);
		        ps.setInt(1, companyId);
		        ps.executeUpdate();
		        ps.close();

		        // Step 3: Delete from company_register_details
		        String deleteCompanySQL = "DELETE FROM \"Admin_Schema\".company_register_details WHERE company_id = ?";
		        ps = connection.prepareStatement(deleteCompanySQL);
		        ps.setInt(1, companyId);
		        ps.executeUpdate();
		        ps.close();

		        connection.commit();
		        return companyId;

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

		public boolean logCompanyRegisterTransaction(int companyId,CompanyRegisterPOJO comppoj,
											ADDAddressPOJO addpojo,BankdetailsPOJO[] Bankdet) {
			
			String insertLogSQL			= "INSERT INTO \"Admin_Schema\".company_register_audit_log "
					                        + "(username, user_ip, transaction_type, company_id, payload) "
					                        + "VALUES (?, ?, ?, ?, ?::jsonb)";
		    
		    try (PreparedStatement stmt 		= connection.prepareStatement(insertLogSQL)) {
	// Build payload
		        Map<String, Object> payloadMap 	= new HashMap<>();
		        
		        payloadMap.put("company", comppoj);
		        payloadMap.put("addresses", addpojo);
		        payloadMap.put("bankDetails", Bankdet);
		        
		        ObjectMapper objectMapper 		= new ObjectMapper();
		        String jsonPayload 				= objectMapper.writeValueAsString(payloadMap);

		        	 stmt.setString(1,	comppoj.UserName);
		     		 stmt.setString(2, 	comppoj.userIPAddress);
		     		 stmt.setString(3, 	comppoj.TransactionName);
		     		 stmt.setInt(4,		comppoj.companyId);
		     		 stmt.setString(5, 	jsonPayload);
       
		        int rowsInserted 				= stmt.executeUpdate();
		        return rowsInserted > 0;
		
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}
		
		public Integer saveInCompanyRegister_Tables(int Company_ID,CompanyRegisterPOJO comppoj,
													ADDAddressPOJO addpojo,BankdetailsPOJO[] Bankdet ) {
		
			try {
				connection.setAutoCommit(false);
				
				if(Company_ID	==0) {
					Company_ID	=	getSeqCompanyID(connection);
				}
				if(Company_ID	==0) {
					connection.rollback();
					return 0;
		    	}
				
				Company_ID 		= Insert_into_CompanyRegisterDetails(Company_ID, comppoj, connection);
				if(Company_ID	==0) {
					connection.rollback();
					return 0;
		    	}
				
				Company_ID		= Insert_into_CompanyRegisterAddress(Company_ID, addpojo, connection);
				if(Company_ID	==0) {
					connection.rollback();
					return 0;
		    	}
				
				Company_ID 		= Insert_into_CompanyRegisterBankDetails(Company_ID, Bankdet, connection);
				if(Company_ID	== 0) {
					connection.rollback();
					return 0;
		    	}
				   connection.commit(); // success
		        return Company_ID;

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

		private Integer getSeqCompanyID(Connection con) throws SQLException {
			String sql 		= "SELECT nextval('\"Admin_Schema\".company_details_company_id_seq'::regclass)";
			
		    try (PreparedStatement ps = con.prepareStatement(sql);
		    		ResultSet rs = ps.executeQuery()) {
		        if (rs.next()) {
		            return rs.getInt(1);
		        }
		    }
		    return 0;//nothing goes correct then return 0
		}

		private Integer Insert_into_CompanyRegisterDetails(int companyId, CompanyRegisterPOJO compPojo, Connection connection) {
		    String INSERT_QUERY 	= "INSERT INTO \"Admin_Schema\".company_register_details (company_id, company_name, GST_NO,"
		    							+ " company_EmailAddress, company_phone_no,shipmenttype) VALUES (?, ?, ?, ?, ?, ?)";
		    PreparedStatement ps 	= null;

		    try {
		        ps 					= connection.prepareStatement(INSERT_QUERY);
		        ps.setInt(1, companyId);
		        ps.setString(2, compPojo.companyName);
		        ps.setString(3, compPojo.gst);
		        ps.setString(4, compPojo.Emailaddress);
		        ps.setString(5, compPojo.phoneNumber);
		        ps.setString(6, compPojo.ShipmentType);
		        
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
		    return companyId;
		}

		private Integer Insert_into_CompanyRegisterAddress(int company_ID,ADDAddressPOJO addressPojo, Connection connection) {
		    String INSERT_QUERY 	="INSERT INTO \"Admin_Schema\".company_address (company_id, address1, address2, "
		    							+ "StateID, DistrictID, CountryID, PostalCode, IsHeadOffice) VALUES "
		    							+ "(?, ?, ?, ?, ?, ?, ?, ?)";
		    // CompanyAddressID is created automatically  

		    PreparedStatement ps 	= null;

		    try {
		    	ps 					= connection.prepareStatement(INSERT_QUERY);
		    	// Insert into company_address
				ps.setInt(1, company_ID);
				ps.setString(2, addressPojo.Address1);			// address1
				ps.setString(3, addressPojo.Address2);			// address2
				ps.setInt(4, addressPojo.stateID);				// state
				ps.setInt(5, addressPojo.DistrictID);			// district
				ps.setInt(6, addressPojo.CountryID);			// country
				ps.setInt(7, addressPojo.Postalcode);			// pincode or postal code
				ps.setBoolean(8, addressPojo.isHeadOffice);  	// is_headoffice
				ps.executeUpdate();
				ps.close();
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
		    return company_ID;
		}	
	
		private int Insert_into_CompanyRegisterBankDetails(int company_ID, BankdetailsPOJO[] bankdet, Connection connection2) {
			
			String INSERT_QUERY 	="insert into \"Admin_Schema\".bankaccount_details (company_id, accountholder_name, bank_id,"
										+ "accountnooriban, ifscorswiftcode ) values(?,?,?,?,?)";
			//, NoBankSerialNo
			/*-- NoBankSerialNo is the serialization of 
			 * the bank in which the bank details would  
			 * be tracked */
			PreparedStatement ps 	= null;
			try {
				// Insert into company_bank_details
				ps 					= connection.prepareStatement(INSERT_QUERY);
				for (BankdetailsPOJO bankPojo : bankdet) {
					ps.setInt(1, company_ID);				// company_id
//					ps.setInt(2, bankPojo.serialno);
					ps.setString(2, bankPojo.accountholderName);	// accountholder_name
					ps.setInt(3, bankPojo.bank_ID); 		// bank_id is used for the normalization of the bank name details
					ps.setString(4, bankPojo.accountNo);	// accountno or iban
					ps.setString(5, bankPojo.ifscCode);		// ifsc or swiftcode
					ps.addBatch();
				}
				ps.executeBatch();
				ps.close();
				
			}catch (Exception e) {
			    e.printStackTrace();
			    return 0;
			} finally {
			    try {
			        if (ps != null) ps.close();
			    } catch (SQLException ex) {
			        ex.printStackTrace();
			    }
			}
			return company_ID;
		}




	
		
		
}
