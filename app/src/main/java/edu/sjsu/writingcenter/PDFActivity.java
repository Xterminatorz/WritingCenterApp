package edu.sjsu.writingcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.joanzapata.pdfview.PDFView;

import java.io.File;


public class PDFActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle params) {
        super.onCreate(params);
        setContentView(R.layout.activity_pdf);

        Intent i = getIntent();
        File f = (File) i.getSerializableExtra("PDF_FILE");

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);


        if(f != null) {
            pdfView.fromFile(f)
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        }
    }

}
