package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import external.AdafruitProductAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Term can be empty or null.
		String term = request.getParameter("term");
		AdafruitProductAPI tmAPI = new AdafruitProductAPI();
		List<Item> items = tmAPI.search(term);
		
		JSONObject result = new JSONObject();
		JSONArray best = new JSONArray();
		JSONArray well = new JSONArray();
		JSONArray common = new JSONArray();
		try {
			result.put("bestSeller", best);	
			result.put("wellSeller", well);
			result.put("commonSeller", common);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			for (Item item : items) {
				// Add a thin version of item object
				JSONObject obj = item.toJSONObject();
				if(obj.getInt("stock") == 0){
					best.put(obj);
				}else if(obj.getInt("stock") != -1){
					well.put(obj);
				}
				else{
					common.put(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//JSONArray result = new JSONArray();
		//result.put(bestSeller);
		//result.put(wellSeller);
		//result.put(commonSeller);

		RpcHelper.writeJsonObject(response, result);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
