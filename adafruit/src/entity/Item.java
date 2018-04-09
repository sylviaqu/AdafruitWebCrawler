package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	private String itemId;
	private String name;
	
	private int stock_num;
	
	private String model;
	private String mpn;
	private int master_categories;
	private String manufacturer;
	private String price;
	private String country;
	private String imageUrl;
	private String url;
	
	
	public static class ItemBuilder {
		private String itemId;
		private String name;		
		private int stock_num;
		private String model;
		private String mpn;
		private int master_categories;
		private String manufacturer;
		private String price;
		private String country;
		private String imageUrl;
		private String url;
		
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setStock_num(int stock_num) {
			this.stock_num = stock_num;
		}
		
		public void setModel(String model){
			this.model = model;
		}
		public void setMpn(String mpn){
			this.mpn = mpn;
		}
		public void setCategories(int master_categories) {
			this.master_categories = master_categories;
		}
		public void setManufacturer(String manufacturer){
			this.manufacturer = manufacturer;
		}
		public void setPrice(String price){
			this.price = price;
		}
		public void setCountry(String country){
			this.country = country;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		public Item build() {
			return new Item(this);
		}


	}

	
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.stock_num = builder.stock_num;
		this.model = builder.model;
		this.mpn = builder.mpn;
		this.master_categories = builder.master_categories;
		this.manufacturer = builder.manufacturer;
		this.price = builder.price;
		this.country = builder.country;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
	}

	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId);
			obj.put("name", name);
			obj.put("model", model);
			obj.put("mpn", mpn);
			obj.put("categories", master_categories);
			obj.put("manufacturer", manufacturer);
			obj.put("price", price);
			obj.put("country", country);
			obj.put("image_url", imageUrl);
			obj.put("url", url);
			obj.put("stock", stock_num);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	
	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public int getStock_num() {
		return stock_num;
	}
	public String getModel() {
		return model;
	}
	public String getMpn() {
		return mpn;
	}
	public int getMaster_categories() {
		return master_categories;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getPrice() {
		return price;
	}
	public String getCountry() {
		return country;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getUrl() {
		return url;
	}
	
}
