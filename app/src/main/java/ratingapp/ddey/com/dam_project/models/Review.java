package ratingapp.ddey.com.dam_project.models;

public class Review {

    private String name;
    private float points;

    public Review(String name, float points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
