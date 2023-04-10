package com.example.movieticketapp.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.movieticketapp.R;

public class UserProflingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profling_screen);

        ImageButton btnNext = findViewById(R.id.btnNext);
        Button btnHorror = findViewById(R.id.btnHorror);
        Button btnAction = findViewById(R.id.btnAction);
        Button btnDrama = findViewById(R.id.btnDrama);
        Button btnWar = findViewById(R.id.btnWar);
        Button btnComedy = findViewById(R.id.btnComedy);
        Button btnCrime = findViewById(R.id.btnCrime);

        Button btnBahasa = findViewById(R.id.btnBahasa);
        Button btnEnglish = findViewById(R.id.btnEnglish);
        Button btnJapanese = findViewById(R.id.btnJapanese);
        Button btnKorean = findViewById(R.id.btnKorean);

        final int[] isSelected = {0};
        btnHorror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });

        btnBahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnJapanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnKorean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.isSelected() == true)
                {
                    view.setSelected(false);
                    isSelected[0] -= 1;
                    if (isSelected[0] == 0) btnNext.setSelected(false);
                }
                else
                {
                    view.setSelected(true);
                    isSelected[0] += 1;
                    btnNext.setSelected(true);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ConfirmationProfileActivity.class);
                startActivity(i);
            }
        });
    }


}