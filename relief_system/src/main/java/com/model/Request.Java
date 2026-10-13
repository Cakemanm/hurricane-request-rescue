package com.model;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Request {
    private int requestPriority;
    private RequestStatus status;
    private Location location;
    private ArrayList<String> comments;
    private ArrayList<RequestType> requestTypes;
    private User requester;
    private User responder;
    private UUID id;

    public Request(int priority, RequestStatus status, Location location,
        ArrayList<String> comments, User requester, User responder, RequestType requestType){
        this.requestPriority = priority;
        this.status = status;
        this.location = location;
        this.comments = comments;
        this.requester = requester;
        this.responder = responder;
        this.id = UUID.randomUUID();
    }
    public RequestStatus getStatus() {
    return status;
    }

    public void setResponder(User responder) {
        this.responder = responder;
    }

    public abstract int calcPriority();

    public void sendAlerts(){

    }

    public void updateStatus(RequestStatus status){
        this.status = status;

    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addComment(String comment) {
        if (comments == null) {
            comments = new ArrayList<String>();
        }
        comments.add(comment);
    }
}