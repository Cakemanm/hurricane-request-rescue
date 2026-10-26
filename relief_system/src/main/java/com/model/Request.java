package com.model;

import java.util.ArrayList;
import java.util.UUID;
import com.location.Address;

import com.location.Address;

/**
 * @author Gracie Bergmann
 * @author Jake McNulty
 * @author Carter Medlin
 * @author Abigail Stalker
 * 
 * Represents a request for assistance, including its address, urgency,
 * assistance types, comments, requester, and assigned responder.
 */
public class Request {
    private int requestPriority;
    private RequestStatus status;
    private Address address;
    private int reportedUrgency;
    private ArrayList<String> comments;
    private ArrayList<RequestType> requestTypes;
    private User requester;
    private User responder;
    private UUID id;

    /**
     * Creates a request and calculates its initial priority.
     *
     * <p>If the ID is null, a random UUID is generated. If the address
     * is null, the requester's address is used.</p>
     *
     * @param id the request's unique identifier, or {@code null} to generate one
     * @param status the initial request status
     * @param address the address where assistance is needed, or {@code null} to use the requester's address
     * @param reportedUrgency the urgency reported by the requester
     * @param comments the comments associated with the request
     * @param requester the user requesting assistance
     * @param responder the assigned responder, or {@code null} if unassigned
     * @param requestTypes the types of assistance requested
     */
    public Request(UUID id, RequestStatus status, Address address, int reportedUrgency,
            ArrayList<String> comments, User requester, User responder, ArrayList<RequestType> requestTypes) {
        this.status = status;
        this.reportedUrgency = reportedUrgency;
        this.comments = comments;
        this.requester = requester;
        this.responder = responder;
        this.requestTypes = requestTypes;
        this.id = (id == null ? UUID.randomUUID() : id);
        this.address = (address == null ? requester.getAddress() : address);
        calcPriority();
    }

    /**
     * Calculates and stores the request's priority.
     *
     * The priority is the rounded average of the reported urgency and the average urgency of the request types. 
     * An empty request type list uses a reduced version of the reported urgency.
     *
     * @return the calculated priority
     */
    public int calcPriority() {
        double averageOfTypes = 0;
        int count = 0;
        for (RequestType type : requestTypes) {
            averageOfTypes += type.urgency;
            count++;
        }
        requestPriority = (int) Math.round(((averageOfTypes / count) + reportedUrgency) / 2);
        if(requestTypes.size() == 0)
            requestPriority = (int) Math.round(reportedUrgency/1.5);
        return requestPriority;
    }

    /**
     * Updates the request's status and responder assignment.
     *
     * The supplied responder is assigned when the status is
     * {@link RequestStatus#HELP_ON_THE_WAY},
     * {@link RequestStatus#AT_SITE}, or {@link RequestStatus#COMPLETED}.
     * For all other statuses, the responder is cleared.
     *
     * @param status the new request status
     * @param responder the responder to assign for an applicable status,
     *                  or {@code null} to leave the request unassigned
     */
    public void updateStatus(RequestStatus status, User responder) {
        this.status = status;
        if (status == RequestStatus.ASSIGNED || status == RequestStatus.HELP_ON_THE_WAY || status == RequestStatus.AT_SITE
                || status == RequestStatus.COMPLETED)
            this.responder = responder;
        else
            this.responder = null;
    }

    /**
     * Updates the request's status and clears its responder assignment.
     * @param status the new request status
     */
    public void updateStatus(RequestStatus status) {
        updateStatus(status, null);
    }

    public String toString() {
        String returnString = "";
        returnString += "Request for help with: ";
        for (RequestType type : requestTypes)
            returnString += type.label + (requestTypes.indexOf(type) < requestTypes.size() - 1 ? ", " : "");
        returnString += "\nPriority Level: " + this.calcPriority() + " \nRequested by: " + requester.getFullName()
                + " at " + address.toString() + "\n";
        for (String comment : comments)
            returnString += comment + " ";
        returnString += "\nRequest Status: " + status
                + (responder == null ? " | Currently Unfulfilled" : " | Responder: " + responder.getFullName());
        return returnString;
    }


    /**
     * Returns the stored priority without recalculating it.
     * @return the stored request priority
     */
    public int getRequestPriority() {
        return requestPriority;
    }

    /**
     * Sets the stored request priority.
     * @param requestPriority the priority to store
     */
    public void setRequestPriority(int requestPriority) {
        this.requestPriority = requestPriority;
    }

    /**
     * Returns the request's current status.
     * @return the request status
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Sets the request's status without changing its responder assignment.
     * @param status the new request status
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    /**
     * Returns the address where assistance is needed.
     * @return the request address
     */    
    public Address getAddress() {
        return address;
    }

    /**
     * Returns the urgency reported by the requester.
     * @return the reported urgency
     */
    public int getReportedUrgency() {
        return reportedUrgency;
    }

    /**
     * Returns the request's comment list.
     * @return the comment list, or {@code null} if none has been initialized
     */
    public ArrayList<String> getComments() {
        return comments;
    }

    /**
     * Adds a comment, initializing the comment list if necessary
     * @param comment the comment to add
     */
    public void addComment(String comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }

    /**
     * Replaces the request's comment list with the supplied list.
     * @param comments the new comment list
     */
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    /**
     * Returns the request's assistance type list.
     * @return the request type list
     */
    public ArrayList<RequestType> getRequestTypes() {
        return requestTypes;
    }

    /**
     * Replaces the request type list.
     * @param requestTypes the new list of assistance types
     */
    public void setRequestTypes(ArrayList<RequestType> requestTypes) {
        this.requestTypes = requestTypes;
        this.requestPriority = calcPriority();
    }

    /**
     * Returns the user requesting assistance.
     * @return the requester
     */
    public User getRequester() {
        return requester;
    }

    /**
     * Sets the requester without changing the request's address.
     * @param requester the user requesting assistance
     */
    public void setRequester(User requester) {
        this.requester = requester;
    }

    /**
     * Returns the assigned responder.
     * @return the responder, or {@code null} if unassigned
     */
    public User getResponder() {
        return responder;
    }

    /**
     * Sets the responder without changing the request's status.
     * @param responder the responder to assign, or {@code null} to clear the assignment
     */
    public void setResponder(User responder) {
        this.responder = responder;
    }

    /**
     * Returns the request's unique identifier.
     * @return the request ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the request's identifier.
     * @param id the new request ID
     */
    public void setId(UUID id) {
        this.id = id;
    }

}
