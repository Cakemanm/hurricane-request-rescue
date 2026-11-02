package com.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.location.Address;

/**
 * Tests for ShelterList.addShelter. The last test also checks that a saved shelter
 * loads back through DataLoader (DataWriter/DataLoader round trip).
 
 * Tested by: Jake McNulty
 * AI use: comments and table of test cases genereted by AI. First draft of extended list of classes generated and then reviewed and edited by Jake McNulty.
 *
+--------------------------------------------+--------------------------------------------------------------------+
| Test                                       | Reasoning                                                          |
+--------------------------------------------+--------------------------------------------------------------------+
| addShelter: adds one shelter               | -a valid shelter should be added to the list                       |
| addShelter: found by name / by id          | -a new shelter must be findable the same ways the app looks        |
|                                            |  shelters up                                                       |
| addShelter: stores address, capacity,      | -every value passed in should be kept as given                     |
|             occupancy, types               |                                                                    |
| addShelter: null id                        | -a missing id should be generated, like User and Request do        |
| addShelter: null resources / null types    | -null lists become empty lists instead of crashing later           |
+--------------------------------------------+--------------------------------------------------------------------+
| addShelter: pet friendly has pet space     | -a PET_FRIENDLY shelter must be able to take a pet (Buddy)         |
| addShelter: duplicate name / duplicate id  | -the second shelter could never be looked up                       |
| addShelter: null name / null address       | -a shelter nobody can name or find shouldn't be added              |
| addShelter: occupancy over capacity,       | -impossible numbers shouldn't be accepted                          |
|             negative capacity              |                                                                    |
+--------------------------------------------+--------------------------------------------------------------------+
| addShelter: save, then reload              | -a saved shelter must load back from Shelters.json                 |
+--------------------------------------------+--------------------------------------------------------------------+
 */

public class ShelterListTest {
    private static final UUID RIVERSIDE_ID = UUID.fromString("3f9a1c52-7d84-4e1b-a6c3-2b5e8f0d9a41");

    private ShelterList shelterList;
    private Address address;

    @Before
    public void setUp() {
        TestFixtures.clearAllData();
        shelterList = ShelterList.getInstance();
        address = TestFixtures.riversideAddress();
    }

    @After
    public void tearDown() {
        TestFixtures.restoreAllData();
    }

    /** Adds a valid shelter: occupancy 140 / 300, PET_FRIENDLY + MEDICAL_STAFF. */
    private void addRiverside(UUID id) {
        shelterList.addShelter("Riverside Community Center", true, address, new ArrayList<>(),
                TestFixtures.shelterTypes(ShelterType.PET_FRIENDLY, ShelterType.MEDICAL_STAFF), 140, 300, id);
    }

    // -- normal cases

    @Test
    public void addShelter_validShelter_increasesListSizeByOne() {
        addRiverside(RIVERSIDE_ID);
        assertEquals(1, shelterList.getShelters().size());
    }

    @Test
    public void addShelter_validShelter_canBeFoundByName() {
        addRiverside(RIVERSIDE_ID);
        assertNotNull(shelterList.getShelter("Riverside Community Center"));
    }

    @Test
    public void addShelter_givenId_canBeFoundById() {
        addRiverside(RIVERSIDE_ID);
        assertEquals("Riverside Community Center", shelterList.getShelterById(RIVERSIDE_ID).getName());
    }

    @Test
    public void addShelter_validShelter_storesAddress() {
        addRiverside(RIVERSIDE_ID);
        assertSame(address, shelterList.getShelter("Riverside Community Center").getAddress());
    }

    @Test
    public void addShelter_validShelter_storesCapacity() {
        addRiverside(RIVERSIDE_ID);
        assertEquals(300, shelterList.getShelter("Riverside Community Center").getCapacity());
    }

    @Test
    public void addShelter_validShelter_storesOccupancy() {
        addRiverside(RIVERSIDE_ID);
        assertEquals(140, shelterList.getShelter("Riverside Community Center").getOccupancy());
    }

    @Test
    public void addShelter_validShelter_storesShelterTypes() {
        addRiverside(RIVERSIDE_ID);
        assertEquals(TestFixtures.shelterTypes(ShelterType.PET_FRIENDLY, ShelterType.MEDICAL_STAFF),
                shelterList.getShelter("Riverside Community Center").getShelterTypes());
    }

    @Test
    public void addShelter_nullId_generatesAnId() {
        addRiverside(null);
        assertNotNull(shelterList.getShelter("Riverside Community Center").getId());
    }

    @Test
    public void addShelter_nullResources_startsWithEmptyResourceList() {
        shelterList.addShelter("Westside Public Library", true, address, null,
                TestFixtures.shelterTypes(ShelterType.DISABILITY_ACCESSIBLE), 30, 60, null);
        assertTrue(shelterList.getShelter("Westside Public Library").getResources().isEmpty());
    }

    @Test
    public void addShelter_nullShelterTypes_startsWithEmptyTypeList() {
        shelterList.addShelter("Westside Public Library", true, address, new ArrayList<>(), null, 30, 60, null);
        assertTrue(shelterList.getShelter("Westside Public Library").getShelterTypes().isEmpty());
    }

    // -- error / edge cases

    @Test
    public void addShelter_petFriendlyShelter_hasRoomForAPet() {
        addRiverside(RIVERSIDE_ID);
        assertTrue("A PET_FRIENDLY shelter added through addShelter should be able to take at least one pet",
                shelterList.getShelter("Riverside Community Center").getAvailablePetSpaces() > 0);
    }

    @Test
    public void addShelter_duplicateName_doesNotAddSecondShelter() {
        addRiverside(RIVERSIDE_ID);
        addRiverside(UUID.randomUUID());
        assertEquals(1, shelterList.getShelters().size());
    }

    @Test
    public void addShelter_duplicateId_doesNotAddSecondShelter() {
        addRiverside(RIVERSIDE_ID);
        shelterList.addShelter("Some Other Shelter", true, address, null, null, 0, 50, RIVERSIDE_ID);
        assertEquals(1, shelterList.getShelters().size());
    }

    @Test
    public void addShelter_nullName_isRejected() {
        shelterList.addShelter(null, true, address, null, null, 0, 50, null);
        assertEquals(0, shelterList.getShelters().size());
    }

    @Test
    public void addShelter_nullAddress_isRejected() {
        shelterList.addShelter("No Address Shelter", true, null, null, null, 0, 50, null);
        assertEquals(0, shelterList.getShelters().size());
    }

    @Test
    public void addShelter_occupancyAboveCapacity_isRejected() {
        shelterList.addShelter("Overfull Shelter", true, address, null, null, 120, 100, null);
        assertEquals(0, shelterList.getShelters().size());
    }

    @Test
    public void addShelter_negativeCapacity_isRejected() {
        shelterList.addShelter("Negative Shelter", true, address, null, null, 0, -10, null);
        assertEquals(0, shelterList.getShelters().size());
    }

    // -- datawriter / dataloader round trip

    @Test
    public void addShelter_afterSave_shelterIsInSavedFile() {
        // ID that is NOT already in Shelters.json, so only a real save can make this pass
        UUID newId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        addRiverside(newId);
        shelterList.save();

        boolean found = false;
        for (Shelter shelter : DataLoader.getShelters()) {
            if (newId.equals(shelter.getId()))
                found = true;
        }
        assertTrue("Shelter saved with DataWriter should be loaded back by DataLoader", found);
    }
}
