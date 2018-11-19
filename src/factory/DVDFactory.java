package factory;

import model.DVD;
import model.MV;
import model.Movie;

public class DVDFactory {
	// use getShape method to get object of type shape
	public static DVD newDVD(String dvdType, int dvdID, String title, int length, int numAvailable, String singer, String director) {
		if (dvdType.equalsIgnoreCase("mo")) {
			return new Movie(dvdID, title, length, numAvailable, director);
		} else if (dvdType.equalsIgnoreCase("mv")) {
			return new MV(dvdID, title, length, numAvailable, singer);
		}
		
		return null;
	}
}
