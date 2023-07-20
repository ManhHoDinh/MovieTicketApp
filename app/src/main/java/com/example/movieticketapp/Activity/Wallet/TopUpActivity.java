package com.example.movieticketapp.Activity.Wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.movieticketapp.Adapter.Helper;
import com.example.movieticketapp.Adapter.PriceGridAdapter;
import com.example.movieticketapp.NetworkChangeListener;
import com.example.movieticketapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TopUpActivity extends AppCompatActivity {

    private GridView listPrice;
    private Button backBtn;
    private Button topUpBtn;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private TextInputEditText textInputEditText;
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        listPrice = (GridView) findViewById(R.id.priceGridView);
        backBtn = (Button) findViewById(R.id.backbutton);
        topUpBtn = (Button) findViewById(R.id.topUpBtn);
        List<String> list = new ArrayList<String>();
        list.add("50000");
        list.add("100000");
        list.add("150000");
        list.add("200000");
        list.add("250000");
        list.add("500000");
        list.add("750000");
        list.add("1000000");

        textInputEditText = (TextInputEditText) findViewById(R.id.amountEt);
        PriceGridAdapter a =new PriceGridAdapter( this, list, textInputEditText );

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInputEditText.getText().toString().equals("")){
                    Toast.makeText(TopUpActivity.this, "Please choose price to top up!", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(TopUpActivity.this, SuccessTopUpActivity.class);
                    intent.putExtra("selectedPrice", textInputEditText.getText().toString());
                    startActivity(intent);
                }

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