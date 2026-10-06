import java.util.ArrayList;

public class ShelterList {
    private static ShelterList shelterList;
    private ArrayList<Shelter> shelters;

    private ShelterList() {
        shelters = new ArrayList<Shelter>();
    }

    public static ShelterList getInstance() {
        if (shelterList == null) {
            shelterList = new ShelterList();
        }
        return shelterList;
    }

    public void addShelter(String name, boolean availability,
            ArrayList<Resource> resources, Location location,
            ShelterType type, int occupancy, int capacity) {
        // TODO: implement later
    }

    public Shelter getShelter(String name) {
        // TODO: implement later
        return null;
    }
}