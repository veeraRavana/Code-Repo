package AdminApplicationModel;


public class TaxProfile {
    private int taxProfileId;
    private String taxUpdatedDate;
    private int version;
    private String taxProfileName;

    public TaxProfile(int taxProfileId, String taxUpdatedDate, int version, String taxProfileName) {
        this.taxProfileId = taxProfileId;
        this.taxUpdatedDate = taxUpdatedDate;
        this.version = version;
        this.taxProfileName = taxProfileName;
    }

    public int getTaxProfileId() {
        return taxProfileId;
    }

    public void setTaxProfileId(int taxProfileId) {
        this.taxProfileId = taxProfileId;
    }

    public String getTaxUpdatedDate() {
        return taxUpdatedDate;
    }

    public void setTaxUpdatedDate(String taxUpdatedDate) {
        this.taxUpdatedDate = taxUpdatedDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTaxProfileName() {
        return taxProfileName;
    }

    public void setTaxProfileName(String taxProfileName) {
        this.taxProfileName = taxProfileName;
    }
}
