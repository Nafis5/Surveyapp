package com.example.SurveyApp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText sitename;
    String buttonname;
    LinearLayout linearLayout;
    LinearLayout siteform;
    EditText siteid;
    Spinner siteclassificationddl;
    Spinner sitesharingstatusddl;
    String []siteformresponse=new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.lin1);
        Button add = findViewById(R.id.add);
        sitename=findViewById(R.id.Site_name);
        Button ok = findViewById(R.id.ok);
        siteform=findViewById(R.id.siteform);
        siteid=findViewById(R.id.site_id);
        siteclassificationddl= findViewById(R.id.site_classification_ddl);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.site_classification, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        siteclassificationddl.setAdapter(adapter);
        siteclassificationddl.setOnItemSelectedListener(this);
        sitesharingstatusddl=findViewById(R.id.site_sharingstatus_ddl);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.site_sharing_status, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         sitesharingstatusddl.setAdapter(adapter2);

        sitesharingstatusddl.setOnItemSelectedListener(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.INVISIBLE);
                siteform.setVisibility(View.VISIBLE);
                //ok.setVisibility(View.VISIBLE);
            }







        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonname = sitename.getText().toString();
                siteform.setVisibility(View.INVISIBLE);
                sitename.setText("");
                //ok.setVisibility(View.INVISIBLE);
                Creattebutton(buttonname);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    void Creattebutton(String name) {
        Button b = new Button(this);
        b.setText(name);
        b.setVisibility(View.INVISIBLE);
        b.setLayoutParams(new LinearLayout.LayoutParams(370, 130));
        linearLayout.addView(b);
        b.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.site_classification_ddl:
                siteformresponse[3] = getResources().getStringArray(R.array.site_classification)[position];
            case R.id.site_sharingstatus_ddl:
                siteformresponse[2] = getResources().getStringArray(R.array.site_sharing_status)[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
