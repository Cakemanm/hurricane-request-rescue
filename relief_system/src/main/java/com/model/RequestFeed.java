package com.model;
import com.location.Address;
import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * 
 * Manages a shared collection of assistance requests using a singleton instance.
 *
 * <p>Supports adding, retrieving, searching, displaying, and saving requests,
 * as well as finding requests relevant to a user's skills.</p>
 */
public class RequestFeed {
    private static RequestFeed requestFeed;
    private ArrayList<Request> requests;

    /**
     * Creates the request feed by loading requests through {@link DataLoader}.
     */
    private RequestFeed(){
        requests = DataLoader.getRequests();
    }

    /**
     * Returns the shared request feed, creating it on first access.
     * @return the singleton request feed
     */
    public static RequestFeed getInstance() {
        if(requestFeed == null) {
        requestFeed = new RequestFeed();
    }
        return requestFeed;
    }

    /**
     * Finds the first request with the specified identifier.
     * @param id the request identifier to search for
     * @return the matching request, or {@code null} if none is found
     */
    public Request getRequest(UUID id) {
        for(Request request : requests){
            if(request.getId().equals(id))
                return request;
        }
        return null;
    }

    /**
     * Finds the first request whose requester equals the specified user.
     * @param requester the user whose request should be retrieved
     * @return the first matching request, or {@code null} if none is found
     */
    public Request getRequest(User requester){
        for(Request request : requests){
            if(request.getRequester().equals(requester))
                return request;
        }
        return null;
    }

    /**
     * Creates a request from the supplied details and adds it to the feed.
     * 
     * <p>The request constructor generates an ID when {@code id} is {@code null}
     * and uses the requester's address when {@code address is {@code null}.
     *
     * @param id the request identifier, or {@code null} to generate one
     * @param status the initial request status
     * @param address the address where assistance is needed, or {@code null} to use the requester's address
     * @param reportedUrgency the urgency reported by the requester
     * @param comments the comments associated with the request
     * @param requester the user requesting assistance
     * @param responder the assigned responder, or {@code null} if unassigned
     * @param requestTypes the types of assistance requested
     */
    public void addRequest(UUID id, RequestStatus status, Address address, int reportedUrgency,
        ArrayList<String> comments, User requester, User responder, ArrayList<RequestType> requestTypes){
            requests.add(new Request(id, status, address, reportedUrgency, comments, requester, responder, requestTypes));
            requestFeed.save();
        }
    
    /**
     * Adds an existing request to the feed if it is not {@code null}.
     * @param request the request to add; {@code null} is ignored
     */
    public void addRequest(Request request) {
        if (request != null) {
            requests.add(request);
            requestFeed.save();
        }
    }

    /**
     * Returns the underlying request list.
     * 
     * <p>Changes to the returned list directly affect this feed.</p>
     * @return the request list
     */
    public ArrayList<Request> getRequests(){
        return requests;
    }

    /**
     * Searches request type labels and comments for a case-insensitive substring match.
     * 
     * @param key the text to search for
     * @return the matching requests, or {@code null} if no matches are found
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public ArrayList<Request> searchRequests(String key){
        ArrayList<Request> results = new ArrayList<>();
        String lower = key.toLowerCase();
        for(Request request : requests){
            if(request.getStatus().equals(RequestStatus.COMPLETED) || request.getStatus().equals(RequestStatus.HELP_ON_THE_WAY))
                continue;
            for(RequestType type : request.getRequestTypes()){
                if(type.label.toLowerCase().contains(lower)){
                    results.add(request);
                    continue;
                }
                    
            }
            for(String comment : request.getComments()){
                if(comment.toLowerCase().contains(lower)){
                    results.add(request);  
                    continue;
                }
                    
            }
        }
        if(results.size() == 0)
            return null;
        return results;
    }

    /**
     * Finds pending or backup-requested requests that match the user's skills
     * and orders them by proximity using {@link ReliefFacade#findClosestRequest}.
     *
     * <p>A request matches when at least one of its request types requires
     * a skill the user has. Matching requests are ordered by repeatedly
     * selecting the closest remaining request to the user.</p>
     *
     * <p>Prints matching requests under "Relevant Requests" and eligible
     * requests without a skill match under "Other requests". Requests with
     * other statuses are excluded from both groups.</p>
     *
     * @param user the user whose skills and location determine relevance
     * @return the matching requests ordered by proximity, or an empty list if no requests match
     */
    public ArrayList<Request> findRelevantRequests(User user){
        ArrayList<Request> resultsTemp = new ArrayList<>();
        ArrayList<Request> others = new ArrayList<>();
        for(Request request : requests){
            if(request.getStatus()!= RequestStatus.PENDING && request.getStatus() != RequestStatus.BACKUP_REQUESTED)
                continue;
            boolean matches = false;
            for(RequestType type : request.getRequestTypes()){
                for(Skill skill : user.getSkills()){
                    if(skill.equals(type.skill)){
                        matches = true;
                        break;
                    }
                }   
            }
            if (matches)
                resultsTemp.add(request);
            else
                others.add(request);
        }
        ReliefFacade reliefFacade = new ReliefFacade();
        ArrayList<Request> results = new ArrayList<>();
        while(resultsTemp.size()>=1){
            Request r = reliefFacade.findClosestRequest(user,resultsTemp);
            results.add(r);
            resultsTemp.remove(r);
        }

        System.out.println("\nRelevant Requests:\n");
        for(Request request: results)
            System.out.println(request.toString() + "\n");
        System.out.println("\nOther requests:\n");
        for(Request request: others)
            System.out.println(request.toString() + "\n");
        return results;
    }
    
    /**
     * Prints every request in the feed using its string representation.
     */
    public void printRequestFeed(){
        for (Request request : requests){
            System.out.println(request.toString());
        }
    }

    /**
     * Saves the current request list through {@link DataWriter}.
     */
    public void save() {
        DataWriter.saveRequests(requests);
    }
}
