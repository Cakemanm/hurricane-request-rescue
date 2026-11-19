package com.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
 * Tests for HurricaneHandler.addHurricane, plus how DataLoader.getHurricanes loads
 * Hurricane.json (expects the file's 3 hurricanes: Katrina, Shelby, Scarlet).
 *
 * Tested by: Jake McNulty
 * AI use: Comment box written with AI and tests checked over to ensure covering all basis.
 *          Some VSCode auto-complete was used to generate forgotten test cases.
 *
+--------------------------------------------+--------------------------------------------------------------------+
| Test                                       | Reasoning                                                          |
+--------------------------------------------+--------------------------------------------------------------------+
| addHurricane: adds one hurricane           | -a valid hurricane should be tracked                               |
| addHurricane: found by name, any case      | -users may type the name in any capitalization                     |
| addHurricane: stores id, category, wind    | -every value passed in should be kept as given                     |
|               speed, zip codes             |                                                                    |
+--------------------------------------------+--------------------------------------------------------------------+
| addHurricane: null id                      | -a missing id should be generated, like the other classes          |
| addHurricane: category 0 / category 6      | -categories only go from 1 to 5                                    |
| addHurricane: negative wind speed          | -wind speed can't be below zero                                    |
| addHurricane: duplicate name               | -getHurricane(name) could only ever find the first one             |
+--------------------------------------------+--------------------------------------------------------------------+
| DataLoader: loads every hurricane          | -all hurricanes in Hurricane.json should load, not just the first  |
| DataLoader: called twice                   | -loading again shouldn't duplicate the list                        |
+--------------------------------------------+--------------------------------------------------------------------+
 */

public class HurricaneHandlerTest {
    private static final UUID SCARLET_ID = UUID.fromString("890e84e7-4971-4268-b1cb-9f01da19f05e");

    private HurricaneHandler handler;

    @Before
    public void setUp() {
        TestFixtures.clearAllData();
        handler = HurricaneHandler.getInstance();
    }

    @After 
    public void tearDown() {
        TestFixtures.restoreAllData();
    }

    private ArrayList<Integer> charlestonZips() {
        return new ArrayList<>(Arrays.asList(29401, 29403, 29405));
    }

    private void addScarlet(UUID id, int category, int windSpeed) {
        handler.addHurricane(id, "Scarlet", category, windSpeed, charlestonZips(),
                "Flying debris, flooding, and power outages.", 200.0, true);
    }

    // -- normal cases

    @Test 
    public void addHurricane_validHurricane_increasesListSizeByOne() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(1, handler.getHurricanes().size());
    }

    @Test
    public void addHurricane_validHurricane_canBeFoundByName() {
        addScarlet(SCARLET_ID, 3, 125);
        assertNotNull(handler.getHurricane("Scarlet"));
    }

    @Test
    public void addHurricane_validHurricane_canBeFoundByNameIgnoringCase() {
        addScarlet(SCARLET_ID, 3, 125);
        assertNotNull(handler.getHurricane("sCaRlEt"));
    }

    @Test
    public void addHurricane_validHurricane_storesCategory() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(3, handler.getHurricane("Scarlet").getCategory());
    }

    @Test
    public void addHurricane_validHurricane_storesWindSpeed() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(125, handler.getHurricane("Scarlet").getWindSpeed());
    }

    @Test 
    public void addHurricane_validHurricane_storesAffectedZips() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(charlestonZips(), handler.getHurricane("Scarlet").getAffectedAreas());
    }

    @Test 
    public void addHurricane_validHurricane_storesId() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(SCARLET_ID, handler.getHurricane("Scarlet").getId());
    }

    // -- error cases

    @Test 
    public void addHurricane_nullId_generatesAnId() {
        addScarlet(null, 3, 125);
        assertNotNull("Like User, Request, and Shelter, a null ID should be replaced with a randomly generated UUID",
            handler.getHurricane("Scarlet").getId());
    }

    @Test 
    public void addHurricane_categoryAboveFive_isRejected() {
        addScarlet(SCARLET_ID, 6, 125);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test
    public void addHurricane_categoryBelowOne_isRejected() {
        addScarlet(SCARLET_ID, 0, 125);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test
    public void addHurricane_windSpeedBelowZero_isRejected() {
        addScarlet(SCARLET_ID, 3, -10);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test 
    public void addHurricane_windSpeedAbove300_isRejected() {
        addScarlet(SCARLET_ID, 3, 350);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test 
    public void addHurricane_negativeWindSpeed_isRejected() {
        addScarlet(SCARLET_ID, 3, -50);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test 
    public void addHurricane_nullName_isRejected() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test 
    public void addHurricane_emptyName_isRejected() {
        addScarlet(SCARLET_ID, 3, 125);
        assertEquals(0, handler.getHurricanes().size());
    }

    @Test 
    public void addHurricane_duplicateName_isRejected() {
        addScarlet(SCARLET_ID, 3, 125);
        addScarlet(UUID.randomUUID(), 4, 140);
        assertEquals(1, handler.getHurricanes().size());
    }

    // -- DataLoader

    @Test 
    public void getHurricanes_fileWithThreeHurricanes_loadsAllThree() {
        assertEquals(3, DataLoader.getHurricanes().size());
    }

    @Test
    public void getHurricanes_calledTwice_doesNotDuplicateHurricanes() {
        int firstLoadSize = DataLoader.getHurricanes().size();
        int secondLoadSize = DataLoader.getHurricanes().size();
        assertEquals(firstLoadSize, secondLoadSize);
    }
}
