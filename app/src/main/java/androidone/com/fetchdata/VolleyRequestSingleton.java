package androidone.com.fetchdata;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hpola on 10/5/2017.
 */

public class VolleyRequestSingleton
{
   private static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue(Context context)//creates a request queue,
    {
        if(requestQueue == null)
        {
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

}
