package AdminApplicationModel;

import java.util.List;

public class MaterialCategoryDataPOJO {
	
	private MaterialCategoryMainTablePOJO materialCategory;
    private List<MaterialCategoryTypeMainTablePOJO> typeList;
    private List<MaterialCategoryFlexiTablePOJO> flexiList;

    // Constructors
    public MaterialCategoryDataPOJO() {}

    public MaterialCategoryDataPOJO(
            MaterialCategoryMainTablePOJO materialCategory,
            List<MaterialCategoryTypeMainTablePOJO> typeList,
            List<MaterialCategoryFlexiTablePOJO> flexiList) {
    	
        this.materialCategory 	= materialCategory;
        this.typeList 			= typeList;
        this.flexiList 			= flexiList;
    }
    

    // Getters & Setters
    public MaterialCategoryMainTablePOJO getMaterialCategory() {        return materialCategory;    }
    public void setMaterialCategory(MaterialCategoryMainTablePOJO materialCategory) {
        this.materialCategory 	= materialCategory;
    }

    public List<MaterialCategoryTypeMainTablePOJO> getTypeList() {        return typeList;    }
    public void setTypeList(List<MaterialCategoryTypeMainTablePOJO> typeList) {
        this.typeList 			= typeList;
    }

    public List<MaterialCategoryFlexiTablePOJO> getFlexiList() {        return flexiList;    }
    public void setFlexiList(List<MaterialCategoryFlexiTablePOJO> flexiList) {
        this.flexiList 			= flexiList;
    }

	
	
}
