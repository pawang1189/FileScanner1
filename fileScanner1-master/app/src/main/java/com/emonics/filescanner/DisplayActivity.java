package com.emonics.filescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cadmium on 2/5/18.
 */

public class DisplayActivity extends AppCompatActivity {
    Bundle extras;
    List<Map<String, Serializable>> frequentExtensions = new ArrayList<Map<String, Serializable>>();
    private List<File> highestSizedFilesList = new ArrayList<File>();

    private TextView txtViewDescr1FileSize, txtViewDescr2FileSize, txtViewFileSizefileName1, txtViewFileSizefileName2, txtViewFileSizefileName3, txtViewFileSizefileName4, txtViewFileSizefileName5, txtViewFileSizefileName6, txtViewFileSizefileName7, txtViewFileSizefileName8, txtViewFileSizefileName9, txtViewFileSizefileName10;
    private TextView txtViewFileSizefileSize1, txtViewFileSizefileSize2, txtViewFileSizefileSize3, txtViewFileSizefileSize4, txtViewFileSizefileSize5, txtViewFileSizefileSize6, txtViewFileSizefileSize7, txtViewFileSizefileSize8, txtViewFileSizefileSize9, txtViewFileSizefileSize10;
    private TextView txtViewFileExtenType1, txtViewFileExtenType2, txtViewFileExtenType3, txtViewFileExtenType4, txtViewFileExtenType5;
    private TextView txtViewFileExtenFreq1, txtViewFileExtenFreq2, txtViewFileExtenFreq3, txtViewFileExtenFreq4, txtViewFileExtenFreq5, txtViewavgFileSize;
    private TableRow tblRowfile1,tblRowfile2,tblRowfile3,tblRowfile4,tblRowfile5,tblRowfile6,tblRowfile7,tblRowfile8,tblRowfile9,tblRowfile10,tblRowExt1,tblRowExt2,tblRowExt3,tblRowExt4,tblRowExt5;
    private String shareHead1 = "\nLargest 10 files are \n\n";
    private String shareHead1Text = "";
    private String shareHead2 = "\nAverage file size : ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        txtViewDescr1FileSize = (TextView) findViewById(R.id.displyActvty_txtView_descr1);
        txtViewDescr2FileSize = (TextView) findViewById(R.id.displyActvty_txtView_descr2);
        txtViewFileSizefileName1 = (TextView) findViewById(R.id.displyActvty_txtView_fileName1);
        txtViewFileSizefileName2 = (TextView) findViewById(R.id.displyActvty_txtView_fileName2);
        txtViewFileSizefileName3 = (TextView) findViewById(R.id.displyActvty_txtView_fileName3);
        txtViewFileSizefileName4 = (TextView) findViewById(R.id.displyActvty_txtView_fileName4);
        txtViewFileSizefileName5 = (TextView) findViewById(R.id.displyActvty_txtView_fileName5);
        txtViewFileSizefileName6 = (TextView) findViewById(R.id.displyActvty_txtView_fileName6);
        txtViewFileSizefileName7 = (TextView) findViewById(R.id.displyActvty_txtView_fileName7);
        txtViewFileSizefileName8 = (TextView) findViewById(R.id.displyActvty_txtView_fileName8);
        txtViewFileSizefileName9 = (TextView) findViewById(R.id.displyActvty_txtView_fileName9);
        txtViewFileSizefileName10 = (TextView) findViewById(R.id.displyActvty_txtView_fileName10);

