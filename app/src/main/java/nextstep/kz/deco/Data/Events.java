package nextstep.kz.deco.Data;

import java.util.ArrayList;
import java.util.List;

public class Events {
    String name;
    String date;
    String price;
    String description;
    String site;
    String city;
    String image;
    int image_id;

    public Events(String name, String date, String price, String description, String site, String city, String image, int image_id) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
        this.site = site;
        this.city = city;
        this.image = image;
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
