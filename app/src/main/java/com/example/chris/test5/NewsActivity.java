package com.example.chris.test5;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.example.chris.test5.di.DaggerNewsComponent;
import com.example.chris.test5.model.Article;
import com.example.chris.test5.model.NewsResponse;
import com.example.chris.test5.util.Constants;
import com.example.chris.test5.util.RecyclerAdapter;
import com.example.chris.test5.view.main.NewsContract;
import com.example.chris.test5.view.main.NewsPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class NewsActivity extends AppCompatActivity implements NewsContract.View
{
    
    @Inject
    NewsPresenter presenter;
    
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 10;
    private List<Article> articles;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location locationCurrent;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static final String TAG = NewsActivity.class.getSimpleName() + "_TAG";
    private Map<String, String> map;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DaggerNewsComponent.create().inject(this);
    
        bindViews();
        checkPermission();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        
        
    }
    
    private void bindViews()
    {
        presenter.attachView(this);
        articles = new ArrayList<>();
        String sources = "cbs-news";
        map = new HashMap<>();
        map.put("sources", sources);
        map.put("apiKey", Constants.NEWS_API_KEY);
        
        recyclerView = findViewById(R.id.rvNews);
        
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        
        
        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                for (Location location : locationResult.getLocations())
                {
                    Log.d(TAG, "onLocationResult: " + location.toString());
                }
            }
        };
    }
    
    @Override
    public void showError(String error)
    {
    
    }
    
    @Override
    public void setNewsResponse(NewsResponse newsResponse)
    {
        Log.d(TAG, "setNewsResponse: ");
        articles = newsResponse.getArticles();
    
        recyclerAdapter = new RecyclerAdapter(articles);
        recyclerView.setAdapter(recyclerAdapter);
    }
    
    @Override
    public void showProgress(String progress)
    {
    
    }
    
    
    private void checkPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS))
            {
                
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                
            }
            else
            {
                
                // No explanation needed, we can request the permission.
                
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                
                // MY_PERMISSIONS_REQUEST_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            getLocation();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d(TAG, "onRequestPermissionsResult: ");
                    getLocation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    
                }
                else
                {
                    Log.d(TAG, "onRequestPermissionsResult: denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    
    @SuppressLint("MissingPermission")
    public void getLocation()
    {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(NewsActivity.this, new OnSuccessListener<Location>()
                {
                    
                    
                    
                    @Override
                    public void onSuccess(Location location)
                    {
                        Log.d(TAG, "onSuccess: " + location.toString());
//                        tvCurrentLocation.setText(location.getLatitude() + " " + location.getLongitude());
                        locationCurrent = location;
    
                        presenter.getNewsResponse(map);
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                    
                    }
                });
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        presenter.detachView();
    }
}
