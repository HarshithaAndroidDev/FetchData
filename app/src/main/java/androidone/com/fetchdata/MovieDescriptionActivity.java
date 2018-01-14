package androidone.com.fetchdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MovieDescriptionActivity extends AppCompatActivity {
    private TextView releaseDate,Description,MovieName,voteAverage;
    private ImageView poster;
    private Gson gson;
    private String MovieID;
    public static final String MOVIE_ID= "MovieID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        MovieID =getIntent().getStringExtra(MOVIE_ID);
        releaseDate=(TextView)findViewById(R.id.releasedate);
        Description=(TextView)findViewById(R.id.description);
        voteAverage=(TextView)findViewById(R.id.voteAverage);
        MovieName=(TextView)findViewById(R.id.TitleName);
        poster=(ImageView) findViewById(R.id.poster_name);
        gson = new Gson();
        fetchMovieParams();
    }
    private void fetchMovieParams()
    {
        String url="https://api.themoviedb.org/3/movie/"+MovieID+"?api_key=ce011fa6eca448775cf42ad614c86548";
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parsedata(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyRequestSingleton.getRequestQueue(this).add(jsonObjectRequest);

    }
    private void parsedata(JSONObject results)
    {
       MovieItem movieItem = gson.fromJson(results.toString(),MovieItem.class);
        releaseDate.setText(movieItem.getReleaseDate());
        Description.setText(movieItem.getDescription());
        voteAverage.setText(movieItem.getVoteAverage());
        MovieName.setText(movieItem.getMovieName());
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500/"+movieItem.getMoviePoster()).error(R.drawable.movieimage).placeholder(R.drawable.movieimage).into(poster);

    }
}
