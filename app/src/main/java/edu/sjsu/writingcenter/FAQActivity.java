package edu.sjsu.writingcenter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAQActivity extends ActionBarActivity {

    ExpandableListView expandableListView;
    FAQExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        new FAQData().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // However, we currently don't need the menu.
        //getMenuInflater().inflate(R.menu.menu_faq, menu);
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

    private class FAQData extends AsyncTask<Void, Void, Void> {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(FAQActivity.this);
            mProgressDialog.setTitle("Writing Center FAQs");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.sjsu.edu/writingcenter/faq/index.html").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (doc != null) {
                Elements questions = doc.select(".content_wrapper > div:nth-child(2) > h3");
                for (Element question : questions) {
                    if (question.text().contains("Where is the Writing Center"))
                        continue;
                    List<String> text = new ArrayList<>();
                    Element el = question.nextElementSibling();
                    while (el != null && el.nodeName().equals("p")) {
                        text.add(el.text());
                        el = el.nextElementSibling();
                        if (el != null && "ul".equals(el.nodeName()))
                            for (Element li : el.children())
                                text.add(li.text());
                    }
                    expandableListDetail.put(question.text(), text);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            expandableListView = (ExpandableListView) findViewById(R.id.FAQExpandableListView);
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new FAQExpandableListAdapter(FAQActivity.this, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
            mProgressDialog.dismiss();
        }
    }

}
