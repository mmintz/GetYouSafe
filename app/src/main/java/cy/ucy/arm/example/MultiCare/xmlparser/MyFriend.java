package cy.ucy.arm.example.MultiCare.xmlparser;

public class MyFriend {

    private  String _id ;
    private  String name;
    private  String surname;
    private  String telNumber;

	public MyFriend (String _id, String name, String surname, String telNumber) {
		this.set_id(_id);
		this.setName(name);
		this.setSurname(surname);
		this.setTelNumber(telNumber);
	}

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
