package com.example.movieticketapp.Activity.Ticket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movieticketapp.Firebase.FirebaseRequest;
import com.example.movieticketapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TicketDetailActivity extends AppCompatActivity {
    private ImageView qrCode;
    private TextView filmName ;
    private RatingBar filmRating;
    private TextView filmRatingText;
    private TextView filmKind ;
    private TextView filmDuration;
    private TextView cinema;
    private TextView bookDay;
    private TextView seatPositon;
    private TextView paidBill;
    private TextView idOder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail_screen);
        RoundedImageView filmPoster = (RoundedImageView) findViewById(R.id.ivPoster);
         filmName = (TextView) findViewById(R.id.tvName);
         filmRating = (RatingBar) findViewById(R.id.rating);
         filmRatingText = (TextView) findViewById(R.id.vote);
         filmKind = (TextView) findViewById(R.id.tvKind);
         filmDuration = (TextView) findViewById(R.id.tvDuration);
         cinema = (TextView) findViewById(R.id.cinema);
         bookDay = (TextView) findViewById(R.id.DateAndTime);
         seatPositon = (TextView) findViewById(R.id.SeatNumber);
         paidBill = (TextView) findViewById(R.id.Paid);
         idOder = (TextView) findViewById(R.id.IDOrder);
        Button backButton = (Button) findViewById(R.id.backbutton);
        qrCode = findViewById(R.id.QRCode);

        Picasso.get().load(getIntent().getExtras().getString("poster")).into(filmPoster);
        filmName.setText(getIntent().getExtras().getString("name"));
        filmRating.setRating((float) getIntent().getExtras().getDouble("rate"));
        filmRatingText.setText("(" + getIntent().getExtras().getDouble("rate") + ")");
        filmKind.setText(getIntent().getExtras().getString("kind"));
        filmDuration.setText(getIntent().getExtras().getString("duration"));
        String cinemaID = getIntent().getExtras().getString("cinemaID");
        FirebaseRequest.database.collection("Cinema").document(cinemaID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                cinema.setText(documentSnapshot.get("Name").toString());
            }
        });

        bookDay.setText(getIntent().getExtras().getString("time"));
        seatPositon.setText(getIntent().getExtras().getString("seat"));
        paidBill.setText(getIntent().getExtras().getString("paid"));
        idOder.setText(getIntent().getExtras().getString("idorder"));
        generateQrcode(idOder.getText().toString());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void generateQrcode(String idOrder){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode("id order: " + idOrder, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);

        }catch(WriterException e){
            throw new RuntimeException(e);
        }
    }


}