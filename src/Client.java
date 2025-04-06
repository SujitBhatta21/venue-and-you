public class Client {
    public String companyName;
    public String contactName;
    public String contactEmail;
    public String phone;
    public String address;
    public String billingName;
    public String billingEmail;

    public Client(String companyName, String contactName, String contactEmail, String phone,
            String address, String billingName, String billingEmail) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.address = address;
        this.billingName = billingName;
        this.billingEmail = billingEmail;
    }
}
