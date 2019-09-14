package com.example.SurveyApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

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

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textSiteName;
    private TextView textSiteClassification;
    private TextView textSiteSharingStatus;
    private ImageView imageSitePhoto;
    private Group groupSiteInfo;
    private static final int IMAGE_FILE_REQUST_CODE = 101;
    private String siteClassification;
    private String siteSharingStatus;
    private File file;
    private Uri finalPath;
    private boolean siteInfoAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        textSiteName = findViewById(R.id.text_site_name);
        textSiteClassification = findViewById(R.id.text_site_classification);
        textSiteSharingStatus = findViewById(R.id.text_site_sharing_status);
        imageSitePhoto = findViewById(R.id.image_site_photo);
        groupSiteInfo = findViewById(R.id.group);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siteInfoAdded) {
                    addItem();
                } else {
                    showAddSiteInfoDialog();
                }
            }
        });
    }

    private void addItem() {
        final View view = LayoutInflater.from(FormActivity.this).inflate(R.layout.form_item_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(FormActivity.this).create();
        dialog.setView(view);

        //initiate and use views here

        dialog.show();
    }

    private void showAddSiteInfoDialog() {
        final View view = LayoutInflater.from(FormActivity.this).inflate(R.layout.form_site_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(FormActivity.this).create();
        dialog.setView(view);
        final EditText editSiteName = view.findViewById(R.id.edit_site_name);
        Spinner selectSiteClassification = view.findViewById(R.id.select_site_classification);
        ArrayAdapter<CharSequence> classificationAdapter = ArrayAdapter.createFromResource(FormActivity.this, R.array.site_classification, android.R.layout.simple_expandable_list_item_1);
        selectSiteClassification.setAdapter(classificationAdapter);
        selectSiteClassification.setOnItemSelectedListener(FormActivity.this);
        Spinner selectSiteSharingStatus = view.findViewById(R.id.select_site_sharing_status);
        ArrayAdapter<CharSequence> sharingStatusAdapter = ArrayAdapter.createFromResource(FormActivity.this, R.array.site_sharing_status, android.R.layout.simple_expandable_list_item_1);
        selectSiteSharingStatus.setAdapter(sharingStatusAdapter);
        selectSiteSharingStatus.setOnItemSelectedListener(FormActivity.this);
        EditText editSiteImageName = view.findViewById(R.id.edit_site_photo_name);
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
                textSiteName.setText(String.format("Site name:%s", editSiteName.getText().toString()));
                textSiteClassification.setText(String.format("Classification:%s", siteClassification));
                textSiteSharingStatus.setText(String.format("Site Sharing Stauts: %s", siteSharingStatus));
                Glide.with(FormActivity.this).load(finalPath).into(imageSitePhoto);
                groupSiteInfo.setVisibility(View.VISIBLE);
                siteInfoAdded = true;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);
        switch (parent.getId()) {
            case R.id.select_site_classification:
                siteClassification = getResources().getStringArray(R.array.site_classification)[position];
                System.out.println(siteClassification);
                break;
            case R.id.select_site_sharing_status:
                siteSharingStatus = getResources().getStringArray(R.array.site_sharing_status)[position];
                System.out.println(siteSharingStatus);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void takePhoto() {
        Intent intent = new Intent(FormActivity.this, ImageSelectActivity.class);
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
