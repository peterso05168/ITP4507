package model;

public class Command {
	private String action;
	private int dvdId;
	private String title;
	private Integer copies;
	
	public Command (String action, int dvdId, String title, Integer copies) {
		this.action = action;
		this.dvdId = dvdId;
		this.title = title;
		this.copies = copies;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getDvdId() {
		return dvdId;
	}

	public void setDvdId(int dvdId) {
		this.dvdId = dvdId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	
	@Override
	public String toString() {
		if (this.copies != null && this.copies > 0) {
			return this.action + " " + this.dvdId + " " + this.title + " " + "("+ this.getCopies() + " copies)"; 
		}else {
			return this.action + " " + this.dvdId + " " + this.title + " "; 
		}
	}

}
