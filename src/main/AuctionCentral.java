package main;

import console_ui.MainUI;
import gui.MainGUI;
import model.*;
import test.GenerateTestData;

import java.io.*;

/**
 * This class is the starting point for the AuctionCentral program and is responsible for storing and saving the
 * serialized objects.
 * @author Simon DeMartini
 * @version Nov 7 2016
 */
public class AuctionCentral {

    /** The file location for the users repo when serialized."*/
    private static final File USERS_REPO_FILE = new File("./users.ser");

    /** The file location for the users repo when serialized."*/
    private static final File CALENDAR_FILE= new File("./cal.ser");

    /** A flag to decide whether to read files or generate test data */
    private static final boolean READ_FILE_MODE = false;

    /** A flag to decide whether to save files or not */
    private static final boolean SAVE_FILE_MODE = true;

    /** The serializers */
    private static Serializer userSer, calSer;

    /**
     * Start the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        UserRepo myUsers;
        Calendar myCalendar;
        userSer = new Serializer(USERS_REPO_FILE);
        calSer = new Serializer(CALENDAR_FILE);

        //read in serialized objects
        if(READ_FILE_MODE) {
            System.out.println("Reading data from disk...");
            myCalendar = (Calendar) calSer.read();
            myUsers = (UserRepo) userSer.read();
        } else { //generate sample data
            System.out.println("Generating test data...");
            GenerateTestData gen = new GenerateTestData();
            myUsers = gen.getUserRepo();
            myCalendar = gen.getCalendar();
        }

        //start the UI
        //System.out.println("Welcome to AuctionCentral");
        MainGUI ui = new MainGUI(myUsers, myCalendar);
        ui.start();
    }

    /**
     * A safe way to exit and save the files
     * @param theCalendar the calendar to save
     * @param theUsers the usersrepo to save
     */
    public static void saveData(Calendar theCalendar, UserRepo theUsers) {
        //save serialized objects when UI is done
        if(SAVE_FILE_MODE) {
            calSer.write(theCalendar);
            userSer.write(theUsers);
            System.out.println("Files saved.");
        } else {
            System.out.println("No files saved.");
        }
    }
}
