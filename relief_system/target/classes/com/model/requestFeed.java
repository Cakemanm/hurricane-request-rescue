import java.util.ArrayList;

public class RequestFeed {
    private static RequestFeed requestFeed;
    private ArrayList<Requests> requests;

    private requestFeed(){
        requests = new ArrayList<Requests>();
    }

    public static requestFeed getInstance(){
        if (requests ==null) {
            requestFeed = new requestFeed();
        }

        return requestFeed;
    }

    public ArrayList<Request> getRequests(){
        //TODO: implement later
    }
}
    public void addRequest(Request request)
    {
        //TODO: implempent later
    }