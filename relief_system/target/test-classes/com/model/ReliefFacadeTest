package com.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/** Test for ReliefFacade
 *
 * Tested by: Gracie Bergmann
 * team contributions:
 * <Gracie>: wrote bulk of ReleifFacade/ finished issue #42-#47
 * <Abbigail>:Fixed up Relieffacade
 *
 * AI use: lined up my asccii borders and fixing compiling errors I had
 *
+-------------------------------------------------+---------------------------------------------------------------+
 * | Test                                            | Reasoning                                                     |
 * +-------------------------------------------------+---------------------------------------------------------------+
 * | submitRequest_addsRequestToFeed                 | Facade should hand the request to the RequestFeed singleton   |
 * | createAccount_validInfo_logsUserIn              | createAccount ends by calling login, so user must be current  |
 * | createAccount_validInfo_addsUserToUserList      | New user must be findable by username/password afterward      |
 * | createAccount_nullFamilyMembers_doesNotThrow    | Checks family members are allowed to be left null             |
 * | createAccount_nullPetDescription_doesNotThrow   | Pet description is only meaningful if hasPet, so null is ok   |
 * | createAccount_nullLocation_doesNotThrow         | Checks whether location is allowed to be left null            |
 * | createAccount_nullShelterOverload_doesNotThrow  | The shelter overload should accept no shelter                 |
 * | createAccount_withShelter_storesShelter         | Shelter overload should attach the shelter to the new user    |
 * | createRequest_notLoggedIn_returnsNull           | Branch: currentUser == null                                   |
 * | createRequest_nullTypes_returnsNull             | Branch: requestTypes == null                                  |
 * | createRequest_emptyTypes_returnsNull            | Branch: requestTypes is empty                                 |
 * | createRequest_valid_returnsPendingRequest       | Happy path: new requests start PENDING with no responder      |
 * | createRequest_valid_addsToFeed                  | Created request must land in the feed                         |
 * | createRequest_nullLocation_usesUserLocation     | Branch: null location falls back to the user's location       |
 * | createRequest_givenLocation_usesGivenLocation   | Branch: supplied location is used instead of user's           |
 * | createRequest_nullComments_doesNotThrow         | Checks comments are allowed to be left null                   |
 * | login_withUnknownUsername_returnsFalse          | Branch 2: user not found -> false, nobody logged in           |
 * | login_wrongPassword_returnsFalse                | Right username with wrong password must be rejected           |
 * | login_correctCredentials_returnsTrueAndSetsUser | Branch 1: user found -> true and currentUser set              |
 * | getUnfulfilledRequests_printsRequestWithoutResponder | Requests with no responder should be listed             |
 * | getUnfulfilledRequests_skipsRequestWithResponder | Requests that already have a responder should not be listed  |
 * | restockShelter_nullShelter_doesNotThrow         | Branch: shelter == null is silently ignored                   |
 * | restockShelter_validShelter_increasesQuantity   | Branch: shelter exists, so restock is applied                 |
 * | dispatchVolunteer_pendingRequest_assignsResponder | Pending request gets the volunteer + HELP_ON_THE_WAY        |
 * | dispatchVolunteer_dispatchesOnlyOneRequest      | Method returns after the first pending request                |
 * | dispatchVolunteer_noPendingRequests_changesNothing | Branch: loop finishes with no pending request              |
 * | logout_afterLogin_clearsCurrentUser             | currentUser must be null after logout                         |
 * | logout_printsConfirmation                       | Facade prints "Logout successful"                             |
 * | logout_whenNotLoggedIn_doesNotThrow             | Logging out twice / while logged out should be harmless       |
 * +-------------------------------------------------+---------------------------------------------------------------+
 */


    public class ReliefFacadeTest {

        private ReliefFacade facade;

        @BeforeEach
        public void setUp()
        {
            facade = new ReliefFacade();
        }
        @Test
        public void login_withUnknownUsername_returnsFalse(){
            boolean result = facade.login("noSuchUser", "WrongPassword123");
            assertFalse(result);
            assertNull(facade.getCurrentUser());
        }
        
        public boolean login(String username, String password) {
        User user = userList.getUser(username, password);
        if (user != null) {       // branch 1: user found -> true
            currentUser = user;
            return true;
        }
        return false;             // branch 2: user not found -> false
    }

        private Location makeLocation() {
            return new Location();
        }

        private String uniqueName() {
            return "testuser_" + UUID.randomUUID();
        }

        private ArrayList<RequestType> oneType() {
            ArrayList<RequestType> types = new ArrayList<>();
            types.add(RequestType.values()[0]);
            return types;
        }

        private Request makeRequest(String address) {
            return facade.createRequest(oneType(), makeLocation(), address, 3, new ArrayList<String>());
        }

        private int countPending() {
            int count = 0;
            for (Request r : RequestFeed.getInstance().getRequests()) {
                if (r.getStatus() == RequestStatus.PENDING) count++;
            }
            return count;
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

        //submitRequest
    @Test
        public void submitRequest_addsRequestToFeed() {
            User user = registerAndLogin();
            Request request = new Request(null, RequestStatus.PENDING, makeLocation(), "submit-" + UUID.randomUUID(),
                    3, new ArrayList<String>(), user, null, oneType());
            facade.submitRequest(request);
            assertTrue(RequestFeed.getInstance().getRequests().contains(request));
        }

        //BIG: createAccount 
        @Test
        public void createAccount_validInfo_logsUserIn() {
            String username = uniqueName();
            facade.createAccount(username, "Test", "User", makeLocation(), "803-555-0100",
                    "123 Main St", "pw12345", new ArrayList<FamilyMember>(), false, 20, "");
            assertNotNull(facade.getCurrentUser());
        }

        @Test
        public void createAccount_validInfo_addsUserToUserList() {
            String username = uniqueName();
            facade.createAccount(username, "Test", "User", makeLocation(), "803-555-0100",
                    "123 Main St", "pw12345", new ArrayList<FamilyMember>(), false, 20, "");
            assertNotNull(UserList.getInstance().getUser(username, "pw12345"));
        }

        @Test
        public void createAccount_nullFamilyMembers_doesNotThrow() {
            assertDoesNotThrow(() -> facade.createAccount(uniqueName(), "Test", "User", makeLocation(),
                    "803-555-0100", "123 Main St", "pw12345", (ArrayList<FamilyMember>) null, false, 20, ""));
        }

        @Test
        public void createAccount_nullPetDescription_doesNotThrow() {
            assertDoesNotThrow(() -> facade.createAccount(uniqueName(), "Test", "User", makeLocation(),
                    "803-555-0100", "123 Main St", "pw12345", new ArrayList<FamilyMember>(), false, 20, null));
        }

        @Test
        public void createAccount_nullLocation_doesNotThrow() {
            assertDoesNotThrow(() -> facade.createAccount(uniqueName(), "Test", "User", null,
                    "803-555-0100", "123 Main St", "pw12345", new ArrayList<FamilyMember>(), false, 20, ""));
        }

        @Test
        public void createAccount_nullShelterOverload_doesNotThrow() {
            assertDoesNotThrow(() -> facade.createAccount(uniqueName(), "Test", "User", makeLocation(),
                    "803-555-0100", "123 Main St", "pw12345", (Shelter) null,
                    new ArrayList<FamilyMember>(), false, 20, ""));
        }


        @Test
        public void createAccount_withShelter_storesShelter() {
            ArrayList<Shelter> shelters = facade.getShelters();
            org.junit.jupiter.api.Assumptions.assumeFalse(shelters.isEmpty(), "No shelters loaded");
            Shelter shelter = shelters.get(0);
            facade.createAccount(uniqueName(), "Test", "User", makeLocation(), "803-555-0100",
                    "123 Main St", "pw12345", shelter, new ArrayList<FamilyMember>(), false, 20, "");
            assertSame(shelter, facade.getCurrentUser().getShelter());
        }

        //createRequest 
        @Test
        public void createRequest_notLoggedIn_returnsNull() {
            facade.logout();
            assertNull(makeRequest("nologin-" + UUID.randomUUID()));
        }

        @Test
        public void createRequest_nullTypes_returnsNull() {
            registerAndLogin();
            assertNull(facade.createRequest(null, makeLocation(), "addr", 3, new ArrayList<String>()));
        }

        @Test
        public void createRequest_emptyTypes_returnsNull() {
            registerAndLogin();
            assertNull(facade.createRequest(new ArrayList<RequestType>(), makeLocation(), "addr", 3,
                    new ArrayList<String>()));
        }

        @Test
        public void createRequest_valid_returnsPendingRequest() {
            registerAndLogin();
            Request request = makeRequest("valid-" + UUID.randomUUID());
            assertNotNull(request);
            assertEquals(RequestStatus.PENDING, request.getStatus());
            assertNull(request.getResponder());
        }

        @Test
        public void createRequest_valid_addsToFeed() {
            registerAndLogin();
            Request request = makeRequest("feed-" + UUID.randomUUID());
            assertTrue(RequestFeed.getInstance().getRequests().contains(request));
        }

        @Test
        public void createRequest_nullLocation_usesUserLocation() {
            User user = registerAndLogin();
            Request request = facade.createRequest(oneType(), null, "nullloc-" + UUID.randomUUID(), 3,
                    new ArrayList<String>());

            assertNotNull(request);
            assertSame(user.getLocation(), request.getLocation());
        }

        @Test
        public void createRequest_givenLocation_usesGivenLocation() {
            registerAndLogin();
            Location given = makeLocation();
            Request request = facade.createRequest(oneType(), given, "givenloc-" + UUID.randomUUID(), 3,
                    new ArrayList<String>());
            assertSame(given, request.getLocation());
        }

        @Test
        public void createRequest_nullComments_doesNotThrow() {
            registerAndLogin();
            assertDoesNotThrow(() -> facade.createRequest(oneType(), makeLocation(),
                    "nullcomments-" + UUID.randomUUID(), 3, null));
        }


        //  login
        @Test
        public void login_withUnknownUsername_returnsFalse() {
            boolean result = facade.login("noSuchUser", "WrongPassword123");
            assertFalse(result);
            assertNull(facade.getCurrentUser());
        }

        @Test
        public void login_wrongPassword_returnsFalse() {
            String username = uniqueName();
            facade.createAccount(username, "Test", "User", makeLocation(), "803-555-0100",
                    "123 Main St", "pw12345", new ArrayList<FamilyMember>(), false, 20, "");
            facade.logout();

            assertFalse(facade.login(username, "notThePassword"));
            assertNull(facade.getCurrentUser());
        }

        @Test
        public void login_correctCredentials_returnsTrueAndSetsUser() {
            String username = uniqueName();
            facade.createAccount(username, "Test", "User", makeLocation(), "803-555-0100",
                    "123 Main St", "pw12345", new ArrayList<FamilyMember>(), false, 20, "");
            facade.logout();
            assertTrue(facade.login(username, "pw12345"));
            assertNotNull(facade.getCurrentUser());
        }

        // getUnfulfilledRequests
        @Test
        public void getUnfulfilledRequests_printsRequestWithoutResponder() {
            registerAndLogin();
            Request unfulfilled = makeRequest("unfulfilled-" + UUID.randomUUID());
            String output = captureOutput(() -> facade.getUnfulfilledRequests());

            assertTrue(output.contains("Unfulfilled Requests:"));
            assertTrue(output.contains(unfulfilled.toString()));
        }

        @Test
        public void getUnfulfilledRequests_skipsRequestWithResponder() {
            User user = registerAndLogin();
            Request fulfilled = makeRequest("fulfilled-" + UUID.randomUUID());
            fulfilled.setResponder(user);

            String output = captureOutput(() -> facade.getUnfulfilledRequests());

            assertFalse(output.contains(fulfilled.toString()));
        }
        // restockShelter
        @Test
        public void restockShelter_nullShelter_doesNotThrow() {
            assertDoesNotThrow(() -> facade.restockShelter(null, ResourceType.values()[0], 5));
        }

        @Test
        public void restockShelter_validShelter_increasesQuantity() {
            ArrayList<Shelter> shelters = facade.getShelters();
            org.junit.jupiter.api.Assumptions.assumeFalse(shelters.isEmpty(), "No shelters loaded");
            Shelter shelter = shelters.get(0);
            ResourceType type = ResourceType.values()[0];
            int before = quantityOf(shelter, type);
            facade.restockShelter(shelter, type, 5);

            assertEquals(before + 5, quantityOf(shelter, type));
        }
        //dispatchVolunteer
        @Test
        public void dispatchVolunteer_pendingRequest_assignsResponder() {
            User volunteer = registerAndLogin();
            makeRequest("dispatch-" + UUID.randomUUID());
        // dispatchVolunteer picks the FIRST pending request in the feed
            Request firstPending = null;
            for (Request r : RequestFeed.getInstance().getRequests()) {
                if (r.getStatus() == RequestStatus.PENDING) {
                    firstPending = r;
                    break;
                }
            }
            assertNotNull(firstPending);

            facade.dispatchVolunteer(volunteer);

            assertSame(volunteer, firstPending.getResponder());
            assertEquals(RequestStatus.HELP_ON_THE_WAY, firstPending.getStatus());
        }

        @Test
        public void dispatchVolunteer_dispatchesOnlyOneRequest() {
            User volunteer = registerAndLogin();
            makeRequest("one-" + UUID.randomUUID());
            makeRequest("two-" + UUID.randomUUID());

            int before = countPending();
            facade.dispatchVolunteer(volunteer);

            assertEquals(before - 1, countPending());
        }

        @Test
        public void dispatchVolunteer_noPendingRequests_changesNothing() {
            User volunteer = registerAndLogin();
            // clear out every pending request so the loop finds nothing
            for (Request r : RequestFeed.getInstance().getRequests()) {
                if (r.getStatus() == RequestStatus.PENDING) {
                    r.updateStatus(RequestStatus.HELP_ON_THE_WAY);
                }
            }

            assertDoesNotThrow(() -> facade.dispatchVolunteer(volunteer));
            assertEquals(0, countPending());
        }

        //  logout
        @Test
        public void logout_afterLogin_clearsCurrentUser() {
            registerAndLogin();
            assertNotNull(facade.getCurrentUser());
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
        public void logout_whenNotLoggedIn_doesNotThrow() {
            facade.logout();
            assertDoesNotThrow(() -> facade.logout());
            assertNull(facade.getCurrentUser());
        }
}



    

