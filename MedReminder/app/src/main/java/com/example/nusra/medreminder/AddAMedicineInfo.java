package com.example.nusra.medreminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddAMedicineInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amedicine_info);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox_meat:
//                if (checked)
//                // Put some meat on the sandwich
//            else
//                // Remove the meat
//                break;
//            case R.id.checkbox_cheese:
//                if (checked)
//                // Cheese me
//            else
//                // I'm lactose intolerant
//                break;
//
    }
}
