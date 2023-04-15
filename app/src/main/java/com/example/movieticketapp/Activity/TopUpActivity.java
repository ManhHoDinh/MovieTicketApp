package com.example.movieticketapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.PriceGridAdapter;
import com.example.movieticketapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TopUpActivity extends AppCompatActivity {
    private GridView listPrice;
    private Button backBtn;
    private TextInputEditText textInputEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        listPrice = (GridView) findViewById(R.id.priceGridView);
        backBtn = (Button) findViewById(R.id.backbutton);
        List<String> list = new ArrayList<String>();
        list.add("500000");
        list.add("1000000");
        list.add("1500000");
        list.add("500000");
        list.add("500000");
        list.add("500000");
        textInputEditText = (TextInputEditText) findViewById(R.id.amountEt);
        PriceGridAdapter a =new PriceGridAdapter( this, list, textInputEditText );
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//       listPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//               Log.e("f","f");
//           }
//       });
        listPrice.setAdapter(a);



}
}