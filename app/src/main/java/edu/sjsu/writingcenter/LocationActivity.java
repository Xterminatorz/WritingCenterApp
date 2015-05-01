package edu.sjsu.writingcenter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


public class LocationActivity extends ActionBarActivity {
    public static Typeface Font;
    private static boolean showingFloorPlan = false;
    private static final String campusMapUrl = "file:///android_res/drawable/campusmap.png";
    private static final String floorPlanMapUrl = "file:///android_res/drawable/clarkfloorplan.jpg";
    private WebView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Create a view for use in the ActionBar
        TextView actionBarTitle = new TextView(this);
        actionBarTitle.setText("Writing Center Location");
        actionBarTitle.setTextColor(Color.WHITE);
        //Create layout parameters to center the text in the ActionBar
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layout.gravity = Gravity.CENTER_HORIZONTAL;
        //Set up the ActionBar to display a custom view
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55000000")));
        getSupportActionBar().setCustomView(actionBarTitle, layout);

        final Button mapToggle = (Button) findViewById(R.id.mapToggle);
        mapToggle.setText("Clark Hall floor plan");
        mapToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapView != null) {
                    if (showingFloorPlan) {
                        mapView.loadUrl(campusMapUrl);
                        mapToggle.setText("Clark Hall floor plan");
                        showingFloorPlan = false;
                    } else {
                        mapView.loadUrl(floorPlanMapUrl);
                        mapToggle.setText("Campus map");
                        showingFloorPlan = true;
                    }
                }
            }
        });

        mapView = (WebView) findViewById(R.id.mapView);
        mapView.loadUrl(campusMapUrl);
        WebSettings webSettings = mapView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        mapView.setBackgroundColor(Color.TRANSPARENT);
        mapView.setWebViewClient(new WebViewClient());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // However, we currently don't need the menu.
        //getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
