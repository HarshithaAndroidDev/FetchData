package androidone.com.fetchdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidone.com.fetchdata.MovieDescriptionActivity.MOVIE_ID;


public class MainActivity extends AppCompatActivity implements ViewAdapter.ItemclickListener {
    RecyclerView recyclerView;//created
    ArrayList<MovieItem> movieItems ;
    ViewAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fetchData();

    }
    private void init()
    {
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_1);//initialized
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));//step 2
        movieItems=new ArrayList<MovieItem>();
        viewAdapter=new ViewAdapter(this,movieItems);
        recyclerView.setAdapter(viewAdapter);
    }
    private void fetchData()
    {
        String url ="http://api.themoviedb.org/3/movie/now_playing?api_key=ce011fa6eca448775cf42ad614c86548";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response","Response :"+response);
                try {
                    parseResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error","Error :"+error);
            }
        });
        VolleyRequestSingleton.getRequestQueue(this).add(jsonObjectRequest);

    }
    private void parseResponse(JSONObject response) throws JSONException {
        JSONArray myJson = JsonUtil.getJsonArray(response,"results");

        for(int i=0;i<myJson.length();i++)
        {

            JSONObject currentObject=myJson.getJSONObject(i);
            MovieItem movieItem = new MovieItem();
            JsonUtil.getJsonString(currentObject,"title");
            movieItem.setMovieId(JsonUtil.getJsonString(currentObject,"id"));
            movieItem.setMovieName(JsonUtil.getJsonString(currentObject,"title"));
            movieItem.setReleaseDate(JsonUtil.getJsonString(currentObject,"release_date"));
            movieItem.setMoviePoster("https://image.tmdb.org/t/p/w500/"+ JsonUtil.getJsonString(currentObject,"poster_path"));
            movieItems.add(movieItem);
        }
        viewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(String MovieId) {
        Intent i=new Intent(MainActivity.this,MovieDescriptionActivity.class);
        i.putExtra(MOVIE_ID,MovieId);
        startActivity(i);

    }
}
