package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Request {
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
        public int calcPriority(){
            return 0;
        }

        public void sendAlerts(){

        }
        
        public void updateStatus(RequestStatus status){
            
        }
}
