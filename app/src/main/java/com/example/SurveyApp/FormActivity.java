package com.example.SurveyApp;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class FormActivity<Group> extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textSiteName;
    private TextView textSiteClassification;
    private TextView textSiteSharingStatus;
    private ImageView imageSitePhoto;
    private androidx.constraintlayout.widget.Group groupSiteInfo;
    private static final int IMAGE_FILE_REQUST_CODE = 101;
   private  String siteName;
    private String siteClassification;
    private String siteSharingStatus;
    private String siteId;
    private File file;
    private Uri finalPath;
    private boolean siteInfoAdded;
    private String []itemquantity=new String[47];
    private String []itemvendor=new String[47];
    private String []itemmodel=new String[47];
    private String []itemequipmentcondition=new String[47];
    private String []itemremark=new String[47];
    RecyclerView itemrecycle;
    int row=0;

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
        final EditText editSiteId=findViewById(R.id.site_id);
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

        //intializing item variables below
          itemrecycle=findViewById(R.id.item_view_recycle);
          itemrecycle.setHasFixedSize(true);
          itemrecycle.setLayoutManager(new LinearLayoutManager(this));
          //have to create adapter for recycleview

        final EditText editquantity=findViewById(R.id.edit_item_quantity);

        Button buttonAddItemPhoto=findViewById(R.id.button_add_item_photo);
        Button buttonAddItem=findViewById(R.id.button_add_item);
        Spinner selectVendor=findViewById(R.id.select_item_vendor);
        ArrayAdapter<CharSequence> vendorAdapter = ArrayAdapter.createFromResource(FormActivity.this, R.array.item_vendor_array, android.R.layout.simple_expandable_list_item_1);
        selectVendor.setAdapter(vendorAdapter);
        Spinner selectModel=findViewById(R.id.select_item_vendor);

        ArrayAdapter<CharSequence> modelAdapter = ArrayAdapter.createFromResource(FormActivity.this, R.array.item_model_array, android.R.layout.simple_expandable_list_item_1);
        selectModel.setAdapter(vendorAdapter);
        final EditText editequipmentcondition=findViewById(R.id.edit_item_equipment_condition);
        final EditText editremark=findViewById(R.id.edit_item_remark);
        buttonAddItemPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                //have to save photo
            }
        });
         buttonAddItem.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 itemquantity[row]=editquantity.getText().toString();
                 itemequipmentcondition[row]=editequipmentcondition.getText().toString();
                itemremark[row]=editremark.getText().toString();
                 //have to save variable here or directly send them to another class to create excel
                 /* Intent sendata=new Intent(FormActivity.this,Excel.class);
                 i.putextra()

                  */
                 row++;
                 //create a done bottom for the entire form,then intialize row=0 then
             }
         });

        buttonAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create file directory here
                textSiteName.setText(String.format("Site name:%s", editSiteName.getText().toString()));
                textSiteClassification.setText(String.format("Classification:%s", siteClassification));
                textSiteSharingStatus.setText(String.format("Site Sharing Stauts: %s", siteSharingStatus));
                //the client wants to preview the data entered on the click of a sperate button named preview
                Glide.with(FormActivity.this).load(finalPath).into(imageSitePhoto);
                groupSiteInfo.setVisibility(View.VISIBLE);
                siteInfoAdded = true;
                dialog.dismiss();
                siteName=editSiteName.getText().toString();
                siteId=editSiteId.getText().toString();

                /* Excel data=new Excel(siteName,siteId,siteSharingStatus,siteClassification);


                 */

                itemrecycle.setVisibility(View.VISIBLE);



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
                System.out.println(siteSharingStatus); //why do we have to print this?
                break;
            case R.id.select_item_vendor:
                itemvendor[row]=getResources().getStringArray(R.array.item_vendor_array)[position];
                break;
            case R.id.select_item_model:
                itemmodel[row]=getResources().getStringArray(R.array.item_model_array)[position];
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
