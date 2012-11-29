package models;

import com.mysql.jdbc.Blob;
import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: epanahi
 * Date: 11/27/12
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class InventoryItem extends Model
{
    public InventoryItem(String name, Double price, Integer quantity, String extension)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgPath = "/assets/images/" +this.name +"." +extension;
    }
    @Id
    public String name;
    public Double price;
    public Integer quantity;
    public String imgPath;
}
