package controllers;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: epanahi
 * Date: 12/2/12
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Shopper extends Controller
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
        System.out.println(budget);
        Iterator<JsonNode> iter = items.getElements();
        while (iter.hasNext())
        {
            JsonNode node = iter.next();
            System.out.println(node.toString());
        }
        ObjectNode result = Json.newObject();
        if(items == null || budget == null)
        {
            result.put("status", "KO");
            result.put("message", "Missing parameter");
            result.put("items", items);
            result.put("budget", budget);
            return badRequest(result);
        } else {
            result.put("status", "OK");
            result.put("budget", budget);
            result.put("message", items);
            return ok(result);
        }
    }
}
