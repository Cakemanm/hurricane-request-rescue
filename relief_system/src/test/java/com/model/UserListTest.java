package com.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for UserList.addUser. The last test also checks that a saved user loads back
 * through DataLoader (DataWriter/DataLoader round trip).
 *
 * Tested by: Jake McNulty
 * AI use: comments and table of test cases genereted by AI. First draft of extended
 *
+--------------------------------------------+--------------------------------------------------------------------+
| Test                                       | Reasoning                                                          |
+--------------------------------------------+--------------------------------------------------------------------+
| addUser: adds one user                     | -a valid user should be added                                      |
| addUser: found by username / id /          | -login and other classes look users up these ways                  |
|          credentials                       |                                                                    |
| addUser: two different users               | -adding a second user shouldn't replace the first                  |
+--------------------------------------------+--------------------------------------------------------------------+
| addUser: null user                         | -a null in the list breaks every later lookup, so it must be       |
|                                            |  ignored                                                           |
| addUser: duplicate username                | -two accounts with one username make login ambiguous               |
+--------------------------------------------+--------------------------------------------------------------------+
| addUser: save, then reload                 | -a saved user must load back from Users.json                       |
+--------------------------------------------+--------------------------------------------------------------------+
 */
public class UserListTest {

    private UserList userList;
    private User michael;

    @Before
    public void setUp() {
        TestFixtures.clearAllData();
        userList = UserList.getInstance();
        michael = TestFixtures.michaelCarter();
    }

    @After
    public void tearDown() {
        TestFixtures.restoreAllData();
    }

    // -- normal cases

    @Test
    public void addUser_validUser_increasesListSizeByOne() {
        userList.addUser(michael);
        assertEquals(1, userList.getUsers().size());
    }

    @Test
    public void addUser_validUser_canBeFoundByUsername() {
        userList.addUser(michael);
        assertSame(michael, userList.getUser("sophiesdad"));
    }

    @Test
    public void addUser_validUser_canBeFoundById() {
        userList.addUser(michael);
        assertSame(michael, userList.getUserById(michael.getID()));
    }

    @Test
    public void addUser_validUser_canBeFoundWithCorrectCredentials() {
        userList.addUser(michael);
        assertSame(michael, userList.getUser("sophiesdad", "Buddy&Sophie09"));
    }

    @Test
    public void addUser_twoDifferentUsers_bothAreStored() {
        userList.addUser(michael);
        userList.addUser(TestFixtures.rosaDelgado());
        assertEquals(2, userList.getUsers().size());
    }

    // -- error cases

    @Test
    public void addUser_nullUser_isIgnored() {
        userList.addUser(null);
        assertEquals(0, userList.getUsers().size());
    }

    @Test
    public void addUser_duplicateUsername_isRejected() {
        userList.addUser(michael);
        User impostor = new User(null, "sophiesdad", "Someone", "Else", "000-000-0000",
                TestFixtures.rosaHome(), "differentPassword", null, null, false, 30, null);
        userList.addUser(impostor);
        assertEquals(1, userList.getUsers().size());
    }

    // -- DataWriter / DataLoader round trip

    @Test
    public void addUser_afterSave_userIsInSavedFile() {
        userList.addUser(michael);
        userList.save();

        boolean found = false;
        for (User loaded : DataLoader.getUsers()) {
            if ("sophiesdad".equals(loaded.getUsername()))
                found = true;
        }
        assertTrue("User saved with DataWriter should be loaded back by DataLoader", found);
    }
}
