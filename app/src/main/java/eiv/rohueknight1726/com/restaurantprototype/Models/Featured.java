package eiv.rohueknight1726.com.restaurantprototype.Models;

/**
 * Created by swathysudarsanan on 03/06/17.
 */

public class Featured {


    String name;

    String id;
    String description;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    String preparationTime;

    public Featured(){}

    public Featured(String id,String name, String description,String price,String preparationTime){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.preparationTime = preparationTime;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
