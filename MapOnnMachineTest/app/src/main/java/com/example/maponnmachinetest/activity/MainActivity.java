package com.example.maponnmachinetest.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.maponnmachinetest.R;
import com.example.maponnmachinetest.adapter.ProductListAdapter;
import com.example.maponnmachinetest.databinding.ActivityMainBinding;
import com.example.maponnmachinetest.model.Data;
import com.example.maponnmachinetest.model.ProductListModel;
import com.example.maponnmachinetest.viewmodel.ProductViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    ProductViewModel modelProduct;
    String TAG = this.getClass().getName();
    private ActivityMainBinding activityMainBinding;
    private RecyclerView recyclerProductList;
    private ProductListAdapter adapter;
    private ArrayList<Data> list;
    private int offset = 0, limit = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Product List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        activityMainBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }

    private void init() {
        recyclerProductList = activityMainBinding.recyclerProductList;
        recyclerProductList.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new ProductListAdapter(this, list,false);
        LinearLayoutManager layoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerProductList.setLayoutManager(layoutManagaer);
        recyclerProductList.setAdapter(adapter);
        loadApiData();
    }

    private void loadApiData() {
        modelProduct = new ViewModelProvider(MainActivity.this).get(ProductViewModel.class);
        modelProduct.getHomeData();
        final ProgressDialog pd = new ProgressDialog(this);
        modelProduct.error().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "error aBoolean " + aBoolean);
                if (aBoolean)
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();


            }
        });
        modelProduct.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "isLoading aBoolean " + aBoolean);
                if (aBoolean) {
                    pd.setMessage("Loading.....");
                    pd.show();
                } else {
                    pd.dismiss();
                }
            }
        });
        modelProduct.homeDataReceived().observe(this, new Observer<ProductListModel>() {
            @Override
            public void onChanged(ProductListModel hometBean) {
                Log.d(TAG, "homeBean " + hometBean.toString());
                list.addAll(hometBean.getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuListView:
                LinearLayoutManager layoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerProductList.setLayoutManager(layoutManagaer);
                adapter = new ProductListAdapter(this, list,false);
                recyclerProductList.setAdapter(adapter);

                break;
            case R.id.menuGridView:
                GridLayoutManager gridlayoutManagaer = new GridLayoutManager(MainActivity.this, 2);
                recyclerProductList.setLayoutManager(gridlayoutManagaer);
                adapter = new ProductListAdapter(this, list,true);
                recyclerProductList.setAdapter(adapter);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}
