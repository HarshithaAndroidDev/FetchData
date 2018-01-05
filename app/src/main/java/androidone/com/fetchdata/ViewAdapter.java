package androidone.com.fetchdata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hpola on 10/4/2017.
 */

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
    ArrayList<MovieItem> movielist;
    ItemclickListener itemclickListener;
    Context context;

    public ViewAdapter(Context context,ArrayList<MovieItem> movielist)
    {
        this.context =context;
        this.movielist=movielist;
        this.itemclickListener=(ItemclickListener)context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.viewholder,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.movieName.setText(movielist.get(position).getMovieName());
        holder.releaseDate.setText(movielist.get(position).getReleaseDate());
        if(! movielist.get(position).getMoviePoster().isEmpty())
        {
            Picasso.with(context).load(movielist.get(position).getMoviePoster()).fit().centerCrop().into(holder.moviePoster);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.onItemClick(movielist.get(position).getMovieId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movielist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView movieName,releaseDate;
        ImageView moviePoster;
        View container;
        public MyViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView)itemView.findViewById(R.id.image_icon);
            releaseDate = (TextView)itemView.findViewById(R.id.releasedate);
            movieName = (TextView)itemView.findViewById(R.id.moviename);
            container=itemView;
        }


    }
    public interface ItemclickListener
    {
        public void onItemClick(String MovieId);
    }

}
