package controllers;

import models.InventoryItem;
import play.*;
import play.data.Form;
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

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render());
  }

  public static Result addItem() {
      MultipartFormData body = request().body().asMultipartFormData();
      Map<String, String[]> itemProps = body.asFormUrlEncoded();
      String itemName = null;
      Integer quantity = null;
      Double price = null;
      try
      {
          itemName = itemProps.get("name")[0];
          quantity = Integer.parseInt(itemProps.get("quantity")[0]);
          price    = Double.parseDouble(itemProps.get("price")[0]);
          FilePart picture = body.getFile("image");
          if (picture == null || quantity == null || price == null || itemName == null)
          {
              throw new Exception("Fields not filled");
          }
          String contentType = picture.getContentType();
          String[] formats = {"image/png", "image/jpg", "image/jpeg", "image/gif"};
          List<String> acceptableFormats = Arrays.asList(formats);
          if (!(acceptableFormats.contains(contentType))) throw new Exception("Unacceptable type");
          File sourceFile = picture.getFile();
          String extension = contentType.split("/")[1];
          if (sourceFile.length() > 100000) throw new Exception("File type is too large");
          InventoryItem newItem = new InventoryItem(itemName, price, quantity, extension);
          newItem.save();
          File destFile = new File("public/images/" +itemName +"." +extension);
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
          return redirect(routes.Application.index());
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
          System.out.println(ex.getMessage());
          flash("error", "Please fill in all of the fields");
          return redirect(routes.Application.index());
      }
  }

  public static Result getItems() {
      List<InventoryItem> items = new Model.Finder(String.class, InventoryItem.class).all();
      return ok(toJson(items));
  }
}