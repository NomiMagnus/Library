package register.model;

import java.time.LocalDate;

import javax.print.attribute.standard.DateTimeAtCompleted;
public class Loan {
	private LocalDate loanDate;
	private LocalDate returnDate;
	private String userId;
	private int extendPeriodTimes;
	private int bookId;
	private boolean isReturned;
	
	
	public LocalDate getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getExtendPeriodTimes() {
		return extendPeriodTimes;
	}
	public void setExtendPeriodTimes(int extendPeriodTimes) {
		this.extendPeriodTimes = extendPeriodTimes;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public boolean isReturned() {
		return isReturned;
	}
	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
}