        txtViewFileSizefileSize1 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize1);
        txtViewFileSizefileSize2 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize2);
        txtViewFileSizefileSize3 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize3);
        txtViewFileSizefileSize4 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize4);
        txtViewFileSizefileSize5 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize5);
        txtViewFileSizefileSize6 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize6);
        txtViewFileSizefileSize7 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize7);
        txtViewFileSizefileSize8 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize8);
        txtViewFileSizefileSize9 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize9);
        txtViewFileSizefileSize10 = (TextView) findViewById(R.id.displyActvty_txtView_fileSize10);

        txtViewFileExtenType1 = (TextView) findViewById(R.id.displyActvty_txtView_fileExten1);
        txtViewFileExtenType2 = (TextView) findViewById(R.id.displyActvty_txtView_fileExten2);
        txtViewFileExtenType3 = (TextView) findViewById(R.id.displyActvty_txtView_fileExten3);
        txtViewFileExtenType4 = (TextView) findViewById(R.id.displyActvty_txtView_fileExten4);
        txtViewFileExtenType5 = (TextView) findViewById(R.id.displyActvty_txtView_fileExten5);

        txtViewFileExtenFreq1 = (TextView) findViewById(R.id.displyActvty_txtView_fileExtenFreq1);
        txtViewFileExtenFreq2 = (TextView) findViewById(R.id.displyActvty_txtView_fileExtenFreq2);
        txtViewFileExtenFreq3 = (TextView) findViewById(R.id.displyActvty_txtView_fileExtenFreq3);
        txtViewFileExtenFreq4 = (TextView) findViewById(R.id.displyActvty_txtView_fileExtenFreq4);
        txtViewFileExtenFreq5 = (TextView) findViewById(R.id.displyActvty_txtView_fileExtenFreq5);

        txtViewavgFileSize = (TextView) findViewById(R.id.displyActvty_txtView_averageFilesize);

        tblRowExt1 = (TableRow)findViewById(R.id.tblRow_fileext1);
        tblRowExt2 = (TableRow)findViewById(R.id.tblRow_fileext2);
        tblRowExt3 = (TableRow)findViewById(R.id.tblRow_fileext3);
        tblRowExt4 = (TableRow)findViewById(R.id.tblRow_fileext4);
        tblRowExt5 = (TableRow)findViewById(R.id.tblRow_fileext5);

        tblRowfile1 = (TableRow)findViewById(R.id.tblRow_fileSize1);
        tblRowfile2 = (TableRow)findViewById(R.id.tblRow_fileSize2);
        tblRowfile3 = (TableRow)findViewById(R.id.tblRow_fileSize3);
        tblRowfile4 = (TableRow)findViewById(R.id.tblRow_fileSize4);
        tblRowfile5 = (TableRow)findViewById(R.id.tblRow_fileSize5);
        tblRowfile6 = (TableRow)findViewById(R.id.tblRow_fileSize6);
        tblRowfile7 = (TableRow)findViewById(R.id.tblRow_fileSize7);
        tblRowfile8 = (TableRow)findViewById(R.id.tblRow_fileSize8);
        tblRowfile9 = (TableRow)findViewById(R.id.tblRow_fileSize9);
        tblRowfile10 = (TableRow)findViewById(R.id.tblRow_fileSize10);



        Intent intent = getIntent();
        extras = intent.getExtras();
        frequentExtensions = (List<Map<String, Serializable>>) extras.getSerializable("FREQUENT_EXTENS");
        highestSizedFilesList = (List<File>) extras.getSerializable("HIGHEST_SIZEDFILES");

        if (highestSizedFilesList.size() > 0) {
            tblRowfile1.setVisibility(View.VISIBLE);
            txtViewFileSizefileName1.setText(highestSizedFilesList.get(0).getName());
            txtViewFileSizefileSize1.setText(highestSizedFilesList.get(0).length() / 1024 + "kb");
        } else {
            tblRowfile1.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 1) {
            tblRowfile2.setVisibility(View.VISIBLE);
            txtViewFileSizefileName2.setText(highestSizedFilesList.get(1).getName());
            txtViewFileSizefileSize2.setText(highestSizedFilesList.get(1).length() / 1024 + "kb");
        } else {
            tblRowfile2.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 2) {
            tblRowfile3.setVisibility(View.VISIBLE);
            txtViewFileSizefileName3.setText(highestSizedFilesList.get(2).getName());
            txtViewFileSizefileSize3.setText(highestSizedFilesList.get(2).length() / 1024 + "kb");
        } else {
            tblRowfile3.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 3) {
            tblRowfile4.setVisibility(View.VISIBLE);
            txtViewFileSizefileName4.setText(highestSizedFilesList.get(3).getName());
            txtViewFileSizefileSize4.setText(highestSizedFilesList.get(3).length() / 1024 + "kb");
        } else {
            tblRowfile4.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 4) {
            tblRowfile5.setVisibility(View.VISIBLE);
            txtViewFileSizefileName5.setText(highestSizedFilesList.get(4).getName());
            txtViewFileSizefileSize5.setText(highestSizedFilesList.get(4).length() / 1024 + "kb");
        } else {
            tblRowfile5.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 5) {
            tblRowfile6.setVisibility(View.VISIBLE);
            txtViewFileSizefileName6.setText(highestSizedFilesList.get(5).getName());
            txtViewFileSizefileSize6.setText(highestSizedFilesList.get(5).length() / 1024 + "kb");
        } else {
            tblRowfile6.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 6) {
            tblRowfile7.setVisibility(View.VISIBLE);
            txtViewFileSizefileName7.setText(highestSizedFilesList.get(6).getName());
            txtViewFileSizefileSize7.setText(highestSizedFilesList.get(6).length() / 1024 + "kb");
        } else {
            tblRowfile7.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 7) {
            tblRowfile8.setVisibility(View.VISIBLE);
            txtViewFileSizefileName8.setText(highestSizedFilesList.get(7).getName());
            txtViewFileSizefileSize8.setText(highestSizedFilesList.get(7).length() / 1024 + "kb");
        } else {
            tblRowfile8.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 8) {
            tblRowfile9.setVisibility(View.VISIBLE);
            txtViewFileSizefileName9.setText(highestSizedFilesList.get(8).getName());
            txtViewFileSizefileSize9.setText(highestSizedFilesList.get(8).length() / 1024 + "kb");
        } else {
            tblRowfile9.setVisibility(View.GONE);
        }

        if (highestSizedFilesList.size() > 9) {
            tblRowfile10.setVisibility(View.VISIBLE);
            txtViewFileSizefileName10.setText(highestSizedFilesList.get(9).getName());
            txtViewFileSizefileSize10.setText(highestSizedFilesList.get(9).length() / 1024 + "kb");
        } else {
            tblRowfile10.setVisibility(View.GONE);
        }


        int frequentExtSize = frequentExtensions.size();
        Log.i("aaa", frequentExtensions.toString());
        if (frequentExtSize > 0) {
            tblRowExt1.setVisibility(View.VISIBLE);
            txtViewFileExtenType1.setText((String) frequentExtensions.get(frequentExtSize - 1).get("extension"));
            txtViewFileExtenFreq1.setText(String.valueOf(frequentExtensions.get(frequentExtSize - 1).get("count")));
        } else {
            tblRowExt1.setVisibility(View.GONE);
        }

        if (frequentExtSize > 1) {
            tblRowExt2.setVisibility(View.VISIBLE);
            txtViewFileExtenType2.setText((String) frequentExtensions.get(frequentExtSize - 2).get("extension"));
            txtViewFileExtenFreq2.setText(String.valueOf(frequentExtensions.get(frequentExtSize - 2).get("count")));
        } else {
            tblRowExt2.setVisibility(View.GONE);
        }

        if (frequentExtSize > 2) {
            tblRowExt3.setVisibility(View.VISIBLE);
            txtViewFileExtenType3.setText((String) frequentExtensions.get(frequentExtSize - 3).get("extension"));
            txtViewFileExtenFreq3.setText(String.valueOf(frequentExtensions.get(frequentExtSize - 3).get("count")));
        } else {
            tblRowExt3.setVisibility(View.GONE);
        }

        if (frequentExtSize > 3) {
            tblRowExt4.setVisibility(View.VISIBLE);
            txtViewFileExtenType4.setText((String) frequentExtensions.get(frequentExtSize - 4).get("extension"));
            txtViewFileExtenFreq4.setText(String.valueOf(frequentExtensions.get(frequentExtSize - 4).get("count")));
        } else {
            tblRowExt4.setVisibility(View.GONE);
        }

        if (frequentExtSize > 4) {
            tblRowExt5.setVisibility(View.VISIBLE);
            txtViewFileExtenType5.setText((String) frequentExtensions.get(frequentExtSize - 5).get("extension"));
            txtViewFileExtenFreq5.setText(String.valueOf(frequentExtensions.get(frequentExtSize - 5).get("count")));
        } else {
            tblRowExt5.setVisibility(View.GONE);
        }

        long totalFileSize = 0;
        for (int i = 0; i < highestSizedFilesList.size(); i++) {
            totalFileSize = totalFileSize + highestSizedFilesList.get(i).length();
            shareHead1Text = shareHead1Text + highestSizedFilesList.get(i) + "\n";
        }
        long averageFileSize = totalFileSize / 10;

        txtViewavgFileSize.setText(averageFileSize / 1024 + "kb");
        shareHead2 = shareHead2 + averageFileSize / 1024 + "kb";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareHead1 + shareHead1Text + "\n" + shareHead2);
                startActivity(Intent.createChooser(intent, "Share using"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
