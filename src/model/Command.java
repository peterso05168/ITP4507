package model;

public class Command {
	private String action;
	private int dvdId;
	private String title;
	private Integer copies;
	private String singer;
	private String director;
	private int length;

	public Command(String action, int dvdId, String title, Integer copies, String singer, String director, int length) {
		this.action = action;
		this.dvdId = dvdId;
		this.title = title;
		this.copies = copies;
		this.singer = singer;
		this.director = director;
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
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
			return this.action + " " + this.dvdId + " " + this.title + " " + "(" + this.getCopies() + " copies)";
		} else {
			return this.action + " " + this.dvdId + " " + this.title + " ";
		}
	}

}
