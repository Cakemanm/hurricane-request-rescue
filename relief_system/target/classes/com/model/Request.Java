package com.model;

public abstract class Request(){
    private int requestPriority;
    private RequestStatus status;
    private Location location;
    private ArrayList<String> comments;
    private ArrayList<RequestType> requestTypes;
    private User requester;
    private User responder;
    private UUID id;

    public abstract Request(int priority, RequestStatus status, Location location,
        ArrayList<String> comments, User user, RequestType requestType){
        public int calcPriority(){

        }
        public void sendAlerts(){

        }
        public void updateStatus(RequestStatus status){
            
        }
    }
}