package beans;

import java.util.Scanner;

public class PeopleDetails {
    private String name;
    private String joined_date;
    private String image_path;
    private String consent_form;

    public PeopleDetails() {}

    public PeopleDetails(String name, String joined_date, String image_path, String consent_form) {
        this.name = name;
        this.joined_date = joined_date;
        this.image_path = image_path;
        this.consent_form = consent_form;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJoined_date() {
        return joined_date;
    }
    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }
    public String getImage_path() {
        return image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    public String getConsent_form() {
        return consent_form;
    }
    public void setConsent_form(String consent_form) {
        this.consent_form = consent_form;
    }

    @Override
    public String toString(){
        return "Person{" +
                "name = '" + name + '\'' +
                ", joined_date = " + joined_date + '\'' +
                ", image_path = " + image_path + '\'' +
                ", consent_form = " + consent_form + '\'' +
                '}';
    }

}
