package main;

import console_ui.MainUI;
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

    /** A debugging mode where files are not saved if true. False by default */
    private static final boolean DEBUG_FILE_MODE = true;

    /**
     * Start the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        UserRepo myUsers;
        Calendar myCalendar;
        Serializer userSer = new Serializer(USERS_REPO_FILE);
        Serializer calSer = new Serializer(CALENDAR_FILE);

        //read in serialized objects
        if(!DEBUG_FILE_MODE) {
            myCalendar = (Calendar) calSer.read();
            myUsers = (UserRepo) userSer.read();
        } else {
            GenerateTestData gen = new GenerateTestData();
            myUsers = gen.getUserRepo();
            myCalendar = gen.getCalendar();
        }

        //start the UI
        System.out.println("Welcome to AuctionCentral");
        MainUI ui = new MainUI(myUsers, myCalendar);
        ui.start();

        //save serialized objects when UI is done
            calSer.write(myCalendar);
            userSer.write(myUsers);


    }


}
