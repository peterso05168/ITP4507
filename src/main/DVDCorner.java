package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import factory.DVDFactory;
import model.DVD;
import model.MV;
import model.Movie;

public class DVDCorner {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);		
		List<DVD> dvdStore = new ArrayList<DVD>();
		
		while (true) {	        
			System.out.println("DVD Record System");
			System.out.println("Please enter command: [c | s | a | l | g | u | r | d | x]");
			System.out.println("c = create DVD,  s = show DVD,  a = accept donation of DVD,  l = lend out a DVD, ");
			System.out.println("g = get back a returned DVD, u = undo,  r = redo,  d = display undo/redo list,  x = exit system");
			System.out.println();		
		
			String command = scanner.next();
			if (command.length() > 1) {
				System.err.println("Command invalid, please enter again.");
			}
			
			switch (command) {
			case "a":
				//Accept Donation
				try {
					System.out.println("Enter ID:");
					int dvdId = scanner.nextInt();
					boolean isDvdExist = false;
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == dvdId) {
							isDvdExist = true;
							break;
						}
					}
					if (!isDvdExist) {
						throw new Exception();
					}
					System.out.println("Enter number of copies donated:");
					int copiesDonated = scanner.nextInt();
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == dvdId) {							
							dvd.setNumAvailable(dvd.getNumAvailable() + copiesDonated);
							System.out.println("Donation accepted: " + dvd.getTitle());
							System.out.println("Number of available copies: " + dvd.getNumAvailable());
							break;
						}
					}
					
				}catch(Exception e) {
					System.out.println("input invalid, please start again");
					System.out.println();
				}				
				break;				
			case "c":
				//Create Record
				System.out.println("Enter DVD type (mo=movie/mv=MV)");
				String dvdType = scanner.next();

				if (dvdType.equals("mo")) {
					System.out.println("Enter id, title, length, number of available copies, director");
				}else if (dvdType.equals("mv")) {
					System.out.println("Enter id, title, length, number of available copies, singer");
				}else {
					System.out.println("DVD type invalid, please start again");
					System.out.println();		
					break;
				}
				
				String dvdDetailStr = scanner.next();
				try {
					String[] dvdDetail = dvdDetailStr.split(",");
					String singer = null, director = null;
					if (dvdType.equalsIgnoreCase("mo")) {
						director = dvdDetail[4].trim();
						singer = null;
					}else {
						singer = dvdDetail[4].trim();
						director = null;
					}			
				
					boolean isCopyExist = false;
					for (DVD dvdInStore : dvdStore) {
						if (dvdInStore.getDvdID() == Integer.valueOf(dvdDetail[0])) {
							dvdInStore.setNumAvailable(dvdInStore.getNumAvailable() + Integer.valueOf(dvdDetail[3]));
							isCopyExist = true;
						}
					}
					if (!isCopyExist) {
						dvdStore.add(DVDFactory.newDVD(dvdType, Integer.valueOf(dvdDetail[0]), dvdDetail[1].trim(), Integer.valueOf(dvdDetail[2]), Integer.valueOf(dvdDetail[3]), singer, director));
					}
					System.out.println("DVD record created");
				}catch(Exception e) {
					System.out.println("input invalid, please start again");
					System.out.println();	
				}				
				break;
			case "g":
				//Get back a DVD
				try {
					System.out.println("Enter ID:");
					int dvdId = scanner.nextInt();
					boolean isDvdExist = false;
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == dvdId) {							
							isDvdExist = true;
							dvd.setNumAvailable(dvd.getNumAvailable() + 1);
							System.out.println("Returned: " + dvd.getTitle());
							System.out.println("Number of available copies: " + dvd.getNumAvailable());
							break;
						}
					}
					if (!isDvdExist) {
						throw new Exception();
					}					
				}catch(Exception e) {
					System.out.println("input invalid, please start again");
					System.out.println();
				}
				break;
			case "l":
				//Lend a DVD
				try {
					System.out.println("Enter ID:");
					int dvdId = scanner.nextInt();
					boolean isDvdExist = false;
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == dvdId) {							
							isDvdExist = true;
							if (dvd.getNumAvailable() == 0) {
								System.out.println("Invalid request (" + dvd.getTitle() + " has no available copy).");
								break;
							}
							dvd.setNumAvailable(dvd.getNumAvailable() - 1);
							System.out.println("Lent out: " + dvd.getTitle());
							System.out.println("Number of available copies: " + dvd.getNumAvailable());
							break;
						}
					}
					if (!isDvdExist) {
						throw new Exception();
					}					
				}catch(Exception e) {
					System.out.println("input invalid, please start again");
					System.out.println();
				}
				break;
			case "s":
				//Show record
				System.out.println("Enter ID (a to show all):");
				String dvdIDStr = scanner.next();
				System.out.println();
				
				if (dvdIDStr.equals("a")) {
					System.out.println("DVD information");
					String leftAlignFormat = "| %-5d | %-25s | %-15d | %-15d | %-25s |%n";

					System.out.format("+-------+---------------------------+-----------------+-----------------+---------------------------+%n");
					System.out.format("|  ID   |           Title           |  Length (mins)  |  No. available  |         Other Info        |%n");
					System.out.format("+-------+---------------------------+-----------------+-----------------+---------------------------+%n");
					for (DVD dvd: dvdStore) {
						String otherInfo = "";
						if (dvd instanceof Movie) {
							otherInfo = ((Movie)dvd).getDirector();
						}else if (dvd instanceof MV) {
							otherInfo = ((MV)dvd).getSinger();
						}
					    System.out.format(leftAlignFormat, dvd.getDvdID(), dvd.getTitle(), dvd.getLength(), dvd.getNumAvailable(), otherInfo);
					}
					System.out.format("+-------+---------------------------+-----------------+-----------------+---------------------------+%n");
				}else {
					try {
						int dvdID = Integer.valueOf(dvdIDStr);
						boolean isDvdExist = false;
						for (DVD dvd: dvdStore) {
							if (dvd.getDvdID() == dvdID) {							
								isDvdExist = true;
								System.out.println("DVD information");
								System.out.println("ID: " + dvd.getDvdID());
								System.out.println("Title: " + dvd.getTitle());
								System.out.println("Length: " + dvd.getLength() + " mins");
								System.out.println("Number of available copies: " + dvd.getNumAvailable());
								if (dvd instanceof Movie) {
									Movie movie = (Movie)dvd;
									System.out.println("Director: " + movie.getDirector());
								}else if (dvd instanceof MV) {
									MV mv = (MV)dvd;
									System.out.println("Singer: " + mv.getSinger());	
								}
								System.out.println("");
								break;
							}
						}
						if (!isDvdExist) {
							throw new Exception();
						}
					}catch (Exception e) {
						System.out.println("Invalid Input, please start again.");
						System.out.println();	
					}
				}
				break;
			case "x":
				System.out.println("Exiting System...");
				System.exit(0);
			}
		}
	}
}
