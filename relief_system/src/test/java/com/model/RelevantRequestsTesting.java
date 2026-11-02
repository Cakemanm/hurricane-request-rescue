package com.model;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.location.Address;
import com.location.Coordinates;
import com.location.MapTools;

//IMPORTANT NOTE: The tests in this class come back unsuccessful when all three are run together, 
//but it's because the combined amount that they call MapTools methods overwhelms the system.
//They should come back successful when run seperately.

/*
+-------------------------------------+--------------------------------------------------------------------+
| Test                                | Reasoning                                                          |
+-------------------------------------+--------------------------------------------------------------------+
| null user                           |   -the method should terminate immediately if the user is null     |
| correctly sorted requests           |   -the requests returned should be sorted into a "relevant" and    |
|                                     |    an "other" category based on if they match the user's abilities |
| requests ordered by distance        |   -the request list returned should be in order of proximity to    |
+-------------------------------------+--------------------------------------------------------------------+
 */

public class RelevantRequestsTesting {

    private RequestFeed feed;
    private ArrayList<Request> originalRequests;
    private User user;

    private PrintStream originalOut;
    private ByteArrayOutputStream output;

    @Before
    public void setUp() {
        feed = RequestFeed.getInstance();

        originalRequests = new ArrayList<>(feed.getRequests());
        feed.getRequests().clear();

        // This user can respond to medical requests.
        user = new User(
                UUID.randomUUID(),
                "testResponder",
                "Test",
                "User",
                "000-000-0000",
                address("100 Aquarium Wharf", "29401"),
                "testPassword",
                null,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        Skill.CAN_PROVIDE_MEDICAL_ASSISTANCE
                )),
                false,
                30,
                null
        );

        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @After
    public void tearDown() {
        if (originalOut != null) {
            System.setOut(originalOut);
        }

        if (originalRequests != null) {
            feed.getRequests().clear();
            feed.getRequests().addAll(originalRequests);
        }
    }

    private Address address(String street, String zipcode) {
        return new Address(street, "Charleston", "SC", zipcode);
    }

    private Request request(
            String comment,
            RequestStatus status,
            Address address,
            RequestType type) {

        return new Request(
                UUID.randomUUID(),
                status,
                address,
                5,
                new ArrayList<>(Arrays.asList(comment)),
                user,
                null,
                new ArrayList<>(Arrays.asList(type))
        );
    }

    private void addRequests(Request... requests) {
        feed.getRequests().addAll(Arrays.asList(requests));
    }

    // Gets the actual text printed under each heading.
    private String recommendedSection() {
        String printed = output.toString();
        int start = printed.indexOf("Recommended Requests:");
        int end = printed.indexOf("Other requests:");

        assertTrue("Recommended heading is missing", start >= 0);
        assertTrue("Other requests heading is missing", end > start);

        return printed.substring(
                start + "Recommended Requests:".length(),
                end
        );
    }

    private String otherSection() {
        String printed = output.toString();
        int start = printed.indexOf("Other requests:");

        assertTrue("Other requests heading is missing", start >= 0);

        return printed.substring(start + "Other requests:".length());
    }

    @Test
    public void nullUser_printsLoginMessage() {
        // A request ensures the method cannot silently continue
        // using a null user after printing the message.
        addRequests(request(
                "LOGIN_TEST",
                RequestStatus.PENDING,
                address("68 Calhoun Street", "29401"),
                RequestType.MEDICAL_ASSISTANCE
        ));

        feed.findRelevantRequests(null);

        assertTrue(output.toString().contains(
                "Please log in to find requests relevant to your profile."
        ));
    }

    @Test
    public void requests_arePrintedInCorrectSections() {
        Address location = address("68 Calhoun Street", "29401");

        Request relevant1 = request(
                "RELEVANT_1", RequestStatus.PENDING,
                location, RequestType.BLEEDING);

        Request relevant2 = request(
                "RELEVANT_2", RequestStatus.PENDING,
                location, RequestType.BROKEN_BONE);

        Request relevant3 = request(
                "RELEVANT_3", RequestStatus.BACKUP_REQUESTED,
                location, RequestType.BRUISED);

        Request relevant4 = request(
                "RELEVANT_4", RequestStatus.PENDING,
                location, RequestType.MEDICAL_ASSISTANCE);

        Request other1 = request(
                "OTHER_1", RequestStatus.PENDING,
                location, RequestType.FLAT_TIRE);

        Request other2 = request(
                "OTHER_2", RequestStatus.BACKUP_REQUESTED,
                location, RequestType.RESOURCE);

        Request completed1 = request(
                "COMPLETED_1", RequestStatus.COMPLETED,
                location, RequestType.BLEEDING);

        Request completed2 = request(
                "COMPLETED_2", RequestStatus.COMPLETED,
                location, RequestType.BROKEN_BONE);

        Request completed3 = request(
                "COMPLETED_3", RequestStatus.COMPLETED,
                location, RequestType.RESOURCE);

        addRequests(
                completed1, other1, relevant1,
                relevant2, completed2, relevant3,
                other2, completed3, relevant4
        );

        feed.findRelevantRequests(user);

        String recommended = recommendedSection();
        String others = otherSection();

        for (Request request : Arrays.asList(
                relevant1, relevant2, relevant3, relevant4)) {

            assertTrue(
                    "Relevant request missing from recommendations",
                    recommended.contains(request.toString())
            );
            assertFalse(
                    "Relevant request appears under Other requests",
                    others.contains(request.toString())
            );
        }

        for (Request request : Arrays.asList(other1, other2)) {
            assertTrue(
                    "Irrelevant request missing from Other requests",
                    others.contains(request.toString())
            );
            assertFalse(
                    "Irrelevant request appears in recommendations",
                    recommended.contains(request.toString())
            );
        }

        for (Request request : Arrays.asList(
                completed1, completed2, completed3)) {

            assertFalse(recommended.contains(request.toString()));
            assertFalse(others.contains(request.toString()));
        }
    }

    @Test
    public void relevantRequests_areOrderedByDrivingTime()
            throws Exception {

        Request library = request(
                "LIBRARY", RequestStatus.PENDING,
                address("68 Calhoun Street", "29401"),
                RequestType.MEDICAL_ASSISTANCE);

        Request museum = request(
                "MUSEUM", RequestStatus.PENDING,
                address("360 Meeting Street", "29403"),
                RequestType.MEDICAL_ASSISTANCE);

        Request gibbes = request(
                "GIBBES", RequestStatus.PENDING,
                address("135 Meeting Street", "29401"),
                RequestType.MEDICAL_ASSISTANCE);

        Request heyward = request(
                "HEYWARD", RequestStatus.PENDING,
                address("87 Church Street", "29401"),
                RequestType.MEDICAL_ASSISTANCE);

        Request battery = request(
                "BATTERY", RequestStatus.PENDING,
                address("21 East Battery", "29401"),
                RequestType.MEDICAL_ASSISTANCE);

        ArrayList<Request> expected = new ArrayList<>(Arrays.asList(
                library, museum, gibbes, heyward, battery
        ));

        // Ask MapTools for each driving time once.
        MapTools maps = MapTools.getInstance();
        Coordinates start = maps.getCoordinates(user.getAddress());
        Map<UUID, Double> drivingTimes = new HashMap<>();

        for (Request request : expected) {
            Coordinates destination =
                    maps.getCoordinates(request.getAddress());

            drivingTimes.put(
                    request.getId(),
                    maps.getDrivingTime(start, destination)
            );
        }

        expected.sort(Comparator.comparingDouble(
                request -> drivingTimes.get(request.getId())
        ));

        // Start with the reverse order so an unsorted result fails.
        for (int i = expected.size() - 1; i >= 0; i--) {
            feed.getRequests().add(expected.get(i));
        }

        output.reset();
        ArrayList<Request> results = feed.findRelevantRequests(user);

        assertNotNull(results);
        assertEquals("All five requests should be returned",
                5, results.size());

        // Compare times so equally quick requests may appear
        // in either order.
        for (int i = 0; i < expected.size(); i++) {
            Double actualTime = drivingTimes.get(results.get(i).getId());

            assertNotNull("An unexpected request was returned", actualTime);

            assertEquals(
                    "Requests should be ordered by quickest driving time",
                    drivingTimes.get(expected.get(i).getId()).doubleValue(),
                    actualTime.doubleValue(),
                    0.001
            );
        }

        // Confirm the console prints that same order.
        String recommended = recommendedSection();
        int previousPosition = -1;

        for (Request request : results) {
            int position = recommended.indexOf(request.toString());

            assertTrue(
                    "Printed order should match the returned list",
                    position > previousPosition
            );

            previousPosition = position;
        }
    }
}