package com.model;

import static org.junit.Assert.*;

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
import com.location.Coordinates;
import com.location.MapTools;

/*
+-------------------------------------+--------------------------------------------------------------------+
| Test                                | Reasoning                                                          |
+-------------------------------------+--------------------------------------------------------------------+
| null group size                     |   -the method shouldn't be able to run if group size is null       |
|                                     |    because it could lead them to a shelter that couldn't take them |
| returns shelters based on capacity  |   -only matches a group with shelters that have enough available   |
|                                     |    spaces for them                                                 |
| needMedicalStaff works              |   -tests the basic boolean matching functionality                  |
| returns shelters that meet with all |   -tests all three booleans at once                                |
| three criteria                      |                                                                    |
| shelters ordered by distance        |   -the shelters should be correctly ordered by proximity to user   |
+-------------------------------------+--------------------------------------------------------------------+
 */

public class ShelterSearchTesting {

    private ShelterList shelterList;
    private ArrayList<Shelter> originalShelters;
    private User user;

    private Shelter a;
    private Shelter b;
    private Shelter c;
    private Shelter d;
    private Shelter e;
    private Shelter f;

    @Before
    public void setUp() {
        shelterList = ShelterList.getInstance();

        originalShelters = new ArrayList<>(shelterList.getShelters());
        shelterList.getShelters().clear();

        user = new User(
                UUID.randomUUID(),
                "shelterTestUser",
                "Test",
                "User",
                "000-000-0000",
                address("100 Aquarium Wharf", "29401"),
                "testPassword",
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                false,
                30,
                null
        );

        // A: 2 spaces; meets every requirement.
        a = shelter(
                "A",
                address("68 Calhoun Street", "29401"),
                2, 1, 0,
                ShelterType.MEDICAL_STAFF,
                ShelterType.DISABILITY_ACCESSIBLE,
                ShelterType.PET_FRIENDLY
        );

        // B: 3 spaces; medical staff only.
        b = shelter(
                "B",
                address("360 Meeting Street", "29403"),
                3, 0, 0,
                ShelterType.MEDICAL_STAFF
        );

        // C: Only 1 space; otherwise meets every requirement.
        c = shelter(
                "C",
                address("100 Aquarium Wharf", "29401"),
                1, 1, 0,
                ShelterType.MEDICAL_STAFF,
                ShelterType.DISABILITY_ACCESSIBLE,
                ShelterType.PET_FRIENDLY
        );

        // D: 2 spaces; no medical staff.
        d = shelter(
                "D",
                address("87 Church Street", "29401"),
                2, 1, 0,
                ShelterType.DISABILITY_ACCESSIBLE,
                ShelterType.PET_FRIENDLY
        );

        // E: 2 spaces; pet friendly, but its pet space is occupied.
        e = shelter(
                "E",
                address("135 Meeting Street", "29401"),
                2, 1, 1,
                ShelterType.MEDICAL_STAFF,
                ShelterType.DISABILITY_ACCESSIBLE,
                ShelterType.PET_FRIENDLY
        );

        // F: 2 spaces; medical staff and pet space, but inaccessible.
        f = shelter(
                "F",
                address("21 East Battery", "29401"),
                2, 1, 0,
                ShelterType.MEDICAL_STAFF,
                ShelterType.PET_FRIENDLY
        );

        shelterList.getShelters().addAll(
                Arrays.asList(a, b, c, d, e, f)
        );
    }

    @After
    public void tearDown() {
        if (originalShelters != null) {
            shelterList.getShelters().clear();
            shelterList.getShelters().addAll(originalShelters);
        }
    }

    private Address address(String street, String zipcode) {
        return new Address(street, "Charleston", "SC", zipcode);
    }

    private Shelter shelter(
            String name,
            Address address,
            int availableSpaces,
            int petCapacity,
            int petCount,
            ShelterType... types) {

        // Every shelter has capacity 10. Occupancy determines
        // how many spaces remain.
        return new Shelter(
                name,
                true,
                new ArrayList<>(),
                address,
                new ArrayList<>(Arrays.asList(types)),
                10 - availableSpaces,
                10,
                UUID.randomUUID(),
                petCapacity,
                petCount
        );
    }

    // Checks exact membership without requiring a particular order.
    private void assertMatches(
            ArrayList<Shelter> actual,
            Shelter... expected) {

        assertNotNull(actual);
        assertEquals(
                "Incorrect number of matching shelters",
                expected.length,
                actual.size()
        );

        for (Shelter shelter : expected) {
            assertTrue(
                    "Missing shelter: " + shelter.getName(),
                    actual.contains(shelter)
            );
        }
    }

    @Test
    public void invalidGroupSize_returnsNull() {
        assertNull(shelterList.findShelters(
                user, 0, false, false, false
        ));

        assertNull(shelterList.findShelters(
                user, -1, false, false, false
        ));
    }

    @Test
    public void capacityOnly_returnsSheltersWithEnoughSpace() {
        ArrayList<Shelter> results = shelterList.findShelters(
                user, 2, false, false, false
        );

        assertMatches(results, a, b, d, e, f);
    }

    @Test
    public void medicalStaff_returnsSheltersWithSpaceAndMedics() {
        ArrayList<Shelter> results = shelterList.findShelters(
                user, 2, true, false, false
        );

        assertMatches(results, a, b, e, f);
    }

    @Test
    public void allRequirements_returnsOnlySuitableShelters() {
        ArrayList<Shelter> results = shelterList.findShelters(
                user, 2, true, true, true
        );

        assertMatches(results, a);
    }

    @Test
    public void medicalShelters_areOrderedByQuickestDrivingTime()
            throws Exception {

        MapTools maps = MapTools.getInstance();
        Coordinates start = maps.getCoordinates(user.getAddress());

        Map<UUID, Double> drivingTimes = new HashMap<>();
        ArrayList<Double> expectedTimes = new ArrayList<>();

        // Calculate each eligible shelter's expected travel time once.
        for (Shelter shelter : Arrays.asList(a, b, e, f)) {
            Coordinates destination =
                    maps.getCoordinates(shelter.getAddress());

            double seconds = maps.getDrivingTime(start, destination);

            drivingTimes.put(shelter.getId(), seconds);
            expectedTimes.add(seconds);
        }

        Collections.sort(expectedTimes);

        // Supply matching shelters in reverse driving-time order.
        // This prevents preserving the input order from passing.
        shelterList.getShelters().sort((first, second) ->
                Double.compare(
                        drivingTimes.getOrDefault(second.getId(), -1.0),
                        drivingTimes.getOrDefault(first.getId(), -1.0)
                )
        );

        ArrayList<Shelter> results = shelterList.findShelters(
                user, 2, true, false, false
        );

        assertMatches(results, a, b, e, f);

        for (int i = 0; i < results.size(); i++) {
            double actualTime =
                    drivingTimes.get(results.get(i).getId());

            assertEquals(
                    "Shelter at position " + i
                            + " should have the next quickest driving time",
                    expectedTimes.get(i).doubleValue(),
                    actualTime,
                    0.001
            );
        }
    }
}