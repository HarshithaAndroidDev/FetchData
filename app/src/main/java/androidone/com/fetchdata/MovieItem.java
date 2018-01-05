package androidone.com.fetchdata;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hpola on 10/4/2017.
 */

public class MovieItem {

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String MovieId;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }



    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @SerializedName("title")
    private String movieName;

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }
    @SerializedName("poster_path")
    private String moviePoster;
    @SerializedName("release_date")
    private String releaseDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @SerializedName("overview")
    private String description;

}
