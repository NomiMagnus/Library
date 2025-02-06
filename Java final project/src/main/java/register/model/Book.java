package register.model;

public class Book {
	private String name;
	private String author;
	private String description;
	private boolean isLoan;
	private int loanUserId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isLoan() {
		return isLoan;
	}
	public void setLoan(boolean isLoan) {
		this.isLoan = isLoan;
	}
	public int getLoanUserId() {
		return loanUserId;
	}
	public void setLoanUserId(int loanUserId) {
		this.loanUserId = loanUserId;
	}
	
	
}

