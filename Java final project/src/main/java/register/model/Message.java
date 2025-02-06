package register.model;

import java.time.LocalDate;

public class Message {
	private String title;
	private String body;
	private LocalDate messageDate;
	private int userId;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public LocalDate getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(LocalDate messageDate) {
		this.messageDate = messageDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
