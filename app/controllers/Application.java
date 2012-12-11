package controllers;

import models.InventoryItem;
import play.db.ebean.Model;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;
import static play.libs.Json.toJson;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Application extends Controller{
    private static String serverPass = System.getenv("PLAYUPLOAD");
    private static String imgDir     = System.getenv("PLAYIMAGEDIR");
    private static String imgBase    = System.getenv("PLAYIMAGEPATH");

/**
 * Render the index page -- all other functions are called by the client
 * @return
 */
      public static Result index() {
        List<InventoryItem> items = new Model.Finder(String.class, InventoryItem.class).all();
        return ok(index.render(items));
      }

    /**
     * Add a new inventory item -- the complexity arises from validating an
     * uploaded picture and placing it in the images directory
     * @return
     */
      public static Result addItem() {
          MultipartFormData body = request().body().asMultipartFormData();
          Map<String, String[]> itemProps = body.asFormUrlEncoded();
          String itemName = null;
          Integer quantity = null;
          Double price = null;
          String password = null;
          try
          {
              itemName = itemProps.get("name")[0];
              quantity = Integer.parseInt(itemProps.get("quantity")[0]);
              price    = Double.parseDouble(itemProps.get("price")[0]);
              password = itemProps.get("password")[0];
              if ( ! (password.equals(serverPass)) ) throw new Exception("Authentication failed");
              FilePart picture = body.getFile("image");
              if (picture == null || quantity == null || price == null || itemName == null || password == null)
              {
                  throw new Exception("Fill in all fields");
              }
              String contentType = picture.getContentType();
              String[] formats = {"image/png", "image/jpg", "image/jpeg", "image/gif"};
              List<String> acceptableFormats = Arrays.asList(formats);
              if (!(acceptableFormats.contains(contentType))) throw new Exception("Unacceptable image type");
              File sourceFile = picture.getFile();
              String extension = contentType.split("/")[1];
              if (sourceFile.length() > 100000) throw new Exception("File type is too large");
              String imageURI = "";
              if (imgBase != null)
              {
                  imageURI = imgBase + itemName +"." +extension;
              }
              else
              {
                  imageURI = "assets/images/" +itemName +"." +extension;
              }
              InventoryItem newItem = new InventoryItem(itemName, price, quantity, imageURI);
              newItem.save();
              File destFile = null;
              if (imgDir != null)
                  destFile = new File(imgDir +itemName +"." +extension);
              else
                destFile = new File("public/images/" +itemName +"." +extension);
              if(!destFile.exists()) destFile.createNewFile();

              FileChannel source = null;
              FileChannel destination = null;

              try {
                  source = new FileInputStream(sourceFile).getChannel();
                  destination = new FileOutputStream(destFile).getChannel();
                  destination.transferFrom(source, 0, source.size());
              }
              finally {
                  if(source != null) {
                      source.close();
                  }
                  if(destination != null) {
                      destination.close();
                  }
              }
              return ok(toJson(newItem));
          }
          catch(Exception ex)
          {
              return badRequest(ex.getMessage());
          }
      }

    /**
     * Route to get a JSON array of all inventory items
     * @return 200 response with JSON array
     */
      public static Result getInventory() {
          List<InventoryItem> items = new Model.Finder(String.class, InventoryItem.class).all();
          return ok(toJson(items));
      }
}