package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import factory.DVDFactory;
import model.Command;
import model.DVD;
import model.MV;
import model.Movie;

public class DVDCorner {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);		
		List<DVD> dvdStore = new ArrayList<DVD>();
		List<Command> undoList = new ArrayList<Command>();
		List<Command> redoList = new ArrayList<Command>();
		
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
							Command cmd = new Command("Accept", dvd.getDvdID(), dvd.getTitle(), copiesDonated, null, null, 0);
							undoList.add(cmd);
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
				scanner.nextLine();
				String dvdDetailStr = scanner.nextLine();
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
							Command cmd = new Command("Accept", dvdInStore.getDvdID(), dvdInStore.getTitle(), Integer.valueOf(dvdDetail[3]), null, null, 0);
							undoList.add(cmd);
							isCopyExist = true;
							break;
						}
					}
					if (!isCopyExist) {
						dvdStore.add(DVDFactory.newDVD(dvdType, Integer.valueOf(dvdDetail[0].trim()), dvdDetail[1].trim(), Integer.valueOf(dvdDetail[2].trim()), Integer.valueOf(dvdDetail[3].trim()), singer, director));
						Command cmd = new Command("Create", Integer.valueOf(dvdDetail[0].trim()), dvdDetail[1].trim(), null, singer, director, Integer.valueOf(dvdDetail[2].trim()));
						undoList.add(cmd);
					}
					System.out.println("DVD record created");
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("input invalid, please start again");
					System.out.println();	
				}				
				break;
			case "d":
				System.out.println();
				System.out.println("Undo List:");
				if (undoList.size() == 0) {
					System.out.println("Empty");
				}
				for (Command cmd: undoList) {
					System.out.println(cmd.toString());
				}
				System.out.println();
				System.out.println("Redo List:");
				if (redoList.size() == 0) {
					System.out.println("Empty");
				}
				for (Command cmd: redoList) {
					System.out.println(cmd.toString());
				}
				System.out.println();	
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
							Command cmd = new Command("Get back", dvd.getDvdID(), dvd.getTitle(), 1, null, null, 0);
							undoList.add(cmd);
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
							Command cmd = new Command("Lend", dvd.getDvdID(), dvd.getTitle(), 1, null, null, 0);
							undoList.add(cmd);
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
			case "r":
				Command redoCmd = redoList.get(redoList.size() - 1);
				if (redoCmd.getAction().equals("Create")) {
					boolean isCopyExist = false;
					for (DVD dvdInStore : dvdStore) {
						if (dvdInStore.getDvdID() == redoCmd.getDvdId()) {
							System.out.println("Dvd with ID already exist, cannot redo command.");
							isCopyExist = true;
							break;
						}
					}
					String dvdType2 = "";
					if (redoCmd.getSinger() != null && !redoCmd.getSinger().isEmpty()) {
						dvdType2 = "mv";
					}else {
						dvdType2 = "mo";
					}
					if (!isCopyExist) {
						dvdStore.add(DVDFactory.newDVD(dvdType2, redoCmd.getDvdId(), redoCmd.getTitle(), redoCmd.getLength(), redoCmd.getCopies(), redoCmd.getSinger(), redoCmd.getDirector()));
					}
				}else if (redoCmd.getAction().equals("Accept")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == redoCmd.getDvdId()) {
							dvd.setNumAvailable(dvd.getNumAvailable() + redoCmd.getCopies());
							break;
						}
					}
				}else if (redoCmd.getAction().equals("Lend")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == redoCmd.getDvdId()) {
							dvd.setNumAvailable(dvd.getNumAvailable() - 1);
							break;
						}
					}
				}else if (redoCmd.getAction().equals("Get back")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == redoCmd.getDvdId()) {
							dvd.setNumAvailable(dvd.getNumAvailable() + 1);
							break;
						}
					}
				}
				redoList.remove(redoCmd);
				System.out.println("redo completed.");
				System.out.println();
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
			case "u":
				Command undoCmd = undoList.get(undoList.size() - 1);
				if (undoCmd.getAction().equals("Create")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == undoCmd.getDvdId()) {
							dvdStore.remove(dvd);
							break;
						}
					}
				}else if (undoCmd.getAction().equals("Accept")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == undoCmd.getDvdId()) {
							dvd.setNumAvailable(dvd.getNumAvailable() - undoCmd.getCopies());
							break;
						}
					}
				}else if (undoCmd.getAction().equals("Lend")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == undoCmd.getDvdId()) {
							dvd.setNumAvailable(dvd.getNumAvailable() + 1);
							break;
						}
					}
				}else if (undoCmd.getAction().equals("Get back")) {
					for (DVD dvd: dvdStore) {
						if (dvd.getDvdID() == undoCmd.getDvdId()) {
							dvd.setNumAvailable(dvd.getNumAvailable() - 1);
							break;
						}
					}
				}
				redoList.add(undoCmd);
				undoList.remove(undoCmd);
				System.out.println("undo completed.");
				System.out.println();
				break;
			case "x":
				System.out.println("Exiting System...");
				System.exit(0);
			}
		}
	}
}
