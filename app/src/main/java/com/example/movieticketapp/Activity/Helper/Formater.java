package com.example.movieticketapp.Activity.Helper;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class Formater extends ValueFormatter {


    private String[] mSuffix = new String[]{
            "", "k", "m", "b", "t"
    };
    private int mMaxLength = 5;
    private DecimalFormat mFormat;
    private String mText = "";

    public Formater() {

        mFormat = new DecimalFormat("###E00");
    }

    /**
     * Creates a formatter that appends a specified text to the result string
     *
     * @param appendix a text that will be appended
     */
    public Formater(String appendix) {
        this();
        mText = appendix;
    }
    @Override
    public String getFormattedValue(float value) {
        return makePretty(value) + mText;
    }
    public void setAppendix(String appendix) {
        this.mText = appendix;
    }

    /**
     * Set custom suffix to be appended after the values.
     * Default suffix: ["", "k", "m", "b", "t"]
     *
     * @param suffix new suffix
     */
    public void setSuffix(String[] suffix) {
        this.mSuffix = suffix;
    }

    public void setMaxLength(int maxLength) {
        this.mMaxLength = maxLength;
    }

    /**
     * Formats each number properly. Special thanks to Roman Gromov
     * (https://github.com/romangromov) for this piece of code.
     */
    private String makePretty(double number) {

        if(number > 1000){
            String r = mFormat.format(Math.round(number));
            int numericValue1 = Character.getNumericValue(r.charAt(r.length() - 1));
            int numericValue2 = Character.getNumericValue(r.charAt(r.length() - 2));
            int combined = Integer.valueOf(numericValue2 + "" + numericValue1);

            r = r.replaceAll("E[0-9][0-9]", mSuffix[combined / 3]);

            while (r.length() > mMaxLength || r.matches("[0-9]+\\.[a-z]")) {
                r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
            }
            return r;
        }

        else {
            double num = Math.round(number * 10) / 10.0;
            return String.valueOf(num);
        }



    }

    public int getDecimalDigits() {
        return 0;
    }
}

