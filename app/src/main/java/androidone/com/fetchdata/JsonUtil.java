package androidone.com.fetchdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hpola on 10/8/2017.
 */

public class JsonUtil
{
    public static JSONArray getJsonArray(JSONObject results, String key) throws JSONException {

        if(results.has(key) && results.get(key)!= null)
        {
            return results.getJSONArray(key);
        }
        return new JSONArray();
    }
    public static String getJsonString(JSONObject results,String key) throws JSONException {
        if(results.has(key) && !results.isNull(key) && results.getString(key) != null)
        {
           return results.getString(key);
        }
        return " ";
    }
}
