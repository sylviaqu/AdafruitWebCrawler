package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import entity.Item.ItemBuilder;

public class AdafruitProductAPI {
	private static final String URL = "https://www.adafruit.com/api/products";
	private static final String DEFAULT_TERM = ""; // no restriction
	//private static final String API_KEY = "";
	
	public List<Item> search(String term){
		//Encode term in url since it may contain special characters
		if (term == null) {
			term = DEFAULT_TERM;
		}
		try {
			term = java.net.URLEncoder.encode(term, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//call AdafruitProductAPI
		try {
			// Open a HTTP connection between Java application and adafruit based on url
			HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
			// Set requrest method to GET
			connection.setRequestMethod("GET");
			// Send request to Adafruit and get response, response code could be returned directly
			// response body is saved in InputStream of connection.
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + URL);
			System.out.println("Response Code : " + responseCode);
			
			// Now read response body to get events data
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));		
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//get all products from API and print as JSON object
			JSONArray array = new JSONArray(response.toString());
			/*try {
			    for (int i = 0; i < array.length(); i++) {
			        JSONObject event = array.getJSONObject(i);
			        //System.out.println(event);
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			return getItemList(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("search api error");
		return new ArrayList<>();
	}
	
	private List<Item> getItemList(JSONArray array) throws JSONException {
		List<Item> itemList = new ArrayList<>();
		for (int i = 0; i < array.length(); ++i) {
			JSONObject product = array.getJSONObject(i);
			ItemBuilder builder = new ItemBuilder();
			if (!product.isNull("product_id")) {
				builder.setItemId(product.getString("product_id"));
			}
			if (!product.isNull("product_name")) {
				builder.setName(product.getString("product_name"));
			}
			if (!product.isNull("product_stock")) {
				try{
					if(product.getString("product_stock").equals("in stock")){
					// need check
					builder.setStock_num(-1);
					}
				}catch (Exception e){
					builder.setStock_num(product.getInt("product_stock"));
				}
			}
			if (!product.isNull("product_model")) {
				builder.setModel(product.getString("product_model"));
			}
			if (!product.isNull("product_mpn")) {
				builder.setMpn(product.getString("product_mpn"));
			}
			if (!product.isNull("product_master_category")) {
				builder.setCategories(product.getInt("product_master_category"));
			}
			if (!product.isNull("product_manufacturer")) {
				builder.setManufacturer(product.getString("product_manufacturer"));
			}
			if (!product.isNull("product_price")) {
				builder.setPrice(product.getString("product_price"));
			}
			if (!product.isNull("product_coo")) {
				builder.setCountry(product.getString("product_coo"));
			}
			if (!product.isNull("product_image")) {
				builder.setImageUrl(product.getString("product_image"));
			}
			if (!product.isNull("product_url")) {
				builder.setUrl(product.getString("product_url"));
			}

			Item item = builder.build();
			itemList.add(item);	
		}
		return itemList;
	}
	
	public static void main(String[] args) {
		AdafruitProductAPI tmApi = new AdafruitProductAPI();
		// Mountain View, CA
		// tmApi.queryAPI(37.38, -122.08);
		// Houston, TX
		List<Item> result= tmApi.search(null);
		for (Item item : result) {
			JSONObject jsonObject = item.toJSONObject();
			System.out.println(jsonObject);
		}

	}

}
