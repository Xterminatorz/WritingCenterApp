package edu.sjsu.writingcenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Create a view for use in the ActionBar
        TextView actionBarTitle = new TextView(this);
        actionBarTitle.setText("SJSU Writing Center");
        actionBarTitle.setTextColor(Color.WHITE);
        //Create layout parameters to center the text in the ActionBar
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layout.gravity = Gravity.CENTER_HORIZONTAL;
        //Set up the ActionBar to display a custom view
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        getSupportActionBar().setCustomView(actionBarTitle, layout);

        // Setup buttons with their intents for click events
        ImageButton appointmentButton = (ImageButton) findViewById(R.id.appointmentsButton);
        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the browser with the URL to create an appointment
                Intent appointmentIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.appointment_url)));
                startActivity(appointmentIntent);
            }
        });
        appointmentButton.setImageResource(R.drawable.appointments);

        ImageButton resourcesButton = (ImageButton) findViewById(R.id.resourcesButton);
        resourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the resources activity
                Intent resourcesIntent = new Intent(getApplicationContext(), ResourcesActivity.class);
                startActivity(resourcesIntent);

            }
        });
        resourcesButton.setImageResource(R.drawable.resources);

        ImageButton locationButton = (ImageButton) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the location activity
                Intent locationIntent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(locationIntent);
            }
        });
        locationButton.setImageResource(R.drawable.location);

        ImageButton faqButton = (ImageButton) findViewById(R.id.faqButton);
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch the FAQ activity
                Intent faqIntent = new Intent(getApplicationContext(), FAQActivity.class);
                startActivity(faqIntent);
            }
        });
        faqButton.setImageResource(R.drawable.faq);

        ImageButton facebookButton = (ImageButton) findViewById(R.id.facebookButton);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Facebook
            }
        });

        ImageButton twitterButton = (ImageButton) findViewById(R.id.twitterButton);
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //twitter
            }
        });

        ImageButton youtubeButton = (ImageButton) findViewById(R.id.youtubeButton);
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //youtube
            }
        });

        //ImageView wcLogo = (ImageView) findViewById(R.id.wcLogo);
        //wcLogo.setImageResource(R.drawable.pencil);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
