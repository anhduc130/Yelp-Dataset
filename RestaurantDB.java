package ca.ece.ubc.cpen221.mp5;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.antlr.v4.runtime.tree.ParseTree;
import org.json.simple.parser.ParseException;


//import sun.security.provider.JavaKeyStore.CaseExactJKS;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {
	private Set<Restaurant> allRestaurants = new HashSet<>();
	private Set<String> restaurantByNames = new HashSet<>();
	private Set<String> restaurantByNeighborhoods = new HashSet<>();
	private Set<String> restaurantByRating = new HashSet<>();
	private Set<String> restaurantByPrice = new HashSet<>();
	private Set<String> restaurantByCategories = new HashSet<>();

	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 * 
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */

	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) throws ParseException, IOException {
		//JSONParser parser = new JSONParser();
		BufferedReader br = new BufferedReader(new FileReader(restaurantJSONfilename));
		String jasonString = null;
		while ((jasonString = br.readLine()) != null ){
			Restaurant restaurant = new Restaurant(jasonString);
			allRestaurants.add(restaurant);
		}
		br.close();
	}


	/**
	 * Receives a query of type String and return a set of restaurants that satisfy the query
	 * @param queryString
	 * 				query received by clients
	 * @return a set of restaurants that satisfy the query
	 * @throws ParseException
	 * @throws IOException
	 */
	public Set<Restaurant> query(String queryString) throws ParseException, IOException {
		// TODO: Implement this method
		// Write specs, etc.
		Set<Restaurant> setRestaurants = new HashSet<>();
		String restaurantName;
		String neighborhoodName;
		String rating;
		String price;
		String category;

		int evenNumber = 0;
		int evenNumber1 = 0;
		FormulaFactory formula = new FormulaFactory(queryString);
		ParseTree tree = formula.parse();
		//System.out.print(tree.getChild(0).getChild(0).getChild(0).getChild(0).getText());
		while (evenNumber <= tree.getChild(0).getChildCount()){
			int num = tree.getChild(0).getChild(evenNumber).getChildCount();
			if (num == 1){
				ParseTree child = tree.getChild(0).getChild(evenNumber).getChild(0);
				switch (child.getChild(0).getText()){
				case "name": {
					restaurantName = child.getChild(3).getText();
					for(int i = 4; i < child.getChildCount() - 2; i++){
						restaurantName = restaurantName + " " + child.getChild(i).getText();
					}
					searchByNames(restaurantName);
					break;
				}

				case "in": {
					neighborhoodName = child.getChild(3).getText();
					for(int i = 4; i < child.getChildCount() - 2; i++){
						neighborhoodName = neighborhoodName + " " + child.getChild(i).getText();
					}
					searchByNeighborhoods(neighborhoodName);
					break;
				}

				case "rating": {
					rating = child.getChild(2).getText();
					searchByRating(rating);
					break;
				}

				case "price": {
					price = child.getChild(2).getText() + child.getChild(4).getText();
					searchByPrice(price);
					break;
				}

				case "category": {
					category = child.getChild(3).getText();
					for(int i = 4; i < child.getChildCount() - 2; i++){
						category = category + " " + child.getChild(i).getText();
					}
					searchByCategories(category);
					break;
				}
				}
			}

			else if (num > 1){
				ParseTree smallTree = tree.getChild(0).getChild(evenNumber).getChild(1);
				while (evenNumber1 <= smallTree.getChildCount()){
					ParseTree smallChild = smallTree.getChild(evenNumber1).getChild(0).getChild(0);
					switch(smallChild.getChild(0).getText()){
					case "name": {
						restaurantName = smallChild.getChild(3).getText();
						for(int i = 4; i < smallChild.getChildCount() - 2; i++){
							restaurantName = restaurantName + " " + smallChild.getChild(i).getText();
						}
						searchByNames(restaurantName);
						break;
					}

					case "in": {
						neighborhoodName = smallChild.getChild(3).getText();
						for(int i = 4; i < smallChild.getChildCount() - 2; i++){
							neighborhoodName = neighborhoodName + " " + smallChild.getChild(i).getText();
						}
						searchByNeighborhoods(neighborhoodName);
						break;
					}

					case "rating": {
						rating = smallChild.getChild(2).getText();
						searchByRating(rating);
						break;
					}

					case "price": {
						price = smallChild.getChild(2).getText() + smallChild.getChild(4).getText();
						searchByPrice(price);
						break;
					}

					case "category": {
						category = smallChild.getChild(3).getText();
						for(int i = 4; i < smallChild.getChildCount() - 2; i++){
							category = category + " " + smallChild.getChild(i).getText();
						}
						searchByCategories(category);
						break;
					}
					}
				}
			}
			restaurantName = null;
			neighborhoodName = null;
			rating = null;
			price = null;
			category = null;
		}
		setRestaurants = getRestaurants(setRestaurants);
		return setRestaurants;
	}

	public void searchByNames(String restaurantName){
		for (Restaurant restaurant : allRestaurants){
			String getName = restaurant.getName();
			if (getName == restaurantName && !restaurantByNames.contains(getName)){
				restaurantByNames.add(getName);
			}
		}
	}

	public void searchByNeighborhoods(String neighborhoodName) throws ParseException, IOException{
		for (Restaurant restaurant : allRestaurants){
			String getName = restaurant.getName();
			Set<String> getNeighborhoods = restaurant.getNeighborhoods();
			for (String neighborhood : getNeighborhoods) {
				if (neighborhood == neighborhoodName && !restaurantByNeighborhoods.contains(getName)){
					restaurantByNeighborhoods.add(getName);
				}
			}
		}
	}

	public void searchByRating(String rating) throws ParseException, IOException{
		for (Restaurant restaurant : allRestaurants){
			String getName = restaurant.getName();
			Double getRating = restaurant.getRating();
			if (getRating.toString() == rating && !restaurantByRating.contains(getName)){
				restaurantByRating.add(getName);
			}
		}
	}

	public void searchByPrice(String price) throws ParseException, IOException{
		for (Restaurant restaurant : allRestaurants){
			String getName = restaurant.getName();
			Long getPrice = restaurant.getPrice();
			if (getPrice.toString().charAt(0) >= price.charAt(0) &&
					getPrice.toString().charAt(0) <= price.charAt(1)
					&& !restaurantByPrice.contains(getName)){
				restaurantByPrice.add(getName);
			}
		}
	}

	public void searchByCategories(String category) throws ParseException, IOException{
		for (Restaurant restaurant : allRestaurants){
			String getName = restaurant.getName();
			Set<String> getCategories = restaurant.getCategories();
			for (String categories : getCategories){
				if (categories == category && !restaurantByCategories.contains(getName)){
					restaurantByCategories.add(getName);
				}
			}
		}
	}

	public Set<Restaurant> getRestaurants(Set<Restaurant> setRestaurants){
		for (Restaurant restaurant : allRestaurants){
			String name = restaurant.getName();
			if (restaurantByCategories.contains(name)
					&& restaurantByNames.contains(name)
					&& restaurantByNeighborhoods.contains(name)
					&& restaurantByPrice.contains(name)
					&& restaurantByRating.contains(name)){
				setRestaurants.add(restaurant);
			}
		}
		return setRestaurants;
	}


	public Set<Restaurant> getAllRestaurants(){
		return allRestaurants;
	}
}
