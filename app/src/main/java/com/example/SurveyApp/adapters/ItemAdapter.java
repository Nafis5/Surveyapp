package com.example.SurveyApp.adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SurveyApp.R;
import com.example.SurveyApp.model.Model;
import com.example.SurveyApp.util.Excel;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Model> list;
    private Excel excel;

    public ItemAdapter(Context context, ArrayList<Model> list, String siteId) {
        this.context = context;
        this.list = list;
        excel = new Excel(siteId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.form_item_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Model model = list.get(position);

        holder.textName.setText(model.getName());

        ArrayAdapter<CharSequence> vendorAdapter = ArrayAdapter.createFromResource(context, R.array.item_vendor_array, android.R.layout.simple_expandable_list_item_1);
        holder.selectVendor.setAdapter(vendorAdapter);
        holder.selectVendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                model.setVendor(context.getResources().getStringArray(R.array.item_vendor_array)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> modelAdapter = ArrayAdapter.createFromResource(context, R.array.item_model_array, android.R.layout.simple_expandable_list_item_1);
        holder.selectModel.setAdapter(modelAdapter);
        holder.selectModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                model.setModel(context.getResources().getStringArray(R.array.item_model_array)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.editquantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    holder.selectVendor.requestFocus();
                }
                return false;
            }
        });

        holder.imageRecycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false));

        holder.buttonAddItemPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takePhoto();
            }
        });

        holder.buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.setQuantity(Integer.parseInt(holder.editquantity.getText().toString()));
                model.setCondition(holder.editequipmentcondition.getText().toString());
                model.setRemark(holder.editremark.getText().toString());
                excel.add(model, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void saveData(String siteName) {
        excel.finish(siteName);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private EditText editquantity;
        private Spinner selectVendor;
        private Spinner selectModel;
        private EditText editequipmentcondition;
        private EditText editremark;
        private RecyclerView imageRecycler;
        private Button buttonAddItemPhoto;
        private Button buttonAddItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.text_item_name);
            editquantity=itemView.findViewById(R.id.edit_item_quantity);
            selectVendor=itemView.findViewById(R.id.select_item_vendor);
            selectModel=itemView.findViewById(R.id.select_item_model);
            editequipmentcondition=itemView.findViewById(R.id.edit_item_equipment_condition);
            editremark=itemView.findViewById(R.id.edit_item_remark);
            imageRecycler = itemView.findViewById(R.id.image_recycler);
            buttonAddItemPhoto=itemView.findViewById(R.id.button_add_item_photo);
            buttonAddItem=itemView.findViewById(R.id.button_add_item);
        }
    }
}
