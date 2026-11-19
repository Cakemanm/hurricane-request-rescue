package com.model;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

/*
+-------------------------------------+-------------------------------------------------------------------+
| Test                                | Reasoning                                                         |
+-------------------------------------+-------------------------------------------------------------------+
| requestType matching                |     basic request matching using one matching request type        |
| capitalization differences          |     a successful search should ignore capitalization              |
| no duplicates                       |     if a request includes the keyword multiple times, there       |
|                                     |     shouldn't be duplicates                                       |
| excluding requests that don't match |     it shouldn't return requests without the keyword              |
| matches disjointed keywords         |     words shouldn't have to be in the exact order they're searched|
|                                     |     in if a request contains all keywords                         |
+-------------------------------------+-------------------------------------------------------------------+
 */

public class ListSearchTesting {
    private RequestFeed feed;

    @Before
    public void setUp() {
        feed = RequestFeed.getInstance();
    }

    // Counts matches by ID, even if the same request appears multiple times.
    private long countRequest(ArrayList<Request> results, String id) {
        UUID requestId = UUID.fromString(id);

        return results.stream()
                .filter(request -> request.getId().equals(requestId))
                .count();
    }

    @Test
    public void searchRequests_matchesRequestType() {
        // The collapsed bridge request has type TRANSPORTATION,
        // but its comment does not contain that type's label.
        ArrayList<Request> results =
                feed.searchRequests(RequestType.TRANSPORTATION.label);

        assertNotNull(results);
        assertTrue(countRequest(
                results,
                "6e39c420-7ba8-4f61-92d3-805ca1b74e96"
        ) > 0);
    }

    @Test 
    public void searchRequests_differentCapitalization(){
        
        ArrayList<Request> results = feed.searchRequests("transportation");
        assertNotNull(results);
        assertTrue(countRequest(
            results,
            "6e39c420-7ba8-4f61-92d3-805ca1b74e96"
        ) > 0);
    }

    @Test
    public void searchRequests_matchesComment() {
        // "clothing donations" appears in the shelter request's comment.
        ArrayList<Request> results =
                feed.searchRequests("clothing donations");

        assertNotNull(results);
        assertTrue(countRequest(
                results,
                "bf0584fd-deca-41f3-93d5-c82d31336762"
        ) > 0);
    }

    @Test
    public void searchRequests_matchesTypeAndComment_withoutDuplicates() {
        // The injured child request has type BLEEDING and
        // a comment containing "bleeding wounds".
        ArrayList<Request> results = feed.searchRequests("bleeding");

        assertNotNull(results);
        assertEquals(1L, countRequest(
                results,
                "aa2387e6-bd6c-4493-96e3-5eeb5d78b1af"
        ));
    }

    @Test
    public void searchRequests_excludesRequestWithoutMatch() {
        ArrayList<Request> results =
                feed.searchRequests("clothing donations");

        assertNotNull(results);

        // The injured child request does not mention clothing donations.
        assertEquals(0L, countRequest(
                results,
                "aa2387e6-bd6c-4493-96e3-5eeb5d78b1af"
        ));
    }

    @Test
    public void searchRequests_matchesWordsThatAppearSeparately() {
        // "tree", "detours", and "lifters" appear in separate comments
        // on the fallen tree request.
        ArrayList<Request> results =
                feed.searchRequests("tree detours lifters");

        assertNotNull(
            "Words appearing separately should still match the request",
            results
        );
        assertTrue(countRequest(
                results,
                "a2170593-1814-427f-9ef6-7e52966a8238"
        ) > 0);
    }
}
