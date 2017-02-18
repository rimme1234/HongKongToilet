package hk.edu.ouhk.ronnie.hongkongtoilet;

import java.io.Serializable;

/**
 * Created by Ronnie on 18/2/2017.
 */

public class Toilet implements Serializable {
    public int id;
    public String type;
    public String name;
    public String address;
    public double lat;
    public double lng;
    public String distance;
}

