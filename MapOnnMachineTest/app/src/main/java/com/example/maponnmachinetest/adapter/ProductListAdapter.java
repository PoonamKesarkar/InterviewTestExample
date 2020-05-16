package com.example.maponnmachinetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.maponnmachinetest.R;
import com.example.maponnmachinetest.databinding.GridListProductItemBinding;
import com.example.maponnmachinetest.databinding.ProductListItemBinding;
import com.example.maponnmachinetest.model.Data;
import com.example.maponnmachinetest.model.ProductListModel;
import com.example.maponnmachinetest.util.CommonMethods;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Data> listdata;
    private Context context;
    private Boolean isGridView;

    public ProductListAdapter(Context context, ArrayList<Data> listdata,Boolean isGridView) {
        this.listdata = listdata;
        this.context = context;
        this.isGridView = isGridView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(isGridView){
            GridListProductItemBinding listItemBinding= DataBindingUtil.inflate(layoutInflater, R.layout.grid_list_product_item,parent,false);
            GridViewHolder viewHolder = new GridViewHolder(listItemBinding);
            return viewHolder;
        }else{
            ProductListItemBinding listItemBinding= DataBindingUtil.inflate(layoutInflater, R.layout.product_list_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(listItemBinding);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isGridView){
            GridViewHolder gridviewHolder = (GridViewHolder)holder;
            gridviewHolder.listItemBinding.setProductGridList(listdata.get(position));
            if (new CommonMethods().isInternetConnection(context)) {
                //Used glide for loading image
                Glide.with(context)
                        .load(listdata.get(position).getImage())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(gridviewHolder.listItemBinding.imgProduct);

            } else {
                gridviewHolder.listItemBinding.imgProduct.setBackgroundResource(R.drawable.ic_launcher_background);
            }
        }else{
            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.listItemBinding.setProductList(listdata.get(position));
            if (new CommonMethods().isInternetConnection(context)) {
                //Used glide for loading image
                Glide.with(context)
                        .load(listdata.get(position).getImage())
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(viewHolder.listItemBinding.imgProduct);

            } else {
                viewHolder.listItemBinding.imgProduct.setBackgroundResource(R.drawable.ic_launcher_background);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ProductListItemBinding listItemBinding;

        public ViewHolder(@NonNull ProductListItemBinding itemView) {
            super(itemView.getRoot());
            listItemBinding = itemView;
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        private GridListProductItemBinding listItemBinding;

        public GridViewHolder(@NonNull GridListProductItemBinding itemView) {
            super(itemView.getRoot());
            listItemBinding = itemView;
        }
    }
}
