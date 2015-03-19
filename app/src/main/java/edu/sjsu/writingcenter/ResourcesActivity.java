package edu.sjsu.writingcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class ResourcesActivity extends ActionBarActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        new HandoutsData().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // However, we currently don't need the menu.
        // getMenuInflater().inflate(R.menu.menu_resources, menu);
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

    private class HandoutsData extends AsyncTask<Void, Void, Void> {
        HashMap<String, List<Handout>> expandableListDetail = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ResourcesActivity.this);
            mProgressDialog.setTitle("Writing Center Resources");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.sjsu.edu/writingcenter/handouts/index.html").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (doc != null) {
                Elements categories = doc.select(".content_wrapper > div:nth-child(2) > h3");
                for (Element e : categories) {
                    List<Handout> handouts = new ArrayList<>();
                    Element ul = e.nextElementSibling();
                    for (Element f : ul.children().select("a"))
                        handouts.add(new Handout(f.text(), f.attr("abs:href")));
                    expandableListDetail.put(e.text(), handouts);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new ExpandableListAdapter(ResourcesActivity.this, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);

            expandableListView.setOnChildClickListener(new OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    //Download the file in the background, on post execute open it in the PDFActivity
                    LoadPdf loadPdf = new LoadPdf(expandableListDetail.get(
                            expandableListTitle.get(groupPosition)).get(
                            childPosition).getUrl());
                    loadPdf.execute();
                    return false;
                }
            });
            mProgressDialog.dismiss();
        }
    }

    private class LoadPdf extends AsyncTask<Void, Void, Void> {

        private URL url;
        private String fileName;
        File pdf;
        boolean fileReady = false;
        public LoadPdf(String urlString) {
            if(Environment.getExternalStorageDirectory().canWrite()) {
                fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
                pdf = new File(Environment.getExternalStorageDirectory().toString() + "/WritingCenter");
                if (!pdf.exists()) {
                    fileReady = pdf.mkdir();
                }
                pdf = new File(Environment.getExternalStorageDirectory().toString() + "/WritingCenter", fileName);
                if (!pdf.exists()){
                    try {
                        fileReady = pdf.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    fileReady = true;
                }
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException e) {
                    Toast.makeText(getApplicationContext(), "URL Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Cannot access phone storage", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ResourcesActivity.this);
            mProgressDialog.setTitle("Writing Center Resources");
            mProgressDialog.setMessage("Downloading PDF...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if(url != null && fileReady) {
                try {
                    FileUtils.copyURLToFile(url, pdf);
                } catch(IOException e ) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(pdf != null) {
                Intent intent = new Intent(getApplicationContext(), PDFActivity.class);
                intent.putExtra("PDF_FILE", pdf);
                startActivity(intent);
            }
            mProgressDialog.dismiss();
        }
    }
}
