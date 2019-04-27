package com.example.galleryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.galleryapp.remoteDataSource.APIInterface;
import com.example.galleryapp.remoteDataSource.ImageDetails;
import com.example.galleryapp.remoteDataSource.Photos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mGridView;
    private ArrayList<SingleGridItem> mListOfItem = new ArrayList<>();
    private LinearLayout mProgressLayout;
    private ImageView mMenuButton;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private static final String BASE_URL="https://api.flickr.com/services/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.grid_view);
        mProgressLayout = findViewById(R.id.progress_layout);
        mMenuButton = findViewById(R.id.menu_button);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        getDataFromAPI();

        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(this);
    }







    private void getDataFromAPI() {
        checkPermissions();
        startProgressBar();
        //get data from retrofit and then populate and stop progressBar()
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);

        String url = "rest/?" +
                "method=flickr.photos.getRecent&per_page=20&page=1&" +
                "api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s";

        Call<Photos> getPhoto = apiInterface.getPhotoDetails(url);

        getPhoto.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                if(response.body()!=null) {
                    for(ImageDetails imageDetails: response.body().getPhotos().getPhoto()){
                        mListOfItem.add(new SingleGridItem(imageDetails.getUrl(),
                                imageDetails.getTitle(),
                                imageDetails.getId()));
                        Log.w("MainActivity","Image url:"+imageDetails.getUrl());
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No data available!", Toast.LENGTH_SHORT).show();
                }
                endProgressBar();
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                endProgressBar();
            }
        });
    }

    private void checkPermissions() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1234);
        }
    }

    private void endProgressBar() {
        mGridView.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.GONE);
        mGridView.setLayoutManager(new GridLayoutManager(this, 2,
                LinearLayout.VERTICAL, false));
        mGridView.setHasFixedSize(true);
        CustomAdapter adapter = new CustomAdapter(MainActivity.this, mListOfItem);
        mGridView.setAdapter(adapter);

    }

    private void startProgressBar() {
        mGridView.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1234:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.w("MainActivity","Granted Permission!");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Grant Permissions", Toast.LENGTH_SHORT).show();
                    checkPermissions();
                }
                return;
            }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        if(menuItem.getItemId() == R.id.search){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }else if(menuItem.getItemId() == R.id.home){
            if(!menuItem.getClass().getSimpleName().equals("MainActivity")){

            }
        }
        return true;
    }
}
