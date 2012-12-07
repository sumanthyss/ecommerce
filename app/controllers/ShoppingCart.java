package controllers;

import models.InventoryItem;
import models.Shopper;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: epanahi
 * Date: 12/2/12
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShoppingCart extends Controller
{
    /**
     * Render the index page -- all other functions are called by the client
     * @return
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result goShopping() {
        JsonNode json = request().body().asJson();
        Double budget = json.get("budget").asDouble();
        ArrayNode items = (ArrayNode) json.get("items");

        ObjectNode result = Shopper.goShopping(items, budget);

        String returnCode = result.remove("status").asText();
        if(returnCode.equals("OK")){
            return ok(result);
        } else {
            return badRequest(result);
        }
    }
}
