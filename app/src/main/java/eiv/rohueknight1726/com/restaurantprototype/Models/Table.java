package eiv.rohueknight1726.com.restaurantprototype.Models;

/**
 * Created by swathysudarsanan on 04/06/17.
 */

public class Table {
    String tableNumber;
    public Table(){}
    public Table(String tableNumber){
        this.tableNumber = tableNumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }
}
