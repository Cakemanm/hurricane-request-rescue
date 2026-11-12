package com.model;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.location.Address;

/** Test for ReliefFacade
 *
 * Tested by: Gracie Bergmann
 * team contributions:
 * <Gracie>: wrote bulk of ReleifFacade/ finished issue #42-#47
 * <Abbigail>:Fixed up Relieffacade
 *
 * AI use: lined up my asccii borders and fixing compiling errors I had
 *
/*
+--------------------------------------------+--------------------------------------------------------------------+
| Test                                       | Reasoning                                                          |
+--------------------------------------------+--------------------------------------------------------------------+
| submitRequest: adds to feed                | -request must appear in the shared feed for volunteers             |
| submitRequest: sets requester              | -request should be tied to the user who submitted it               |
| submitRequest: adds comment                | -user's note should be saved on the request                        |
+--------------------------------------------+--------------------------------------------------------------------+
| createAccount: returns user                | -valid info should produce a real user                             |
| createAccount: logs user in                | -a new account becomes the current user                            |
| createAccount: adds to user list           | -account must be stored so they can log in later                   |
| createAccount: null family members         | -null becomes an empty list instead of crashing                    |
| createAccount: null shelter                | -a user with no shelter is simply unassigned                       |
| createAccount: with shelter                | -a provided shelter is assigned to the user                        |
| createAccount: null address                | -address is optional, so the user is still created                 |
| createAccount: null pet description        | -no pet means no description is needed                             |
+--------------------------------------------+--------------------------------------------------------------------+
| createRequest: not logged in               | -no current user to attach to, so it returns null                  |
| createRequest: null types                  | -a request needs at least one type of help                         |
| createRequest: empty types                 | -same as null, at least one type is required                       |
| createRequest: starts pending              | -nobody has responded yet, so status is PENDING                    |
| createRequest: no responder                | -no volunteer is assigned until someone is dispatched              |
| createRequest: adds to feed                | -volunteers can only see requests that are in the feed             |
| createRequest: uses given address          | -help should go to the address the user entered                    |
| createRequest: null comments               | -comments are optional, so null shouldn't crash                    |
| createRequest: null address                | -address is optional, so the request is still created              |
+--------------------------------------------+--------------------------------------------------------------------+
| login: unknown username                    | -a user that doesn't exist can't log in                            |
| login: unknown username, no user set       | -a failed login shouldn't leave anyone logged in                   |
| login: wrong password                      | -the right username with a bad password must be rejected           |
| login: correct credentials                 | -valid username and password should return true                    |
| login: sets current user                   | -a successful login should make them the current user              |
+--------------------------------------------+--------------------------------------------------------------------+
| getUnfulfilled: prints no responder        | -requests still waiting for help should be listed                  |
| getUnfulfilled: skips responder            | -requests that already have a volunteer shouldn't show             |
+--------------------------------------------+--------------------------------------------------------------------+
| restockShelter: null shelter               | -should do nothing instead of throwing an exception                |
| restockShelter: new resource               | -a resource the shelter lacks should be added                      |
| restockShelter: existing resource          | -restocking should add to the current quantity                     |
| restockShelter: negative quantity          | -a shelter's supply should never drop below zero                   |
+--------------------------------------------+--------------------------------------------------------------------+
| dispatch: pending request                  | -a pending request can be taken, so it returns true                |
| dispatch: sets help on the way             | -status should show a volunteer is coming                          |
| dispatch: assigns responder                | -the request should know which volunteer is responding             |
| dispatch: already dispatched               | -two volunteers shouldn't be sent to the same request              |
+--------------------------------------------+--------------------------------------------------------------------+
| logout: clears current user                | -after logging out nobody should be logged in                      |
| logout: prints confirmation                | -the user should see that the logout worked                        |
| logout: when not logged in                 | -logging out twice shouldn't cause an error                        |
+--------------------------------------------+--------------------------------------------------------------------+
| matchesSkill: has required skill           | -a volunteer with the needed skill matches the request             |
| matchesSkill: no skills                    | -a volunteer with no skills shouldn't match                        |
| matchesSkill: not logged in                | -with no user it should return false, not crash                    |
+--------------------------------------------+--------------------------------------------------------------------+
 */


    public class ReliefFacadeTest {

        private ReliefFacade facade;

        @Before
        public void setUp()
        {
            facade = new ReliefFacade();
        }
         private Address makeAddress(String street) {
        return new Address(street, "Columbia", "SC", "29201");
    }

    private String uniqueName() {
        return "testuser_" + UUID.randomUUID();
    }

    private ArrayList<RequestType> oneType() {
        ArrayList<RequestType> types = new ArrayList<>();
        types.add(RequestType.values()[0]);
        return types;
    }

    /** Creates an account through the facade (which also logs it in) and returns that user. */
    private User registerAndLogin() {
        return facade.createAccount(uniqueName(), "Test", "User", "803-555-0100",
                makeAddress("123 Main St"), "pw12345", null,
                new ArrayList<FamilyMember>(), false, 20, "");
    }

    /** Creates a request as the current user. Call registerAndLogin() first. */
    private Request makeRequest(String street) {
        return facade.createRequest(oneType(), makeAddress(street), 3, new ArrayList<String>());
  }

    private int quantityOf(Shelter shelter, ResourceType type) {
        for (Resource resource : shelter.getResources()) {
            if (resource.getResourceType() == type) return resource.getQuantity();
        }
        return 0;
    }

    private Shelter makeEmptyShelter() {
        return new Shelter("Test Shelter " + UUID.randomUUID(), true, new ArrayList<Resource>(),
                makeAddress("1 Shelter Way"), new ArrayList<ShelterType>(), 0, 10, UUID.randomUUID());
    }

    private String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }
 
 
    // submitRequest
    private Request buildRequestFor(User requester, String street) {
        return new Request(null, RequestStatus.PENDING, makeAddress(street), 3,
                new ArrayList<String>(), requester, null, oneType());
    }

    @Test
    public void submitRequest_addsRequestToFeed() {
        User user = registerAndLogin();
        Request request = buildRequestFor(user, "submit-" + UUID.randomUUID());

        facade.submitRequest(user, request, "need water");

        assertTrue(RequestFeed.getInstance().getRequests().contains(request));
    }

    @Test
    public void submitRequest_setsRequester() {
        User user = registerAndLogin();
        Request request = buildRequestFor(null, "requester-" + UUID.randomUUID());

        facade.submitRequest(user, request, "need water");

        assertSame(user, request.getRequester());
    }

    @Test
    public void submitRequest_addsComment() {
        User user = registerAndLogin();
        Request request = buildRequestFor(user, "comment-" + UUID.randomUUID());

        facade.submitRequest(user, request, "need water");

        assertTrue(request.getComments().contains("need water"));
    }

    // ------------------------------------------------------------------
    // createAccount (what is allowed to be null)
    // ------------------------------------------------------------------

    @Test
    public void createAccount_validInfo_returnsUser() {
        User user = registerAndLogin();
        assertNotNull(user);
    }

    @Test
    public void createAccount_validInfo_logsUserIn() {
        User user = registerAndLogin();
        assertEquals(user.getUsername(), facade.getCurrentUser().getUsername());
    }

    @Test
    public void createAccount_validInfo_addsUserToUserList() {
        User user = registerAndLogin();
        assertNotNull(UserList.getInstance().getUser(user.getUsername(), "pw12345"));
    }

    @Test
    public void createAccount_nullFamilyMembers_createsEmptyList() {
        User user = facade.createAccount(uniqueName(), "Test", "User", "803-555-0100",
                makeAddress("123 Main St"), "pw12345", null, null, false, 20, "");
        assertEquals(0, user.getFamilyMembers().size());
    }

    @Test
    public void createAccount_nullShelter_leavesShelterUnassigned() {
        User user = registerAndLogin();
        assertNull(user.getShelter());
    }

    @Test
    public void createAccount_withShelter_assignsShelter() {
        Shelter shelter = makeEmptyShelter();
        User user = facade.createAccount(uniqueName(), "Test", "User", "803-555-0100",
                makeAddress("123 Main St"), "pw12345", shelter,
                new ArrayList<FamilyMember>(), false, 20, "");
        assertSame(shelter, user.getShelter());
    }

    @Test
    public void createAccount_nullAddress_stillCreatesUser() {
        User user = facade.createAccount(uniqueName(), "Test", "User", "803-555-0100",
                null, "pw12345", null, new ArrayList<FamilyMember>(), false, 20, "");
        assertNotNull(user);
    }

    @Test
    public void createAccount_nullPetDescriptionWithNoPet_stillCreatesUser() {
        User user = facade.createAccount(uniqueName(), "Test", "User", "803-555-0100",
                makeAddress("123 Main St"), "pw12345", null,
                new ArrayList<FamilyMember>(), false, 20, null);
        assertNotNull(user);
    }
 
    // createRequest (what is allowed to be null)
    @Test
    public void createRequest_notLoggedIn_returnsNull() {
        facade.logout();
        assertNull(makeRequest("nologin-" + UUID.randomUUID()));
    }

    @Test
    public void createRequest_nullTypes_returnsNull() {
        registerAndLogin();
        assertNull(facade.createRequest(null, makeAddress("addr"), 3, new ArrayList<String>()));
    }

    @Test
    public void createRequest_emptyTypes_returnsNull() {
        registerAndLogin();
        assertNull(facade.createRequest(new ArrayList<RequestType>(), makeAddress("addr"), 3,
                new ArrayList<String>()));
    }

    @Test
    public void createRequest_valid_startsPending() {
        registerAndLogin();
        Request request = makeRequest("pending-" + UUID.randomUUID());
        assertEquals(RequestStatus.PENDING, request.getStatus());
    }

    @Test
    public void createRequest_valid_hasNoResponder() {
        registerAndLogin();
        Request request = makeRequest("noresponder-" + UUID.randomUUID());
        assertNull(request.getResponder());
    }

    @Test
    public void createRequest_valid_addsToFeed() {
        registerAndLogin();
        Request request = makeRequest("feed-" + UUID.randomUUID());
        assertTrue(RequestFeed.getInstance().getRequests().contains(request));
    }

    @Test
    public void createRequest_valid_usesGivenAddress() {
        registerAndLogin();
        Address given = makeAddress("given-" + UUID.randomUUID());
        Request request = facade.createRequest(oneType(), given, 3, new ArrayList<String>());
        assertSame(given, request.getAddress());
    }

    @Test
    public void createRequest_nullComments_stillCreatesRequest() {
        registerAndLogin();
        Request request = facade.createRequest(oneType(), makeAddress("nullcomments-" + UUID.randomUUID()),
                3, null);
        assertNotNull(request);
    }

    @Test
    public void createRequest_nullAddress_stillCreatesRequest() {
        registerAndLogin();
        Request request = facade.createRequest(oneType(), null, 3, new ArrayList<String>());
        assertNotNull(request);
    }

    // login
    @Test
    public void login_withUnknownUsername_returnsFalse() {
        boolean result = facade.login("noSuchUser", "WrongPassword123");
        assertFalse(result);
    }

    @Test
    public void login_withUnknownUsername_leavesNoCurrentUser() {
        facade.login("noSuchUser", "WrongPassword123");
        assertNull(facade.getCurrentUser());
    }

    @Test
    public void login_wrongPassword_returnsFalse() {
        User user = registerAndLogin();
        facade.logout();
        assertFalse(facade.login(user.getUsername(), "notThePassword"));
    }

    @Test
    public void login_correctCredentials_returnsTrue() {
        User user = registerAndLogin();
        facade.logout();
        assertTrue(facade.login(user.getUsername(), "pw12345"));
    }

    @Test
    public void login_correctCredentials_setsCurrentUser() {
        User user = registerAndLogin();
        facade.logout();
        facade.login(user.getUsername(), "pw12345");
        assertEquals(user.getUsername(), facade.getCurrentUser().getUsername());
    }


    // getUnfulfilledRequest
    @Test
    public void getUnfulfilledRequests_printsRequestWithoutResponder() {
        registerAndLogin();
        Request unfulfilled = makeRequest("unfulfilled-" + UUID.randomUUID());
        String output = captureOutput(() -> facade.getUnfulfilledRequests());
        assertTrue(output.contains(unfulfilled.toString()));
    }

    @Test
    public void getUnfulfilledRequests_skipsRequestWithResponder() {
        User volunteer = registerAndLogin();
        Request fulfilled = makeRequest("fulfilled-" + UUID.randomUUID());
        facade.dispatchVolunteer(fulfilled, volunteer);
        String output = captureOutput(() -> facade.getUnfulfilledRequests());
        assertFalse(output.contains(fulfilled.toString()));
    }

    // restockShelter
    @Test
    public void restockShelter_nullShelter_doesNothing() {
        facade.restockShelter(null, ResourceType.values()[0], 5);
        // reaching this line without an exception is the check
        assertTrue(true);
    }

    @Test
    public void restockShelter_newResourceType_addsResource() {
        Shelter shelter = makeEmptyShelter();
        ResourceType type = ResourceType.values()[0];
        facade.restockShelter(shelter, type, 5);
        assertEquals(5, quantityOf(shelter, type));
    }

    @Test
    public void restockShelter_existingResourceType_addsToQuantity() {
        Shelter shelter = makeEmptyShelter();
        ResourceType type = ResourceType.values()[0];
        facade.restockShelter(shelter, type, 5);
        facade.restockShelter(shelter, type, 3);
        assertEquals(8, quantityOf(shelter, type));
    }

    @Test
    public void restockShelter_negativeQuantity_doesNotGoBelowZero() {
        // Likely to FAIL: restock() doesn't check for negative amounts. Decide if that's a bug.
        Shelter shelter = makeEmptyShelter();
        ResourceType type = ResourceType.values()[0];
        facade.restockShelter(shelter, type, -5);
        assertTrue(quantityOf(shelter, type) >= 0);
    }
     // dispatchVolunteer
    @Test
    public void dispatchVolunteer_pendingRequest_returnsTrue() {
        User volunteer = registerAndLogin();
        Request request = makeRequest("dispatch1-" + UUID.randomUUID());
        assertTrue(facade.dispatchVolunteer(request, volunteer));
    }

    @Test
    public void dispatchVolunteer_pendingRequest_setsHelpOnTheWay() {
        User volunteer = registerAndLogin();
        Request request = makeRequest("dispatch2-" + UUID.randomUUID());
        facade.dispatchVolunteer(request, volunteer);
        assertEquals(RequestStatus.HELP_ON_THE_WAY, request.getStatus());
    }

    @Test
    public void dispatchVolunteer_pendingRequest_assignsResponder() {
        User volunteer = registerAndLogin();
        Request request = makeRequest("dispatch3-" + UUID.randomUUID());

        facade.dispatchVolunteer(request, volunteer);

        assertSame(volunteer, request.getResponder());
    }

    @Test
    public void dispatchVolunteer_alreadyDispatchedRequest_returnsFalse() {
        User volunteer = registerAndLogin();
        Request request = makeRequest("dispatch4-" + UUID.randomUUID());
        facade.dispatchVolunteer(request, volunteer);

        assertFalse(facade.dispatchVolunteer(request, volunteer));
    }
    // logout
    @Test
    public void logout_afterLogin_clearsCurrentUser() {
        registerAndLogin();
        facade.logout();
        assertNull(facade.getCurrentUser());
    }
 
    @Test
    public void logout_printsConfirmation() {
        registerAndLogin();
        String output = captureOutput(() -> facade.logout());
        assertTrue(output.contains("Logout successful"));
    }
 
    @Test
    public void logout_whenNotLoggedIn_leavesNoCurrentUser() {
        facade.logout();
        facade.logout();
        assertNull(facade.getCurrentUser());
    }
 
    // matchesSkill
    @Test
    public void matchesSkill_userHasRequiredSkill_returnsTrue() {
        User volunteer = registerAndLogin();
        Request request = makeRequest("skill1-" + UUID.randomUUID());
        volunteer.getSkills().add(RequestType.values()[0].skill);
        assertTrue(facade.matchesSkill(request));
    }

    @Test
    public void matchesSkill_userHasNoSkills_returnsFalse() {
        registerAndLogin();
        Request request = makeRequest("skill2-" + UUID.randomUUID());
        assertFalse(facade.matchesSkill(request));
    }

    @Test
    public void matchesSkill_notLoggedIn_returnsFalse() {
        // Likely to FAIL: matchesSkill uses currentUser without a null check (NullPointerException).
        registerAndLogin();
        Request request = makeRequest("skill3-" + UUID.randomUUID());
        facade.logout();

        assertFalse(facade.matchesSkill(request));
    }
}