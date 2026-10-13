package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Request {
    private int requestPriority;
    private RequestStatus status;
    private Location location;
    private String address;
    private int reportedUrgency;
    private ArrayList<String> comments;
    private ArrayList<RequestType> requestTypes;
    private User requester;
    private User responder;
    private UUID id;

    public Request(UUID id, RequestStatus status, Location location, String address, int reportedUrgency,
        ArrayList<String> comments, User requester, User responder, ArrayList<RequestType> requestTypes){
        //this.requestPriority = priority;
        this.status = status;
        this.location = location;
        this.reportedUrgency = reportedUrgency;
        this.comments = comments;
        this.requester = requester;
        this.responder = responder;
        this.requestTypes = requestTypes;
        this.id = (id == null ? UUID.randomUUID() : id);
        this.address = (address == null ? requester.getAddress() : address);
    }
        public int calcPriority(){
            double averageOfTypes = 0;
            int count = 0;
            for(RequestType type : requestTypes){
                averageOfTypes += type.urgency;
                count++;
            }
            requestPriority = (int)Math.round(((averageOfTypes/count) + reportedUrgency)/2);
            return requestPriority;
        }

        public void sendAlerts(){

        }
        
        public void updateStatus(RequestStatus status, User responder){  
            this.status = status;
            if(status == RequestStatus.HELP_ON_THE_WAY || status == RequestStatus.AT_SITE || status == RequestStatus.COMPLETED)
                this.responder = responder;
            else
                this.responder = null;
        }

        public void updateStatus(RequestStatus status){
            updateStatus(status, null);
        }

        public String toString(){
            String returnString = "";
            returnString += "Request for help with: ";
            for(RequestType type: requestTypes)
                returnString += type.label + (requestTypes.indexOf(type) < requestTypes.size()-1 ? ", " : "");
            returnString += "\nPriority Level: " + requestPriority + " \nRequested by: " + requester.getFullName() + " at " + address + " " + location.toString() + "\n";
            for(String comment : comments)
                returnString += comment + " ";
            returnString += "\nRequest Status " + status + (responder == null ? "Currently Unfulfilled" : " | Responder: "+ responder.getFullName());
            return returnString;
        }

        public int getRequestPriority() {
            return requestPriority;
        }
        public void setRequestPriority(int requestPriority) {
            this.requestPriority = requestPriority;
        }
        public RequestStatus getStatus() {
            return status;
        }
        public void setStatus(RequestStatus status) {
            this.status = status;
        }
        public Location getLocation() {
            return location;
        }
        public void setLocation(Location location) {
            this.location = location;
        }
        public ArrayList<String> getComments() {
            return comments;
        }
        public void setComments(ArrayList<String> comments) {
            this.comments = comments;
        }
        public ArrayList<RequestType> getRequestTypes() {
            return requestTypes;
        }
        public void setRequestTypes(ArrayList<RequestType> requestTypes) {
            this.requestTypes = requestTypes;
        }
        public User getRequester() {
            return requester;
        }
        public void setRequester(User requester) {
            this.requester = requester;
        }
        public User getResponder() {
            return responder;
        }
        public void setResponder(User responder) {
            this.responder = responder;
        }
        public UUID getId() {
            return id;
        }
        public void setId(UUID id) {
            this.id = id;
        }

        
}
