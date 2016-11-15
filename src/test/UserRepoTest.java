package test;

import model.Bidder;
import model.Contact;
import model.Staff;
import model.UserRepo;

import static org.junit.Assert.*;

/**
 * This is a JUnit test class for the UserRep
 * @author Simon DeMartini
 * @version Nov 13 2016
 */
public class UserRepoTest {

    private UserRepo repo;
    private Bidder b1;
    private Staff s1;
    private Contact c1;

    @org.junit.Before
    public void setUp() throws Exception {
        repo = new UserRepo();

        b1 = new Bidder("Jane Smith",
                "janesmith",
                "555-555-5555",
                "Somewhere",
                "email@somewhere.com");

        s1 = new Staff("Bobby Joe",
                "bj");

        c1 = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");

    }

    @org.junit.Test
    public void registerUserFirstOfAUsername() throws Exception {
        assertTrue(repo.registerUser(s1));
    }

    @org.junit.Test
    public void registerUserSecondOfAUsername() throws Exception {
        Staff s2 = new Staff("Bob James",
                "bj");
        repo.registerUser(s1);
        assertFalse(repo.registerUser(s2));
    }

    @org.junit.Test
    public void registerUserAllTypes() throws Exception {
        assertTrue(repo.registerUser(b1));
        assertTrue(repo.registerUser(s1));
        assertTrue(repo.registerUser(c1));
    }

    @org.junit.Test
    public void loginUserExists() throws Exception {
        repo.registerUser(s1);
        repo.registerUser(b1);
        repo.registerUser(c1);
        assertEquals(s1, repo.loginUser("bj"));
    }

    @org.junit.Test
    public void loginUserDoesNotExist() throws Exception {
        assertEquals(null, repo.loginUser("bj"));
    }

}