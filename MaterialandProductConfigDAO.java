package AdminDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import AdminApplicationModel.MaterialCategoryDataPOJO;
import AdminApplicationModel.MaterialCategoryFlexiTablePOJO;
import AdminApplicationModel.MaterialCategoryMainTablePOJO;
import AdminApplicationModel.MaterialCategoryTypeMainTablePOJO;
import AdminApplicationModel.ProductCategoryDataPOJO;
import AdminApplicationModel.ProductCategoryFlexiTablePOJO;
import AdminApplicationModel.ProductCategoryMainTablePOJO;
import AdminApplicationModel.ProductCategoryTypeMainTablePOJO;
import common.UOMs;
import commonGUI.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaterialandProductConfigDAO {
	
	 private Connection connection;
	 
	public MaterialandProductConfigDAO() {
	    try {
            connection 		= DatabaseConnection.getInstance().getConnection();
            if (connection 	== null) {
                System.err.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public ObservableList<MaterialCategoryMainTablePOJO> getAllMaterialCategoriesMainTableListData() {
		 
		ObservableList<MaterialCategoryMainTablePOJO> MatCatTableData 	= null;
		List<MaterialCategoryMainTablePOJO> MatCatList					= new ArrayList<>();
		    String query 				= "select mat_categoryid, mat_categoryname, mat_category_status "
		    								+ "from \"ComGeneral\".Material_category order by"
		    								+ " mat_categoryid asc";

		    try (PreparedStatement stmt = connection.prepareStatement(query);
		         ResultSet rs 			= stmt.executeQuery()) {

		        while (rs.next()) {
		        	MatCatList.add(new MaterialCategoryMainTablePOJO(
		        		rs.getInt("mat_categoryid"),
		                rs.getString("mat_categoryname"),
		                rs.getString("mat_category_status").equals("t") ? "Active" : "Inactive"

		            ));
		        }
		        MatCatTableData 		= FXCollections.observableArrayList(MatCatList);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return MatCatTableData;
	}

	public ObservableList<MaterialCategoryTypeMainTablePOJO> getMaterialTypeCategoryById(int selectedMaterialCategorySeqID) {

		ObservableList <MaterialCategoryTypeMainTablePOJO> MatTypeData 	= null;
	    List<MaterialCategoryTypeMainTablePOJO> MatType 				= new ArrayList<>();
	    
	    String sql 					= "SELECT	ROW_NUMBER() OVER ( ORDER BY MCT.MAT_TYPEID ) AS S_NO, MCT.MAT_CATEGORYID,"
	    								+ "	MCT.MAT_TYPEID,	MCT.MAT_TYPENAME,UT.UOM_ID, UT.UOM_NAME, MCT.MAT_TYPESTATUS "
	    								+ " FROM \"ComGeneral\".MATERIAL_CATEGORY_TYPE MCT left join \"ComGeneral\".UOM_TABLE UT "
	    								+ " on UOM_ID = MAT_TYPEUOMID where MCT.MAT_CATEGORYID = ? order by MCT.MAT_TYPEID Asc";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1,selectedMaterialCategorySeqID );
	        
	        ResultSet rs 			= stmt.executeQuery();
	        while (rs.next()) {
	        	MatType.add(new MaterialCategoryTypeMainTablePOJO(
	        		rs.getInt("S_NO"),
	        		rs.getInt("MAT_CATEGORYID"),	
	                rs.getInt("MAT_TYPEID"),
	                rs.getString("MAT_TYPENAME"),
	                rs.getInt("UOM_ID"),	
	                rs.getString("UOM_NAME"),
	                rs.getString("MAT_TYPESTATUS").equals("t") ? "Active" : "Inactive"
	        		
	            ));
	        }
	        MatTypeData			=   FXCollections.observableArrayList(MatType);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return MatTypeData;
	
	}

/************************************************************
 * Add Material Category Screen -STARTS-
 ************************************************************/
	
	public ObservableList<UOMs>GetUOMDetails () {
		ObservableList<UOMs> OlUOMData	= null;
		List<UOMs> uomList 				= new ArrayList<>();
		
		String Sqlquery						= "select uom_id,uom_name from \"ComGeneral\".UOM_TABLE"
											+ " where uom_status=true order by uom_id";
		
	    try (PreparedStatement stmt = connection.prepareStatement(Sqlquery);
		         ResultSet rs 			= stmt.executeQuery()) {

		        while (rs.next()) {
		        	uomList.add(new UOMs(
		        		rs.getInt("uom_id"),
		                rs.getString("uom_name")
		            ));
		        }
		        
		        OlUOMData 			= FXCollections.observableArrayList(uomList);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
		return OlUOMData;
	}

	public ObservableList<MaterialCategoryFlexiTablePOJO> getMaterialCategoryFlexiById(int selectedMaterialCategorySeqID) {
		
		ObservableList <MaterialCategoryFlexiTablePOJO> MatFlexiData 	= null;
	    List<MaterialCategoryFlexiTablePOJO> MatFlexiList 				= new ArrayList<>();
	    String sql 					= " select MAT_CATEGORYID, MAT_FLEXICONFIGID , ROW_NUMBER() OVER ( ORDER BY MAT_FLEXICONFIGID )"
	    								+ " AS SN_NO, MAT_FLEXI_NAME, MAT_FLEXI_COMPONETS, MAT_FLEXISTATUS from "
	    								+ "\"ComGeneral\".MATERIAL_CATEGORY_FLEXI where mat_categoryid = ?  order by"
	    								+ " mat_flexiconfigid asc";
	
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1,selectedMaterialCategorySeqID );
	        
	        ResultSet rs 			= stmt.executeQuery();
	        while (rs.next()) {
	        	MatFlexiList.add(new MaterialCategoryFlexiTablePOJO(
		        			rs.getInt("SN_NO"),
		        			rs.getInt("MAT_CATEGORYID"),
		        			rs.getInt("MAT_FLEXICONFIGID"),
		        			rs.getString("MAT_FLEXI_NAME"),
		        			rs.getString("MAT_FLEXI_COMPONETS"),
		        			rs.getString("MAT_FLEXISTATUS").equals("t") ? "Active" :"In Active"
	        			));
	        }
	        MatFlexiData			=   FXCollections.observableArrayList(MatFlexiList);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	    return MatFlexiData;
	}
	
	
/****************************************************************
 * Insert Into Material category Table's
 * [ MATERIAL_CATEGORY, MATERIAL_CATEGORY_TYPE, 
 *  MATERIAL_CATEGORY_FLEXI ]
 *****************************************************************/	
	public Integer SaveIntoMaterialCategoryTables(Integer MaterialCategoryID, MaterialCategoryDataPOJO MatCatDataPOJO) {
		
		try {
			 connection.setAutoCommit(false);

			 if(MaterialCategoryID		== 0 ) {
				 MaterialCategoryID		=	getSeqMaterialCategory(connection);
			 }
			 
					 if(MaterialCategoryID	== 0 ) {
						 connection.rollback();
						 return 0;
					 }
			 
			 MaterialCategoryID 		= Insert_into_MaterialCategory(MaterialCategoryID, MatCatDataPOJO.getMaterialCategory());
			 
					 if(MaterialCategoryID	== 0 ) {
						 connection.rollback();
						 return 0;
					 }
			 
			 MaterialCategoryID 		= Insert_Into_MaterialCategoryType(MaterialCategoryID, MatCatDataPOJO.getTypeList());
					
			 		if(MaterialCategoryID	== 0 ) {
						 connection.rollback();
						 return 0;
					 }
			 
			 MaterialCategoryID 		= Insert_Into_MaterialCategoryFlexi(MaterialCategoryID, MatCatDataPOJO.getFlexiList());
			
					 if(MaterialCategoryID	== 0 ) {
						 connection.rollback();
						 return 0;
					 }
					 
			 connection.commit(); // success
			 return MaterialCategoryID;
			
		}catch (SQLException e) {
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
	
	public Integer ModifyMaterialCategoryTables(Integer MaterialCategoryID, MaterialCategoryDataPOJO MatCatDataPOJO) {
		try {
			connection.setAutoCommit(false);
			
			MaterialCategoryID 		= UpdateMaterialCategory(MaterialCategoryID, MatCatDataPOJO.getMaterialCategory());
			
			MaterialCategoryID 		= ModifyMaterialCategoryType(MaterialCategoryID, MatCatDataPOJO.getTypeList());
			
				if(MaterialCategoryID	== 0 ) {
					 connection.rollback();
					 return 0;
				 }
				
			MaterialCategoryID 		= ModifyCategoryFlexi(MaterialCategoryID, MatCatDataPOJO.getFlexiList());
			
				if(MaterialCategoryID	== 0 ) {
					 connection.rollback();
					 return 0;
				 }
			 connection.commit(); // success
			 return MaterialCategoryID;
			 
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

	public Integer DeleteMaterialCategoryTables(Integer MaterialCategoryID) {
		   PreparedStatement ps = null;
		    try {
		        connection.setAutoCommit(false);
		        
		        String deleteCategoryFlexiSQL 	= "DELETE FROM \"ComGeneral\".MATERIAL_CATEGORY_FLEXI WHERE	MAT_CATEGORYID = ?";
		        ps = connection.prepareStatement(deleteCategoryFlexiSQL);
		        ps.setInt(1, MaterialCategoryID);
		        ps.executeUpdate();
		        ps.close();
		        
		        
		        String deleteMaterialTypeSQL 	 = "DELETE FROM \"ComGeneral\".MATERIAL_CATEGORY_TYPE WHERE	MAT_CATEGORYID = ?";
		        ps = connection.prepareStatement(deleteMaterialTypeSQL);
		        ps.setInt(1, MaterialCategoryID);
		        ps.executeUpdate();
		        ps.close();
		        
		        String deleteMaterialCategorySQL = "DELETE FROM \"ComGeneral\".MATERIAL_CATEGORY WHERE MAT_CATEGORYID = ?";
		        ps = connection.prepareStatement(deleteMaterialCategorySQL);
		        ps.setInt(1, MaterialCategoryID);
		        ps.executeUpdate();
		        ps.close();
		        
		        connection.commit();
		        
		        return MaterialCategoryID;

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

	
 	private Integer getSeqMaterialCategory(Connection connection) throws SQLException {
		String Sql 					= "SELECT nextval('\"ComGeneral\".material_category_mat_categoryid_seq'::regclass)";
	    try (PreparedStatement ps 	= connection.prepareStatement(Sql);
	    		ResultSet rs 		= ps.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    }
		return 0;
	}
	
	private Integer Insert_into_MaterialCategory(Integer MaterialCategoryID, MaterialCategoryMainTablePOJO materialCategory) {
	  
		String INSERT_QUERY 	= "INSERT INTO	\"ComGeneral\".MATERIAL_CATEGORY "
	    							+ "( MAT_CATEGORYID, MAT_CATEGORYNAME, MAT_CATEGORY_STATUS"
	    							+ " ) VALUES (?, ?, ?)";
		PreparedStatement ps 	= null;
		
		try {
			ps 					= connection.prepareStatement(INSERT_QUERY);
			ps.setInt(1, MaterialCategoryID);
			ps.setString(2, materialCategory.getMaterialCategoryName());
			ps.setBoolean(3, materialCategory.getMaterialCategoryStatus().equals("Active") ? true: false );
			
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
		return MaterialCategoryID;
	}

	private Integer Insert_Into_MaterialCategoryType(Integer MaterialCategoryID,
					List<MaterialCategoryTypeMainTablePOJO> MaterialTypeList) {
		String INSERT_QUERY 		= "INSERT INTO	\"ComGeneral\".MATERIAL_CATEGORY_TYPE ( MAT_CATEGORYID,"
										+ " MAT_TYPENAME, MAT_TYPEUOMID, MAT_TYPESTATUS )"
										+ " VALUES (?, ?, ?, ?)";
		PreparedStatement ps 		= null;
		try {
			ps 						= connection.prepareStatement(INSERT_QUERY);
			for (MaterialCategoryTypeMainTablePOJO matTypePOJOs : MaterialTypeList) {
				ps.setInt(1, MaterialCategoryID);				// company_id
				ps.setString(2, matTypePOJOs.getCategorymaterialTypeName());	// accountholder_name
				ps.setInt(3, matTypePOJOs.getCategorymaterialTypeUOMID());	
				ps.setBoolean(4, matTypePOJOs.getCategorymaterialTypeStatus().equals("Active") ? true: false );	
				
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
		return MaterialCategoryID;
	}
	
	private Integer Insert_Into_MaterialCategoryFlexi(Integer MaterialCategoryID,
									List<MaterialCategoryFlexiTablePOJO> MaterialFlexiList) {
		
		String INSERT_QUERY 		= "INSERT INTO	\"ComGeneral\".MATERIAL_CATEGORY_FLEXI ( MAT_CATEGORYID,"
										+ " MAT_FLEXI_NAME, MAT_FLEXI_COMPONETS, MAT_FLEXISTATUS )"
										+ " VALUES (?, ?, ?, ?)";
		PreparedStatement ps 		= null;
		try {
			ps 						= connection.prepareStatement(INSERT_QUERY);
			for (MaterialCategoryFlexiTablePOJO matTypePOJOs : MaterialFlexiList) {
				ps.setInt(1, MaterialCategoryID);			
				ps.setString(2, matTypePOJOs.getCategoryMaterialFlexiName());	
				ps.setString(3, matTypePOJOs.getCategoryMaterialFlexiComponet());	
				ps.setBoolean(4, matTypePOJOs.getCategoryMaterialFlexiStatus().equals("Active") ? true: false );	
		
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
		return MaterialCategoryID;
	}
	
/************************************************************
 * Add Material Category Screen -ENDS-
 ************************************************************/	
 /*************************************************
 * MATERIAL CATEGORY Sync Algorithm
 * this is used to update the material category table
 * MATERIAL_CATEGORY
 **************************************************/
	private Integer UpdateMaterialCategory(Integer MaterialCategoryID , MaterialCategoryMainTablePOJO materialCategory) {
	
	    String update 					= "UPDATE \"ComGeneral\".MATERIAL_CATEGORY SET MAT_CATEGORYNAME = ?,"
	    									+ " MAT_CATEGORY_STATUS = ? WHERE MAT_CATEGORYID = ?";
			try (PreparedStatement ps 	= connection.prepareStatement(update)) {
				ps.setString(1, materialCategory.getMaterialCategoryName());
				ps.setBoolean(2, materialCategory.getMaterialCategoryStatus().equals("Active") ? true: false);
				ps.setInt(3, 	materialCategory.getMaterialCategoryID());
	
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return MaterialCategoryID;
	}
	
	
 /*************************************************
 *  Material Type Sync Algorithm
 **************************************************/	
	
 	private Integer ModifyMaterialCategoryType(Integer MaterialCategoryID,
						List<MaterialCategoryTypeMainTablePOJO> updatedList) {
	
	    List<MaterialCategoryTypeMainTablePOJO> dbList 			= getMaterialTypeCategoryById(MaterialCategoryID).stream().toList();
	    
	    Map<Integer, MaterialCategoryTypeMainTablePOJO> dbMap 	= dbList.stream().collect(
	    						Collectors.toMap(
		    						MaterialCategoryTypeMainTablePOJO::getCategorymaterialTypeID,
		    						Function.identity()
	    						) );
	    Set<Integer> updatedIds 		= new HashSet<>();
	    
	    for (MaterialCategoryTypeMainTablePOJO updated : updatedList) {
	    	 Integer id 				= updated.getCategorymaterialTypeID() == null ? 0 : updated.getCategorymaterialTypeID();
	    	 if (id == null || id == 0) {
	        	List<MaterialCategoryTypeMainTablePOJO>	Matepojodata	= ConvertMatTypePOJOToList(updated);
	        	Insert_Into_MaterialCategoryType(MaterialCategoryID ,Matepojodata); // New row
	        } else if (dbMap.containsKey(id)) {
	        	updateMaterialType(updated); // Modified row
	            updatedIds.add(id);
	        }
	    }

	    for (MaterialCategoryTypeMainTablePOJO dbRow : dbList) {
	        if (!updatedIds.contains(dbRow.getCategorymaterialTypeID())) {
	        	DeleteMaterialType(dbRow.getCategorymaterialTypeID()); // Deleted in UI
	        }
	    }
	    
		return MaterialCategoryID;
	}

	private List<MaterialCategoryTypeMainTablePOJO>ConvertMatTypePOJOToList (MaterialCategoryTypeMainTablePOJO MatTypepojo) {
		
		List<MaterialCategoryTypeMainTablePOJO> MatTypeList 	= new ArrayList<>();
			 MatTypeList.add( new MaterialCategoryTypeMainTablePOJO(
					 MatTypepojo.getCategorymaterialTypeSNno(),
					 			MatTypepojo.getCategorymaterialCategoryID(),
					 			MatTypepojo.getCategorymaterialTypeID(),
					 			MatTypepojo.getCategorymaterialTypeName(),
					 			MatTypepojo.getCategorymaterialTypeUOMID(),
					 			MatTypepojo.getCategorymaterialTypeUOM(),
					 			MatTypepojo.getCategorymaterialTypeStatus()
			 		) );
		 return MatTypeList;
	 }

    private void updateMaterialType(MaterialCategoryTypeMainTablePOJO MatCateTypepojo) {
        String update 				= "UPDATE \"ComGeneral\".MATERIAL_CATEGORY_TYPE SET MAT_TYPENAME = ?, "
        									+ "MAT_TYPEUOMID = ?, MAT_TYPESTATUS = ? "
        									+ "WHERE MAT_TYPEID = ?";
        try (PreparedStatement ps 	= connection.prepareStatement(update)) {
            ps.setString(1, MatCateTypepojo.getCategorymaterialTypeName());
            ps.setInt(2, 	MatCateTypepojo.getCategorymaterialTypeUOMID());
            ps.setBoolean(3, MatCateTypepojo.getCategorymaterialTypeStatus().equals("Active") ? true: false);
            ps.setInt(4, 	MatCateTypepojo.getCategorymaterialTypeID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void DeleteMaterialType(int MaterialTypeID) {
        String delete 				= "DELETE FROM \"ComGeneral\".MATERIAL_CATEGORY_TYPE WHERE MAT_TYPEID = ?";
        try (PreparedStatement ps 	= connection.prepareStatement(delete)) {
            ps.setInt(1, MaterialTypeID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 /*************************************************
 *  Category Flexi Sync Algorithm
 **************************************************/
    
	private Integer ModifyCategoryFlexi(Integer MaterialCategoryID,
			List<MaterialCategoryFlexiTablePOJO> updatedFlexiList) {

		List<MaterialCategoryFlexiTablePOJO> dbList 			= getMaterialCategoryFlexiById(MaterialCategoryID).stream().toList();
		
		Map<Integer, MaterialCategoryFlexiTablePOJO> dbMap 		= dbList.stream().collect(
							Collectors.toMap(
									MaterialCategoryFlexiTablePOJO::getCategorymaterialFlexiID,
								Function.identity()
							) );
		Set<Integer> updatedFlexiIds 		= new HashSet<>();
		
		for (MaterialCategoryFlexiTablePOJO updatedFlexiPojo : updatedFlexiList) {
			 Integer id 				= updatedFlexiPojo.getCategorymaterialFlexiID() == null ? 0 : updatedFlexiPojo.getCategorymaterialFlexiID();
			 if (id == null || id == 0) {
				List<MaterialCategoryFlexiTablePOJO>	FlexipojoList	= ConvertCategoryFlexiPOJOToList(updatedFlexiPojo);
				Insert_Into_MaterialCategoryFlexi(MaterialCategoryID, FlexipojoList) ;// New row
			} else if (dbMap.containsKey(id)) {
				updateCategoryFlexi(updatedFlexiPojo); // Modified row
				updatedFlexiIds.add(id);
			}
		}
		
		for ( MaterialCategoryFlexiTablePOJO dbRow : dbList ) {
			if (	!updatedFlexiIds.contains( dbRow.getCategorymaterialFlexiID()	)	) {
				DeleteCategoryFlexi( dbRow.getCategorymaterialFlexiID()	); // Deleted in UI
			}
		}
	
		return MaterialCategoryID;
	}
    
	private List<MaterialCategoryFlexiTablePOJO > ConvertCategoryFlexiPOJOToList(MaterialCategoryFlexiTablePOJO CatFlexiPojo) {
		
		List <MaterialCategoryFlexiTablePOJO > catFlexiList 	= new ArrayList<>();
			catFlexiList.add(new MaterialCategoryFlexiTablePOJO(
					CatFlexiPojo.getCategoryMaterialFlexiSnno(),
					CatFlexiPojo.getCategoryMaterialID(),
					CatFlexiPojo.getCategoryMaterialFlexiName(),
					CatFlexiPojo.getCategoryMaterialFlexiComponet(),
					CatFlexiPojo.getCategoryMaterialFlexiStatus()
					));
		return catFlexiList;
	}
    
    private void updateCategoryFlexi(MaterialCategoryFlexiTablePOJO MatCateFlexiPojo) {
        String update 				= "UPDATE \"ComGeneral\".MATERIAL_CATEGORY_FLEXI SET MAT_FLEXI_NAME = ?,"
        									+ " MAT_FLEXI_COMPONETS = ?, MAT_FLEXISTATUS = ?"
        									+ " WHERE MAT_FLEXICONFIGID = ?";
        try (PreparedStatement ps 	= connection.prepareStatement(update)) {
	            ps.setString(1,  MatCateFlexiPojo.getCategoryMaterialFlexiName());
	            ps.setString(2,  MatCateFlexiPojo.getCategoryMaterialFlexiComponet());
	            ps.setBoolean(3, MatCateFlexiPojo.getCategoryMaterialFlexiStatus().equals("Active") ? true: false);
	            ps.setInt(4, 	 MatCateFlexiPojo.getCategorymaterialFlexiID());
           
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    private void DeleteCategoryFlexi(Integer MaterialFlexiID) {
        String delete 				= "DELETE FROM \"ComGeneral\".MATERIAL_CATEGORY_FLEXI WHERE MAT_FLEXICONFIGID = ?";
        try (PreparedStatement ps 	= connection.prepareStatement(delete)) {
            ps.setInt(1, MaterialFlexiID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/****************************************************
 * Material  Category Screen -Ends-
 ****************************************************/

/****************************************************
 * Product Category Screen -STARTS-
 ****************************************************/
	public ObservableList<ProductCategoryMainTablePOJO> getAllProductCategoriesMainTableListData() {
		
		ObservableList<ProductCategoryMainTablePOJO> ProdCatTableData 	= null;
		List<ProductCategoryMainTablePOJO> ProdCatList					= new ArrayList<>();
		    String query 				= "select PROD_CATEGORYID, PROD_CATEGORYNAME, PROD_CATEGORY_STATUS"
		    								+ " from \"ComGeneral\".PRODUCT_CATEGORY"
		    								+ " order by PROD_CATEGORYID asc";

		    try (PreparedStatement stmt = connection.prepareStatement(query);
		         ResultSet rs 			= stmt.executeQuery()) {

		        while (rs.next()) {
		        	ProdCatList.add(new ProductCategoryMainTablePOJO(
		        		rs.getInt("PROD_CATEGORYID"),
		                rs.getString("PROD_CATEGORYNAME"),
		                rs.getString("PROD_CATEGORY_STATUS").equals("t") ? "Active" : "Inactive"

		            ));
		        }
		        ProdCatTableData 		= FXCollections.observableArrayList(ProdCatList);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return ProdCatTableData;
	
	}
	
	public ObservableList<ProductCategoryTypeMainTablePOJO> getProductTypeCategoryById(Integer selectedProductCategorySeqID) {
		ObservableList <ProductCategoryTypeMainTablePOJO> ProdTypeData 	= null;
	    List<ProductCategoryTypeMainTablePOJO> ProdType 				= new ArrayList<>();
	    
	    String sql 					= "SELECT	ROW_NUMBER() OVER ( ORDER BY PCT.PROD_TYPEID ) AS S_NO, PCT.PROD_CATEGORYID,"
	    								+ " PCT.PROD_TYPEID,	PCT.PROD_TYPENAME, UT.UOM_ID, UT.UOM_NAME, PCT.PROD_TYPESTATUS"
	    								+ " FROM \"ComGeneral\".PRODUCT_CATEGORY_TYPE PCT"
	    								+ " left join \"ComGeneral\".UOM_TABLE UT on UOM_ID = PROD_TYPEUOMID"
	    								+ " where PCT.PROD_CATEGORYID = ? order by PCT.PROD_TYPEID Asc";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1,selectedProductCategorySeqID );
	        
	        ResultSet rs 			= stmt.executeQuery();
	        while (rs.next()) {
	        	ProdType.add(new ProductCategoryTypeMainTablePOJO(
	        		rs.getInt("S_NO"),
	        		rs.getInt("PROD_CATEGORYID"),	
	                rs.getInt("PROD_TYPEID"),
	                rs.getString("PROD_TYPENAME"),
	                rs.getInt("UOM_ID"),	
	                rs.getString("UOM_NAME"),
	                rs.getString("PROD_TYPESTATUS").equals("t") ? "Active" : "Inactive"
	        		
	            ));
	        }
	        ProdTypeData			=   FXCollections.observableArrayList(ProdType);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ProdTypeData;

	} 

	public ObservableList<ProductCategoryFlexiTablePOJO> getProductCategoryFlexiById(Integer selectedProductCategorySeqID) {
		
		ObservableList <ProductCategoryFlexiTablePOJO> ProdFlexiData 	= null;
	    List<ProductCategoryFlexiTablePOJO> ProdFlexiList 				= new ArrayList<>();
	    String sql 					= " select PROD_CATEGORYID, PROD_FLEXICONFIGID , ROW_NUMBER() OVER ( ORDER BY "
	    								+ "PROD_FLEXICONFIGID )AS SN_NO, PROD_FLEXI_NAME, PROD_FLEXI_COMPONETS,"
	    								+ " PROD_FLEXISTATUS from \"ComGeneral\".PRODUCT_CATEGORY_FLEXI "
	    								+ "where PROD_CATEGORYID = ?  order by PROD_FLEXICONFIGID asc";
	
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1,selectedProductCategorySeqID );
	        
	        ResultSet rs 			= stmt.executeQuery();
	        while (rs.next()) {
	        	ProdFlexiList.add(new ProductCategoryFlexiTablePOJO(
		        			rs.getInt("SN_NO"),
		        			rs.getInt("PROD_CATEGORYID"),
		        			rs.getInt("PROD_FLEXICONFIGID"),
		        			rs.getString("PROD_FLEXI_NAME"),
		        			rs.getString("PROD_FLEXI_COMPONETS"),
		        			rs.getString("PROD_FLEXISTATUS").equals("t") ? "Active" :"In Active"
	        			));
	        }
	        ProdFlexiData			=   FXCollections.observableArrayList(ProdFlexiList);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	    return ProdFlexiData;
	}
	
 /****************************************************************
  * Insert Into Material category Table's
  * [ MATERIAL_CATEGORY, MATERIAL_CATEGORY_TYPE, 
  *  MATERIAL_CATEGORY_FLEXI ]
  *****************************************************************/	 
	public Integer SaveIntoProductCategoryTables(Integer productCategoryID, ProductCategoryDataPOJO prodCatDataPOJO) {
		
		try {
			 connection.setAutoCommit(false);

			 if(productCategoryID		== 0 ) {
				 productCategoryID		=	getSeqProductCategory(connection);
			 }
			 
			 		IfKeyZeroRollback(productCategoryID, connection);
			 
			 productCategoryID 		= Insert_into_ProductCategory(productCategoryID, prodCatDataPOJO.getProductCategory());
			 
			 		IfKeyZeroRollback(productCategoryID, connection);
			 
			 productCategoryID 		= Insert_Into_ProductCategoryType(productCategoryID, prodCatDataPOJO.getProductCategoryTypeList());
					
			 		IfKeyZeroRollback(productCategoryID, connection);
			 
			 productCategoryID 		= Insert_Into_ProductCategoryFlexi(productCategoryID, prodCatDataPOJO.getProductCategoryFlexiList());
			
			 		IfKeyZeroRollback(productCategoryID, connection);
						 
			 connection.commit(); // success
			 return productCategoryID;
			
		}catch (SQLException e) {
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

	private Integer getSeqProductCategory(Connection connection) throws SQLException {
		String Sql 					= "SELECT nextval('\"ComGeneral\".product_category_prod_categoryid_seq'::regclass)";
	    try (PreparedStatement ps 	= connection.prepareStatement(Sql);
	    		ResultSet rs 		= ps.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    }
		return 0;
	}

	private Integer Insert_into_ProductCategory(Integer productCategoryID, ProductCategoryMainTablePOJO productCategory) throws SQLException {
		  
			String INSERT_QUERY 	= "INSERT INTO	\"ComGeneral\".PRODUCT_CATEGORY "
		    							+ "( PROD_CATEGORYID, PROD_CATEGORYNAME, PROD_CATEGORY_STATUS"
		    							+ " ) VALUES (?, ?, ?)";
			PreparedStatement ps 	= null;
			
			try {
				ps 					= connection.prepareStatement(INSERT_QUERY);
				ps.setInt(1, productCategoryID);
				ps.setString(2, productCategory.getProductCategoryName());
				ps.setBoolean(3, productCategory.getProductCategoryStatus().equals("Active") ? true: false );
				
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
			return productCategoryID;
		}
	
	private Integer Insert_Into_ProductCategoryType(Integer productCategoryID, 
		List<ProductCategoryTypeMainTablePOJO> productCategoryTypeList)  {
				String INSERT_QUERY 		= "INSERT INTO	\"ComGeneral\".PRODUCT_CATEGORY_TYPE ( PROD_CATEGORYID,"
						+ " PROD_TYPENAME, PROD_TYPEUOMID, PROD_TYPESTATUS )"
						+ " VALUES (?, ?, ?, ?)";
		PreparedStatement ps 		= null;
		try {
			ps 						= connection.prepareStatement(INSERT_QUERY);
			for (ProductCategoryTypeMainTablePOJO ProdTypePOJOs : productCategoryTypeList) {
				ps.setInt(1, productCategoryID);				// company_id
				ps.setString(2, ProdTypePOJOs.getProductTypeName());	// accountholder_name
				ps.setInt(3, ProdTypePOJOs.getProductTypeUOMID());	
				ps.setBoolean(4, ProdTypePOJOs.getProductTypeStatus().equals("Active") ? true: false );	
				
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
		return productCategoryID;
	}
	
	private Integer Insert_Into_ProductCategoryFlexi(Integer productCategoryID,
					List<ProductCategoryFlexiTablePOJO> ProductFlexiList) {
					
		String INSERT_QUERY 		= "INSERT INTO	\"ComGeneral\".PRODUCT_CATEGORY_FLEXI ( PROD_CATEGORYID,"
										+ " PROD_FLEXI_NAME, PROD_FLEXI_COMPONETS, PROD_FLEXISTATUS )"
										+ " VALUES (?, ?, ?, ?)";
		PreparedStatement ps 		= null;
		try {
			ps 						= connection.prepareStatement(INSERT_QUERY);
			for (ProductCategoryFlexiTablePOJO ProdFlexiPOJOs : ProductFlexiList) {
				ps.setInt(1, productCategoryID);			
				ps.setString(2, ProdFlexiPOJOs.getCategoryProductFlexiName());	
				ps.setString(3, ProdFlexiPOJOs.getCategoryProductFlexiComponet());	
				ps.setBoolean(4, ProdFlexiPOJOs.getCategoryProductFlexiStatus().equals("Active") ? true: false );	
				
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
		return productCategoryID;
	}
	
	public Integer ModifyProductCategoryTables(Integer productCategoryID, ProductCategoryDataPOJO prodCatDataPOJO) {
		try {
			connection.setAutoCommit(false);
			
			productCategoryID 		= UpdateProductCategory(productCategoryID, prodCatDataPOJO.getProductCategory());
			
			productCategoryID 		= ModifyProductCategoryType(productCategoryID, prodCatDataPOJO.getProductCategoryTypeList());
			
				IfKeyZeroRollback(productCategoryID, connection);
				
			productCategoryID 		= ModifyProductCategoryFlexi(productCategoryID, prodCatDataPOJO.getProductCategoryFlexiList());
			
				IfKeyZeroRollback(productCategoryID, connection);
			
			 connection.commit(); // success
			 return productCategoryID;
			 
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

	public Integer DeleteProductCategoryTables(Integer productCategoryID) {
		   PreparedStatement ps = null;
		    try {
		        connection.setAutoCommit(false);

		        String deleteCategoryFlexiSQL 	= "DELETE FROM \"ComGeneral\".PRODUCT_CATEGORY_FLEXI WHERE PROD_CATEGORYID = ?";
		        ps = connection.prepareStatement(deleteCategoryFlexiSQL);
		        ps.setInt(1, productCategoryID);
		        ps.executeUpdate();
		        ps.close();
		        
		        
		        String deleteMaterialTypeSQL 	 = "DELETE FROM \"ComGeneral\".PRODUCT_CATEGORY_TYPE WHERE	PROD_CATEGORYID = ?";
		        ps = connection.prepareStatement(deleteMaterialTypeSQL);
		        ps.setInt(1, productCategoryID);
		        ps.executeUpdate();
		        ps.close();
		        
		        String deleteMaterialCategorySQL = "DELETE FROM \"ComGeneral\".PRODUCT_CATEGORY_FLEXI WHERE PROD_CATEGORYID = ?";
		        ps = connection.prepareStatement(deleteMaterialCategorySQL);
		        ps.setInt(1, productCategoryID);
		        ps.executeUpdate();
		        ps.close();
		        
		        connection.commit();
		        
		        return productCategoryID;

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
	
		
 /****************************************************
 * PRODUCT CATEGORY Sync Algorithm
 * this is used to update the Product category table
 *****************************************************/
/*UpdateProductCategory ->
 *  update the Category name and status*/	
	private Integer UpdateProductCategory(Integer productCategoryID , ProductCategoryMainTablePOJO productCategory) {
		
		    String update 					= "UPDATE \"ComGeneral\".PRODUCT_CATEGORY SET PROD_CATEGORYNAME = ?,"
		    									+ " PROD_CATEGORY_STATUS = ? WHERE PROD_CATEGORYID = ?";
				try (PreparedStatement ps 	= connection.prepareStatement(update)) {
					// Set the parameters for the prepared statement
					ps.setString(1, 	productCategory.getProductCategoryName());
					ps.setBoolean(2, 	productCategory.getProductCategoryStatus().equals("Active") ? true: false);
					ps.setInt(3, 		productCategory.getProductCategoryID());
		
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			return productCategoryID;
		}

/* ModifyProductCategoryType-> gets the Product Category type by productCategoryID
 * and filter the identical productCategoryID and productTypeID 
 * the insert the new row if the productTypeID is null or 0 then 
 * if it already exists then it will update the row
 * or the productTypeID is not in the database then it will delete the row */
		private Integer ModifyProductCategoryType(Integer productCategoryID,
				List<ProductCategoryTypeMainTablePOJO> updatedTypeList) {
			
			List<ProductCategoryTypeMainTablePOJO> dbList 			= getProductTypeCategoryById(productCategoryID).stream().toList();
			
			Map<Integer, ProductCategoryTypeMainTablePOJO> dbMap 	= dbList.stream().collect(
									Collectors.toMap(
										ProductCategoryTypeMainTablePOJO::getProductTypeID,
			    						Function.identity()
									) );
			Set<Integer> updatedIds 		= new HashSet<>();
			
			for (ProductCategoryTypeMainTablePOJO updated : updatedTypeList) {
				 Integer id 				= updated.getProductTypeID() == null ? 0 : updated.getProductTypeID();
				 if (id == null || id == 0) {
				 //converts the POJO to List<ProductCategoryTypeMainTablePOJO>
			    	List<ProductCategoryTypeMainTablePOJO>	ProdPojoData	= ConvertProdTypePOJOToList(updated);
			    	Insert_Into_ProductCategoryType(productCategoryID ,ProdPojoData); // New row
			    } else if (dbMap.containsKey(id)) {
			    	updateProductType(updated); // Modified row
			        updatedIds.add(id);
			    }
			}
			
			for (ProductCategoryTypeMainTablePOJO dbRow : dbList) {
			    if (!updatedIds.contains(dbRow.getProductTypeID())) {
			    	DeleteProductType(dbRow.getProductTypeID()); // Deleted in UI
			    }
			}
			
			return productCategoryID;
		}	
		
		private List<ProductCategoryTypeMainTablePOJO>ConvertProdTypePOJOToList (
				ProductCategoryTypeMainTablePOJO ProdTypepojo) {
			
			List<ProductCategoryTypeMainTablePOJO> ProdTypeList 	= new ArrayList<>();
						ProdTypeList.add( new ProductCategoryTypeMainTablePOJO(
							ProdTypepojo.getProductCategoryTypeSNno(),
							ProdTypepojo.getProductCategoryID(),
							ProdTypepojo.getProductTypeID(),
							ProdTypepojo.getProductTypeName(),
							ProdTypepojo.getProductTypeUOMID(),
							ProdTypepojo.getProductTypeUOM(),
							ProdTypepojo.getProductTypeStatus()
				 		) );
			 return ProdTypeList;
		 }
		
		private void updateProductType(ProductCategoryTypeMainTablePOJO ProdTypePojo) {
		        String update 				= "UPDATE \"ComGeneral\".PRODUCT_CATEGORY_TYPE SET PROD_TYPENAME = ?, "
		        									+ "PROD_TYPEUOMID = ?, PROD_TYPESTATUS = ? "
		        									+ "WHERE PROD_TYPEID = ?";
		        try (PreparedStatement ps 	= connection.prepareStatement(update)) {
		            ps.setString(1,  ProdTypePojo.getProductTypeName());
		            ps.setInt(2, 	 ProdTypePojo.getProductTypeUOMID());
		            ps.setBoolean(3, ProdTypePojo.getProductTypeStatus().equals("Active") ? true: false);
		            ps.setInt(4, 	 ProdTypePojo.getProductTypeID());
		            ps.executeUpdate();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		}
		   
	    private void DeleteProductType(Integer productCategoryID) {
	        String delete 				= "DELETE FROM \"ComGeneral\".PRODUCT_CATEGORY_TYPE WHERE PROD_TYPEID = ?";
	        try (PreparedStatement ps 	= connection.prepareStatement(delete)) {
	            ps.setInt(1, productCategoryID);
	            ps.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	  
/****************************************************************/
		private Integer ModifyProductCategoryFlexi(Integer productCategoryID,
				List<ProductCategoryFlexiTablePOJO> updatedFlexiList) {

			List<ProductCategoryFlexiTablePOJO> dbList 			= getProductCategoryFlexiById(productCategoryID).stream().toList();
			
			Map<Integer, ProductCategoryFlexiTablePOJO> dbMap 	= dbList.stream().collect(
								Collectors.toMap(
										ProductCategoryFlexiTablePOJO::getCategoryProductFlexiID,
									Function.identity()
								) );
			Set<Integer> updatedFlexiIds 		= new HashSet<>();
			
			for (ProductCategoryFlexiTablePOJO updatedFlexiPojo : updatedFlexiList) {
				 Integer id 				= updatedFlexiPojo.getCategoryProductFlexiID() == null ? 0 : updatedFlexiPojo.getCategoryProductFlexiID();
				 if (id == null || id == 0) {
					List<ProductCategoryFlexiTablePOJO>	FlexipojoList	= ConvertMaterialFlexiPOJOToList(updatedFlexiPojo);
					Insert_Into_ProductCategoryFlexi(productCategoryID, FlexipojoList) ;// New row
				} else if (dbMap.containsKey(id)) {
					UpdateProductFlexi(updatedFlexiPojo); // Modified row
					updatedFlexiIds.add(id);
				}
			}
			
			for ( ProductCategoryFlexiTablePOJO dbRow : dbList ) {
				if (	!updatedFlexiIds.contains( dbRow.getCategoryProductFlexiID()	)	) {
					DeleteProductFlexi( dbRow.getCategoryProductFlexiID()	); // Deleted in UI
				}
			}
		
			return productCategoryID;
		}
		
		private List<ProductCategoryFlexiTablePOJO > ConvertMaterialFlexiPOJOToList(ProductCategoryFlexiTablePOJO ProdFlexiPojo) {
			
			List <ProductCategoryFlexiTablePOJO > ProdFlexiList 	= new ArrayList<>();
			ProdFlexiList.add(new ProductCategoryFlexiTablePOJO(
						ProdFlexiPojo.getCategoryProductFlexiSnno(),
						ProdFlexiPojo.getCategoryProductID(),
						ProdFlexiPojo.getCategoryProductFlexiName(),
						ProdFlexiPojo.getCategoryProductFlexiComponet(),
						ProdFlexiPojo.getCategoryProductFlexiStatus()
						));
			return ProdFlexiList;
		}
	   
	    private void UpdateProductFlexi(ProductCategoryFlexiTablePOJO ProdFlexiPojo) {
	        String update 				= "UPDATE \"ComGeneral\".PRODUCT_CATEGORY_FLEXI SET PROD_FLEXI_NAME = ?,"
	        									+ " PROD_FLEXI_COMPONETS = ?, PROD_FLEXISTATUS = ?"
	        									+ " WHERE PROD_FLEXICONFIGID = ?";
	        
	        try (PreparedStatement ps 	= connection.prepareStatement(update)) {
		            ps.setString(1,  ProdFlexiPojo.getCategoryProductFlexiName());
		            ps.setString(2,  ProdFlexiPojo.getCategoryProductFlexiComponet());
		            ps.setBoolean(3, ProdFlexiPojo.getCategoryProductFlexiStatus().equals("Active") ? true: false);
		            ps.setInt(4, 	 ProdFlexiPojo.getCategoryProductFlexiID());
		           
	            ps.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	   
	    private void DeleteProductFlexi(Integer productCategoryID) {
	        String delete 				= "DELETE FROM \"ComGeneral\".PRODUCT_CATEGORY_FLEXI WHERE PROD_FLEXICONFIGID = ?";
	        try (PreparedStatement ps 	= connection.prepareStatement(delete)) {
	            ps.setInt(1, productCategoryID);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

/****************************************************
 * Product Category Screen -ENDS-
 ****************************************************/
	public boolean MaterialAndProductConfigLog(Integer MaterialOrProductID, 
			MaterialCategoryDataPOJO MatCatDataPOJO,
			ProductCategoryDataPOJO ProdCatDataPOJO) {
	
		String insertLogSQL				= "insert into \"ComGeneral\".material_and_productlog (username,"
											+ " user_ip, MAT_OR_PRODID, MAT_OR_PRODTRANS_NAME ) "
											+ "values( ?,?,?,?)";
		
	    try (PreparedStatement stmt 	= connection.prepareStatement(insertLogSQL)) {
	    String UserName					= null;
	    String UserIpAddress 			= null;
	    String TransactionName 			= null;
	    
	    	if(MatCatDataPOJO 	!= null) {
			// If Material Category Data is provided
				MaterialCategoryMainTablePOJO MaterialCategory	= MatCatDataPOJO.getMaterialCategory();
				UserName				= MaterialCategory.getUserName();
				UserIpAddress			= MaterialCategory.getUserIPAddress();
				TransactionName 		= MaterialCategory.getMaterialCategoryTransactionName();
			}else if(ProdCatDataPOJO != null) {
			//	 If Product Category Data is provided	
				ProductCategoryMainTablePOJO ProductCategory	= ProdCatDataPOJO.getProductCategory();
				UserName				= ProductCategory.getUserName();
				UserIpAddress			= ProductCategory.getUserIPAddress();
				TransactionName 		= ProductCategory.getMatCatTransactionName();
			}
		    	stmt.setString(1,	UserName	);
		    	stmt.setString(2, 	UserIpAddress);
				stmt.setInt(3, 		MaterialOrProductID);
				stmt.setString(4,   TransactionName );
			
			int rowsInserted 				= stmt.executeUpdate();
	        return rowsInserted > 0;
	   
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	private Integer IfKeyZeroRollback(Integer key, Connection connection) throws SQLException {
		if (key == 0) {
			connection.rollback();
			return 0;
		}
		return key;
	}
}
