package ca.ece.ubc.cpen221.mp5;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.antlr.v4.parse.BlockSetTransformer.setAlt_return;
import org.antlr.v4.runtime.tree.ParseTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.CosNaming.NamingContextPackage.CannotProceedHolder;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.javafx.event.EventHandlerManager;
import com.sun.xml.internal.ws.wsdl.writer.document.OpenAtts;

import apple.laf.JRSUIConstants.State;
import javafx.scene.shape.Line;


// TODO: Use this class to represent a restaurant.
// State the rep invariant and abs
/**
 * This class represents all the features of restaurants
 * @author anhduc130
 *
 */
public class Restaurant {
	private JSONObject jsonObject;
	private String jasonString;
	private Boolean open;
	private String url;
	private Double longitude;
	private Set<String> neighborhoods;
	private String business_id;
	private String name;
	private Set<String> categories;
	private String state;
	private String type;
	private Double stars;
	private String city;
	private String full_address;
	private Long review_count;
	private String photo_url;
	private Set<String> schools;
	private Double latitude;
	private Long price;
	// Rep invariant:
	//		all the variables above should follow its types accordingly when being used
	public Restaurant(String jasonString) throws ParseException {
		this.jasonString = jasonString;
		JSONParser parser = new JSONParser();
		Object obj = parser
				.parse(jasonString);
		jsonObject = (JSONObject) obj;
		
		jasonFormatString(jasonString);
		Open();
		Url();
		Longitude();
		Neighborhoods();
		Business_id();
		Name();
		Categories();
		State();
		Type();
		Rating();
		City();
		Full_address();
		Review_count();
		Photo_url();
		Schools();
		Latitude();
		Price();
		
	}
	public void jasonFormatString(String jasonString){
		this.jasonString = jasonString;
	}
	public void Open(){
		this.open = (Boolean) jsonObject.get("open");
	}
	public void Url(){
		this.url = (String) jsonObject.get("url");
	}
	public void Longitude(){
		this.longitude = (Double) jsonObject.get("longitude");
	}
	public void Neighborhoods(){
		this.neighborhoods = new HashSet<String>();
		JSONArray neighborhoodsArray = (JSONArray) jsonObject.get("neighborhoods");
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = neighborhoodsArray.iterator();
		while(iterator.hasNext()){
			neighborhoods.add(iterator.next());
		}
	}
	public void Business_id(){
		this.business_id = (String) jsonObject.get("business_id");
	}
	public void Name(){
		this.name = (String) jsonObject.get("name");
	}
	public void Categories(){
		this.categories = new HashSet<String>();
		JSONArray categoriesArray = (JSONArray) jsonObject.get("categories");
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = categoriesArray.iterator();
		while(iterator.hasNext()){
			categories.add(iterator.next());
		}
	}
	public void State(){
		this.state = (String) jsonObject.get("state");
	}
	public void Type(){
		this.type = (String) jsonObject.get("type");
	}
	public void Rating(){
		this.stars = (Double) jsonObject.get("stars");
	}
	public void City(){
		this.city = (String) jsonObject.get("city");
	}
	public void Full_address(){
		this.full_address = (String) jsonObject.get("full_address");
	}
	public void Review_count(){
		this.review_count = (Long) jsonObject.get("review_count");
	}
	public void Photo_url(){
		this.photo_url = (String) jsonObject.get("photo_url");
	}
	public void Schools(){
		this.schools = new HashSet<String>();
		JSONArray schoolsArray = (JSONArray) jsonObject.get("schools");
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = schoolsArray.iterator();
		while(iterator.hasNext()){
			schools.add(iterator.next());
		}
	}
	public void Latitude(){
		this.latitude = (Double) jsonObject.get("latitude");
	}
	public void Price(){
		this.price = (Long) jsonObject.get("price");
	}
	public String getJasonFormatString(){
		return jasonString;
	}
	public Boolean getOpen(){
		return open;
	}
	public String getUrl(){
		return url;
	}
	public Double getLongitude(){
		return longitude;
	}
	public Set<String> getNeighborhoods(){
		return neighborhoods;
	}
	public String getBusiness_id(){
		return business_id;
	}
	public String getName(){
		return name;
	}
	public Set<String> getCategories(){
		return categories;
	}
	public String getState(){
		return state;
	}
	public String getType(){
		return type;
	}
	public Double getRating(){
		return stars;
	}
	public String getCity(){
		return city;
	}
	public String getFull_address(){
		return full_address;
	}
	public Long getReview_count(){
		return review_count;
	}
	public String getPhoto_url(){
		return photo_url;
	}
	public Set<String> getSchools(){
		return schools;
	}
	public Double getLatitude(){
		return latitude;
	}
	public Long getPrice(){
		return price;
	}
}
