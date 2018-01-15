package androidone.com.fetchdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
    public static String url="http://api.themoviedb.org/3/movie/now_playing?api_key=ce011fa6eca448775cf42ad614c86548";
    ArrayList<MovieItem> movieItems ;
    Button filterButton;
    Spinner spinner;
    ArrayAdapter<String> sortValues;
    ViewAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity","\n onCreate()");
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSortSelected();
        Log.d("Activity","\n onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        onSortSelected();
        Log.d("Activity","\n onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        onSortSelected();
        Log.d("Activity","\n onPause()");
    }


    private void init()
    {
        spinner=(Spinner)findViewById(R.id.SortSpinner);
        sortValues=new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.sort_types));
        sortValues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortValues);
        filterButton=(Button)findViewById(R.id.filterButton);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_1);//initialized
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));//step 2
        movieItems=new ArrayList<MovieItem>();
        viewAdapter=new ViewAdapter(this,movieItems);
        recyclerView.setAdapter(viewAdapter);
        Log.d("Activity1","\n init()");
    }

    private void fetchData(String url)
    {
        Log.d("Activity1","\n fetchData()");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response","Response :"+response);
                Log.d("Activity1","\n onResponse()-AsyncTask");
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
        Log.d("Activity1","\n parseResponse():to display the recyclerview data");
        for(int i=0;i<myJson.length();i++)
        {

            JSONObject currentObject=myJson.getJSONObject(i);
            MovieItem movieItem = new MovieItem();
            JsonUtil.getJsonString(currentObject,"title");
            movieItem.setMovieId(JsonUtil.getJsonString(currentObject,"id"));
            movieItem.setMovieName(JsonUtil.getJsonString(currentObject,"title"));
            movieItem.setReleaseDate(JsonUtil.getJsonString(currentObject,"release_date"));
            movieItem.setMoviePoster("https://image.tmdb.org/t/p/w500/"+ JsonUtil.getJsonString(currentObject,"poster_path"));
            movieItem.setVoteAverage(JsonUtil.getJsonString(currentObject,"vote_average"));
            movieItems.add(movieItem);
        }
        viewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(String MovieId) {
        Log.d("Activity1","\n onItemClick(): when movie is clicked");
        Intent i=new Intent(MainActivity.this,MovieDescriptionActivity.class);
        i.putExtra(MOVIE_ID,MovieId);
        startActivity(i);

    }
    private void onSortSelected()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                    url="https://api.themoviedb.org/3/discover/movie?api_key=af7585c98115bbf645f463276b9b072b&language=en-US&sort_by=popularity.desc";
                }
                else if(i==2)
                {
                    url="https://api.themoviedb.org/3/discover/movie?api_key=af7585c98115bbf645f463276b9b072b&language=en-US&sort_by=vote_average.desc";
                }
                viewAdapter.notifyItemChanged(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData(url);
            }
        });

    }
}
