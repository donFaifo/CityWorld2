package me.mguerrero.cityworld2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.mguerrero.cityworld2.R;
import me.mguerrero.cityworld2.models.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<City> cities;
    private CityAdapter.OnItemClickListener listener;

    public CityAdapter(List<City> cities, CityAdapter.OnItemClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(cities.get(position).getName());
        holder.description.setText(cities.get(position).getDescription());
        Picasso.get().load(cities.get(position).getImageLink()).fit().into(holder.cityImage);
        holder.rating.setText(String.valueOf(cities.get(position).getRating()));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteButtonClick(cities.get(position), position);
            }
        });
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCardClick(cities.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    /*-- VIEWHOLDER CLASS --*/
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView description;
        public TextView deleteButton;
        public CardView cardItem;
        public ImageView cityImage;
        public TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCityName);
            description = itemView.findViewById(R.id.textViewCityDescription);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            cardItem = itemView.findViewById(R.id.cardItem);
            cityImage = itemView.findViewById(R.id.imageViewCItyImage);
            rating = itemView.findViewById(R.id.textViewRating);
        }
    }

    public interface OnItemClickListener {
        void onDeleteButtonClick(City city, int position);
        void onCardClick(City city, int position);
    }

}
