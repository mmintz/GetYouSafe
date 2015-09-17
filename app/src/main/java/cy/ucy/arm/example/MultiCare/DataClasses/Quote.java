package cy.ucy.arm.example.MultiCare.DataClasses;

public class Quote {

	private int _id;
	private String quote;
	
	public Quote (int _id, String quote) {
		this.set_id(_id);
		this.setQuote(quote);
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}

}
