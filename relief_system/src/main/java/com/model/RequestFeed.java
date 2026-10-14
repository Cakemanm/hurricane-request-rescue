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

    public ArrayList<Request> getRequests(){
        return requests;
    }

    public ArrayList<Request> searchRequests(String key){
        ArrayList<Request> results = new ArrayList<>();
        for(Request request : requests){
            for(RequestType type : request.getRequestTypes()){
                if(type.label.toLowerCase().contains(key.toLowerCase()))
                    results.add(request);
            }
            for(String comment : request.getComments()){
                if(comment.toLowerCase().contains(key.toLowerCase()))
                    results.add(request);
            }
        }
        if(results.size() == 0)
            return null;
        return results;
    }
    
    public void printRequestFeed(){
        for (Request request : requests){
            System.out.println(request.toString());
        }
    }
}
