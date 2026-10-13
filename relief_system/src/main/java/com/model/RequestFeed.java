package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class RequestFeed {
    private static RequestFeed requestFeed;
    private ArrayList<Request> requests;

    private RequestFeed(){
        requests = DataLoader.getRequests();
    }

    public static RequestFeed getInstance(){
        if(requestFeed == null) {
        requestFeed = new RequestFeed();
    }
        return requestFeed;
    }

    public Request getRequest(UUID id){
        for(Request request : requests){
            if(request.getId().equals(id))
                return request;
        }
        return null;
    }

    public void addRequest(UUID id, RequestStatus status, Location location, String address, int reportedUrgency,
        ArrayList<String> comments, User requester, User responder, ArrayList<RequestType> requestTypes){
            requests.add(new Request(id, status, location, address, reportedUrgency, comments, requester, responder, requestTypes));
        }
    
}
