package com.example.SurveyApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.SurveyApp.R;
import com.example.SurveyApp.adapters.ItemAdapter;
import com.example.SurveyApp.model.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textSiteName;
    private TextView textSiteClassification;
    private TextView textSiteSharingStatus;
    private ImageView imageSitePhoto;
    private Group groupSiteInfo;
    private Button save;

    private static final int IMAGE_FILE_REQUST_CODE = 101;
    private String siteName;
    private String siteClassification;
    private String siteSharingStatus;
    private File file;
    private Uri finalPath;

    private ArrayList<Model> list;
    private ItemAdapter adapter;
    private boolean siteAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSiteName = findViewById(R.id.text_site_name);
        textSiteClassification = findViewById(R.id.text_site_classification);
        textSiteSharingStatus = findViewById(R.id.text_site_sharing_status);
        imageSitePhoto = findViewById(R.id.image_site_photo);
        groupSiteInfo = findViewById(R.id.group);

        list = new ArrayList<>();
        adapter = new ItemAdapter(this, list);
        RecyclerView itemrecycle = findViewById(R.id.item_view_recycle);
        itemrecycle.setHasFixedSize(true);
        itemrecycle.setLayoutManager(new LinearLayoutManager(this));
        itemrecycle.setAdapter(adapter);

        save = findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siteAdded) {
                    saveData();
                } else {
                    showAddSiteInfoDialog();
                }
            }
        });
    }

    private void saveData() {

    }

    private void showAddSiteInfoDialog() {
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.form_site_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
        dialog.setView(view);
        final EditText editSiteName = view.findViewById(R.id.edit_site_name);
        Spinner selectSiteClassification = view.findViewById(R.id.select_site_classification);
        ArrayAdapter<CharSequence> classificationAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.site_classification, android.R.layout.simple_expandable_list_item_1);
        selectSiteClassification.setAdapter(classificationAdapter);
        selectSiteClassification.setOnItemSelectedListener(MainActivity.this);
        Spinner selectSiteSharingStatus = view.findViewById(R.id.select_site_sharing_status);
        ArrayAdapter<CharSequence> sharingStatusAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.site_sharing_status, android.R.layout.simple_expandable_list_item_1);
        selectSiteSharingStatus.setAdapter(sharingStatusAdapter);
        selectSiteSharingStatus.setOnItemSelectedListener(MainActivity.this);
//        EditText editSiteImageName = view.findViewById(R.id.edit_site_photo_name);
        Button buttonAddSitePhoto = view.findViewById(R.id.button_add_site_photo);
        buttonAddSitePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        Button buttonAddSite = view.findViewById(R.id.button_add_site);

        buttonAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siteName = editSiteName.getText().toString();
                loadView();
                siteAdded = true;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadView() {
        save.setText("Save");
        textSiteName.setText(String.format("Site name:%s", siteName));
        textSiteClassification.setText(String.format("Classification:%s", siteClassification));
        textSiteSharingStatus.setText(String.format("Site Sharing Stauts: %s", siteSharingStatus));
        Glide.with(MainActivity.this).load(finalPath).into(imageSitePhoto);
        groupSiteInfo.setVisibility(View.VISIBLE);
        list.add(new Model("Item One"));
        list.add(new Model("Iem Two"));
        list.add(new Model("Item Three"));
        list.add(new Model("Item Four"));
        list.add(new Model("Item Five"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.select_site_classification:
                siteClassification = getResources().getStringArray(R.array.site_classification)[position];
                break;
            case R.id.select_site_sharing_status:
                siteSharingStatus = getResources().getStringArray(R.array.site_sharing_status)[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void takePhoto() {
        Intent intent = new Intent(MainActivity.this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, false);
        startActivityForResult(intent, IMAGE_FILE_REQUST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_FILE_REQUST_CODE && resultCode == Activity.RESULT_OK) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            try {
                if (filePath != null) {
                    file = new File(filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (file.exists()) {
                try {
                    File compressor = new Compressor(this)
                            .setMaxHeight(100)
                            .setMaxWidth(100)
                            .setQuality(30)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .compressToFile(file);
                    finalPath = Uri.fromFile(compressor);
                    System.out.println(finalPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            }
        }
    }
}
