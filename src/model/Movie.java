package model;

public class Movie extends DVD {
	private String director;
	
	public Movie(int dvdID, String title, int length, int numAvailable, String director) {
		super(dvdID, title, length, numAvailable);
		this.director = director;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	@Override
	public String toString() {
		return "mo," + getDvdID() + "," + getTitle() + "," + getLength() + "," + getNumAvailable() + "," + this.director;
	}
}
