package com.emonics.filescanner;

import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PROJECT_NAME = "FileScanner ---> ";
    private Button btnStartScan;
    private int REQUEST_CODE = 111;
    private File rootFile;
    private List<File> highestSizedFilesList = new ArrayList<File>();
    private Map<String, Integer> extensionCountMap = new HashMap<String, Integer>();
//    private File[] highestSizedFilesList = new File[10];

    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private int fileSize = 0;

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_main);

        String root_sd = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i(PROJECT_NAME, root_sd);
        rootFile = new File(root_sd);

        btnStartScan = (Button) findViewById(R.id.btn_startScan);
        btnStartScan.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("File Scanner");
        mBuilder.setContentText("Scanner Running");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1001, mBuilder.build());
        progressBar = new ProgressDialog(v.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("File scanning ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        fileSize = 0;


        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus = checkProgress();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }
        }).start();


        scanfiles(rootFile);
        List<Map<String, Serializable>> frequentExtensions = new ArrayList<Map<String, Serializable>>();
        frequentExtensions = sortHashMapByValues(extensionCountMap);
        Intent intent = null;
        Bundle extras = new Bundle();
        intent = new Intent(this, DisplayActivity.class);
        extras.putSerializable("FREQUENT_EXTENS", (Serializable) frequentExtensions);
        extras.putSerializable("HIGHEST_SIZEDFILES", (Serializable) highestSizedFilesList);
        intent.putExtras(extras);
        Log.i(PROJECT_NAME, "starting activity");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You granted the permission", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();

        }
    }

    private int checkProgress() {
        return fileSize;
    }

    private void scanfiles(File rootDirectory) {
        int filecount=0;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            } else {
                File filesList[] = rootDirectory.listFiles();
                for (int i = 0; i < filesList.length; i++) {
                    if (filesList[i].isDirectory()) {
                        Log.i("Directory", filesList[i].getName());
                        scanfiles(filesList[i]);
                    } else {
                        Log.i("file", filesList[i].getName());
                        filecount++;
                        if(filecount==20){
                            fileSize=10;
                        }else if(filecount==60){
                            fileSize=20;
                        }else if(filecount==100){
                            fileSize=30;
                        }else if(filecount==120){
                            fileSize=40;
                        }else if(filecount==1000){
                            fileSize=50;
                        }else if(filecount==3000){
                            fileSize=70;
                        }else{
                            fileSize=75;
                        }

                        sortHighestSize(filesList[i]);
                        extensionCount(filesList[i]);
                        fileSize=100;
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void sortHighestSize(File file) {
        if (highestSizedFilesList.size() != 0) {
            for (int i = 0; highestSizedFilesList.size() > i && i < 10; i++) {
                if (highestSizedFilesList.get(i).length() < file.length()) {
                    highestSizedFilesList.add(i, file);
                    break;
                }
            }
        } else if (highestSizedFilesList.size() == 0) {
            highestSizedFilesList.add(file);
        }
    }

    private void display() {
        for (int i = 0; i < highestSizedFilesList.size(); i++) {
        }
    }

    private void extensionCount(File file) {
        String extension = getExtension(file);
        if (extensionCountMap.containsKey(extension)) {
            int extensionCountTemp = extensionCountMap.get(extension);
            extensionCountMap.put(extension, extensionCountTemp + 1);
        } else {
            extensionCountMap.put(extension, 1);
        }
    }

    private String getExtension(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
        return extension;
    }

    private List<Map<String, Serializable>> getMostFrequentExtension() {
        List<Map<String, Serializable>> frequentExtensions = new ArrayList<Map<String, Serializable>>();
        if (extensionCountMap.size() != 0) {
            for (Map.Entry<String, Integer> entry : extensionCountMap.entrySet()) {
                int count = (Integer) entry.getValue();
                String extension = entry.getKey();
                if (frequentExtensions.size() == 0) {
                    Map<String, Serializable> frequentExtensionMap = new HashMap<String, Serializable>();
                    frequentExtensionMap.put("count", count);
                    frequentExtensionMap.put("extension", extension);
                    frequentExtensions.add(frequentExtensionMap);
                } else {
                    for (int i = 0; i < frequentExtensions.size(); i++) {
                        if ((Integer) frequentExtensions.get(i).get("count") < count && (Integer) frequentExtensions.get(i).get("count") != count) {
                            Map<String, Serializable> frequentExtensionMap = new HashMap<String, Serializable>();
                            frequentExtensionMap.put("count", count);
                            frequentExtensionMap.put("extension", extension);
                            frequentExtensions.add(i, frequentExtensionMap);
                        }
                    }
                }
            }
        }
        return frequentExtensions;
    }


    public List<Map<String, Serializable>> sortHashMapByValues(
            Map<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);
        List<Map<String, Serializable>> frequentExtensions = new ArrayList<Map<String, Serializable>>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    Map<String, Serializable> frequentExtensionMap = new HashMap<String, Serializable>();
                    frequentExtensionMap.put("extension", key);
                    frequentExtensionMap.put("count", val);
                    frequentExtensions.add(frequentExtensionMap);
//                    frequentExtensionMap.put(key, val);
                    break;
                }
            }
        }
        return frequentExtensions;
    }
}
