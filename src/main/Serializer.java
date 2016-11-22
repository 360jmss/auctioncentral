package main;

import model.Calendar;
import model.UserRepo;

import java.io.*;

/**
 * This class is responsible for serializing all the data
 * @author Simon DeMartini
 */
public class Serializer {

    /** The file to write/read */
    private File myFile;

    /** THe UserRepo */
    private UserRepo myUsers;

    /** The Calendar */
    private Calendar myCalendar;

    /**
     * Create a serializable object with the location of a config file.
     * @param theFile a file location
     */
    public Serializer(File theFile) {
        myFile = theFile;
    }

    /**
     * Read the object
     */
    public Object read() {
        Object obj = null;
        if(myFile.isFile()) {
            try {
                obj = readFile(myFile);
            } catch ( Exception e) {
                System.out.println("Yeah something went wrong reading a file. ");
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * Write the write theObject to the file
     */
    public void write(Object theObject) {

        try {
            writeFile(theObject, myFile);
        } catch ( Exception e) {
            System.out.println("Yeah something went wrong writing the file. ");
            myFile.delete();
            e.printStackTrace();
        }
    }

    /**
     * Read in a serialized object from the disk
     * @param theFile the file to read
     * @return a generic Object
     */
    private static Object readFile(File theFile) {
        //https://www.tutorialspoint.com/java/java_serialization.htm
        Object obj;
        try {
            FileInputStream fileIn = new FileInputStream(theFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("Class not found. Abort");
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * Write a serialized object to the disk
     * @param theObject to write
     * @param theFile to write to
     */
    private static void writeFile(Object theObject, File theFile) {
        //https://www.tutorialspoint.com/java/java_serialization.htm
        try {
            FileOutputStream fileOut = new FileOutputStream(theFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theObject);
            out.close();
            fileOut.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
