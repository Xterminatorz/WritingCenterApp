package edu.sjsu.writingcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.pdfview.PDFView;

import java.io.File;


public class PDFActivity extends ActionBarActivity {

    private File mPdfFile;

    @Override
    public void onCreate(Bundle params) {
        super.onCreate(params);
        setContentView(R.layout.activity_pdf);

        Intent i = getIntent();
        mPdfFile = (File) i.getSerializableExtra("PDF_FILE");

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);


        if(mPdfFile != null) {
            pdfView.fromFile(mPdfFile)
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(mPdfFile != null) {
            getMenuInflater().inflate(R.menu.menu_pdf, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open_external) {
            //Launch an intent with PDF information
            if(mPdfFile != null) {
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(Uri.fromFile(mPdfFile), "application/pdf");
                startActivity(pdfIntent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
