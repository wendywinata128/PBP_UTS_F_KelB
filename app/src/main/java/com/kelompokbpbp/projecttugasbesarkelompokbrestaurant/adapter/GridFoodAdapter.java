package com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.R;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.databinding.ItemGridFoodBinding;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.model.Menu;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.model.User;

import java.util.ArrayList;
import java.util.List;

public class GridFoodAdapter extends RecyclerView.Adapter<GridFoodAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Menu> listMenu;
    private List<Menu> filterMenu;

    public GridFoodAdapter(Context context, List<Menu> listMenu){
        this.context = context;
        this.listMenu = listMenu;
        filterMenu = new ArrayList<>();
        filterMenu.addAll(listMenu);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemGridFoodBinding itemGridFoodBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_grid_food, parent, false);

        return new MyViewHolder(itemGridFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Menu menu = listMenu.get(position);
        holder.bind(menu);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemGridFoodBinding binding;

        public MyViewHolder(@NonNull ItemGridFoodBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Menu menu) {
            binding.setMenu(menu);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                Log.d("MASUK","Perform Filtering " +charSequence);

                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = filterMenu.size();
                    filterResults.values = filterMenu;
                    Log.d("MASUK","Filter Empty " +charSequence);
                }else{
                    Log.d("MASUK","Filter loaded " +charSequence);
                    List<Menu> filterList = new ArrayList<>();
                    for(Menu menu : filterMenu){
                        if(menu.getNama().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filterList.add(menu);
                            Log.d("TEST","MASUK");
                        }
                        Log.d("TEST","LOOP");
                    }
                    filterResults.count = filterList.size();
                    filterResults.values = filterList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listMenu.clear();
                listMenu.addAll((List<Menu>)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}