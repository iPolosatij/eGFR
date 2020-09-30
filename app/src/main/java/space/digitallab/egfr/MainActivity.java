package space.digitallab.egfr;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    private EditText mKreatinin;
    private EditText mAge;
    private EditText mVes;
    private EditText mCistatine;
    private TextView mResult;
    private CheckBox mFemin;
    private CheckBox mBlack;

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }//скрыть клавиатуру при нажатии кнопки


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CheckBox");


        WebView webView = findViewById(R.id.recomend);
        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        webView.loadUrl("https://www.digitallab.space/dec-f");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mKreatinin = findViewById(R.id.kreatinine);
        mAge = findViewById(R.id.age);
        mVes = findViewById(R.id.mass);
        mResult = findViewById(R.id.Result);
        mCistatine = findViewById(R.id.cistatine);
        mFemin = (CheckBox) findViewById(R.id.femin);
        mBlack = (CheckBox) findViewById(R.id.black);


        Button mRach = (Button) findViewById(R.id.relise);
        mRach.setOnClickListener(new View.OnClickListener() {
            float a, g, s;
            double b, c, d, e, f, m, f1, i, g1, r;
            double m1, m2, m3, m4, m5; // MDRD
            double i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11,i12;
            String Dop;//komment
            String Dop1;//MDRD
            String Dop2;//CKD
            String Dop3;//KG
            String Dop4;//ed
            String Dop5;
            String Dop6;//eGFR(Cistatine C)
            String Dop7;//eGFR(Cistatine/Kreatinine)

            @Override
            public void onClick(View v) {

                if (mCistatine.getText().toString().equals("")) {

                    //Start_only_Scr_KG_MDRD_CKD-EPI

                    if (mKreatinin.getText().toString().equals("")) {

                        Dop5 = "specify the amount of creatinine or cystatin C";
                        mResult.setText(Dop5);

                    } else if (mAge.getText().toString().equals("")) {

                        Dop5 = "specify the age";
                        mResult.setText(Dop5);

                    } else if (mVes.getText().toString().equals("")) {

                        Dop5 = "specify the weight";
                        mResult.setText(Dop5);

                    } else {

                        String S1 = mKreatinin.getText().toString();
                        String S2 = mAge.getText().toString();
                        String S3 = mVes.getText().toString();


                        a = Float.parseFloat(S1);
                        b = Double.parseDouble(S2);
                        c = Double.parseDouble(S3);

                        if (a < 5) {
                            a = (float) (a * 88.4017);
                        } else {
                            a = a * 1;
                        }


                        i1 = -0.329;
                        i2 = -1.209;
                        i3 = -0.411;
                        i4 = a / 0.7 * 0.0113;
                        i5 = a / 0.9 * 0.0113;
                        i6 = Math.pow(i4, i1);
                        i7 = Math.pow(i4, i2);
                        i8 = Math.pow(i5, i3);
                        i9 = Math.pow(i5, i2);
                        i10 = Math.pow(0.993, b);

                        m1 = -1.154;
                        m2 = -0.203;
                        m3 = a * 0.0113;
                        m4 = Math.pow(m3, m1);
                        m5 = Math.pow(b, m2);

                        if (mFemin.isChecked()) {
                            e = 0.85;//kg.MDRD
                            if (a <= 62)//CKD
                                i = 144 * i6 * i10;
                            else
                                i = 144 * i7 * i10;
                        } else {
                            e = 1;
                            if (a <= 80)//CKD
                                i = 144 * i8 * i10;
                            else
                                i = 144 * i9 * i10;
                        }

                        if (mBlack.isChecked()) {
                            f = 0.742;//MDRD
                            f1 = 1.18;
                        } else {
                            f = 1;
                            f1 = 1;
                        }

                        m = f * 186 * m4 * m5 * f1;
                        d = (88 * (140 - b) * c / (72 * a)) * e;

                        m = Math.ceil(m);//MDRD
                        i = Math.ceil(i);//CKD
                        d = Math.ceil(d);


                        if (d >= 90)
                            Dop = "Normal or high GFR, no CKD or CKD G1 (if other signs are present)";
                        else if (d <= 89 && d >= 60)
                            Dop = "CKD G2 Mildly decreased GFR.(In the absence of other evidence of kidney damage, neither GFR category G1 nor G2 fulfills the criteria for CKD.)";
                        else if (d <= 59 && d >= 45)
                            Dop = "CKD G3a Mildly to moderately decreased GFR.";
                        else if (d <= 44 && d >= 30)
                            Dop = "CKD G3b Moderately to severely decreased GFR.";
                        else if (d <= 29 && d >= 15)
                            Dop = "CKD G4 Severely decreased GFR.";
                        else if (d < 15)
                            Dop = "CKD G5 Kidney failure";
                        else
                            Dop = "check that the data you entered is correct";

                        Dop1 = "MDRD - ";

                        Dop2 = "CKD-EPI -";

                        Dop4 = " ml/min";

                        Dop3 = "KG -";


                        mResult.setText(Dop3 + d + Dop4 + " \n " + Dop1 + m + Dop4 + " \n " + Dop2 + i + Dop4 + " \n " + Dop);
                    }
                    //End_only_Scr_KG_MDRD_CKD-EPI

                } else if (mKreatinin.getText().toString().equals("")) {

                    //Start_only_Scys
                     if (mAge.getText().toString().equals("")) {

                        Dop5 = "specify the age";
                        mResult.setText(Dop5);

                    }else{

                        String S2 = mAge.getText().toString();
                        String S4 = mCistatine.getText().toString();

                        b = Float.parseFloat(S2);
                        g = Float.parseFloat(S4);

                        if (mFemin.isChecked()) {
                            i11 = 0.932;
                            if (g <= 0.8)
                                g1 = 133 * (Math.pow((g / 0.8), -0.499) * (Math.pow(0.996, b))) * i11;
                            else
                                g1 = 133 * (Math.pow((g / 0.8), -1.328) * (Math.pow(0.996, b))) * i11;
                        } else {
                            i11 = 1;
                            if (g <= 0.8)
                                g1 = 133 * (Math.pow((g / 0.8), -0.499) * (Math.pow(0.996, b))) * i11;
                            else
                                g1 = 133 * (Math.pow((g / 0.8), -1.328) * (Math.pow(0.996, b))) * i11;
                        }

                        g1 = Math.ceil(g1);

                        if (g1 >= 90)
                            Dop = "Normal or high GFR, no CKD or CKD G1 (if other signs are present)";
                        else if (g1 <= 89 && g1 >= 60)
                            Dop = "CKD G2 Mildly decreased GFR.(In the absence of other evidence of kidney damage, neither GFR category G1 nor G2 fulfills the criteria for CKD.)";
                        else if (g1 <= 59 && g1 >= 45)
                            Dop = "CKD G3a Mildly to moderately decreased GFR.";
                        else if (g1 <= 44 && g1 >= 30)
                            Dop = "CKD G3b Moderately to severely decreased GFR.";
                        else if (g1 <= 29 && g1 >= 15)
                            Dop = "CKD G4 Severely decreased GFR.";
                        else if (g1 < 15)
                            Dop = "CKD G5 Kidney failure";
                        else
                            Dop = "check that the data you entered is correct";

                        mResult.setText(Dop4 + g1 + " \n " + Dop);
                    }

                     //End_only_Scys

                } else {

                    //Start_oll_formulas

                    if (mAge.getText().toString().equals("")) {

                        Dop5 = "specify the age";
                        mResult.setText(Dop5);

                    } else if (mVes.getText().toString().equals("")) {

                        Dop5 = "specify the weight";
                        mResult.setText(Dop5);

                    } else {


                        String S1 = mKreatinin.getText().toString();
                        String S2 = mAge.getText().toString();
                        String S3 = mVes.getText().toString();
                        String S4 = mCistatine.getText().toString();

                        a = Float.parseFloat(S1);//Scr
                        b = Double.parseDouble(S2);//Age
                        c = Double.parseDouble(S3);//Mass
                        g = Float.parseFloat(S4);//Scys

                        //Scr_converter>
                        if (a < 5) {

                            s = a * 1;
                            a = (float) (a * 88.4017);

                        } else {

                            s = (float) (a * 0.0113);
                            a = a * 1;

                        }
                        //Scr_converter<


                        i1 = -0.329;
                        i2 = -1.209;
                        i3 = -0.411;
                        i4 = a / 0.7 * 0.0113;
                        i5 = a / 0.9 * 0.0113;
                        i6 = Math.pow(i4, i1);
                        i7 = Math.pow(i4, i2);
                        i8 = Math.pow(i5, i3);
                        i9 = Math.pow(i5, i2);
                        i10 = Math.pow(0.993, b);

                        m1 = -1.154;
                        m2 = -0.203;
                        m3 = a * 0.0113;
                        m4 = Math.pow(m3, m1);
                        m5 = Math.pow(b, m2);

                        if (mFemin.isChecked()) {
                            e = 0.85;//kg.MDRD
                            if (a <= 62)//CKD
                                i = 144 * i6 * i10;
                            else
                                i = 144 * i7 * i10;
                        } else {
                            e = 1;
                            if (a <= 80)//CKD
                                i = 144 * i8 * i10;
                            else
                                i = 144 * i9 * i10;
                        }

                        if (mBlack.isChecked()) {
                            f = 0.742;//MDRD
                            f1 = 1.18;
                        } else {
                            f = 1;
                            f1 = 1;
                        }


                        //CKD-EPI (Scys).Start
                        if (mFemin.isChecked()) {
                            i11 = 0.932;
                            if (g <= 0.8)
                                g1 = 133 * (Math.pow((g / 0.8), -0.499) * (Math.pow(0.996, b))) * i11;
                            else
                                g1 = 133 * (Math.pow((g / 0.8), -1.328) * (Math.pow(0.996, b))) * i11;
                        } else {
                            i11 = 1;
                            if (g <= 0.8)
                                g1 = 133 * (Math.pow((g / 0.8), -0.499) * (Math.pow(0.996, b))) * i11;
                            else
                                g1 = 133 * (Math.pow((g / 0.8), -1.328) * (Math.pow(0.996, b))) * i11;
                        }
                        //CKD-EPI (Scys).End

                        //CKD-EPI (Scr/Scys).Start
                        if (mBlack.isChecked()) {
                            if(mFemin.isChecked()) {
                                if(s<=0.7 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.248))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b))*1.08;
                                }else if(s<=0.7 && g>0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.248))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b))*1.08;
                                }else if(s>0.7 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.601))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b))*1.08;
                                }else if(s>0.7 && g>0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.601))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b))*1.08;
                                }
                            }else{
                                if(s<=0.9 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.248))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b))*1.08;
                                }else if(s<=0.9 && g>0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.248))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b))*1.08;
                                }else if(s>0.9 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.601))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b))*1.08;
                                }else if(s>0.9 && g>0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.601))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b))*1.08;
                                }
                            }
                        }else{
                            if(mFemin.isChecked()) {
                                if(s<=0.7 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.248))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b));
                                }else if(s<=0.7 && g>0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.248))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b));
                                }else if(s>0.7 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.601))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b));
                                }else if(s>0.7 && g>0.8) {
                                    r = 130*(Math.pow((s/0.7),-0.601))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b));
                                }
                            }else{
                                if(s<=0.9 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.248))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b));
                                }else if(s<=0.9 && g>0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.248))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b));
                                }else if(s>0.9 && g<=0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.601))*(Math.pow((g/0.8),-0.375))*(Math.pow(0.995,b));
                                }else if(s>0.9 && g>0.8) {
                                    r = 130*(Math.pow((s/0.9),-0.601))*(Math.pow((g/0.8),-0.711))*(Math.pow(0.995,b));
                                }
                            }
                        }
                        //CKD-EPI (Scr/Scys).End

                        m = f * 186 * m4 * m5 * f1;
                        d = (88 * (140 - b) * c / (72 * a)) * e;

                        g1 = Math.ceil(g1);//Cistatine C
                        m = Math.ceil(m);//MDRD
                        i = Math.ceil(i);//CKD
                        d = Math.ceil(d);//KG
                        r = Math.ceil(r);//Cistatine/Kreatinine GFR


                        if (r >= 90)
                            Dop = "Normal or high GFR, no CKD or CKD G1 (if other signs are present)";
                        else if (g1 <= 89 && g1 >= 60)
                            Dop = "CKD G2 Mildly decreased GFR.(In the absence of other evidence of kidney damage, neither GFR category G1 nor G2 fulfills the criteria for CKD.)";
                        else if (g1 <= 59 && g1 >= 45)
                            Dop = "CKD G3a Mildly to moderately decreased GFR.";
                        else if (g1 <= 44 && g1 >= 30)
                            Dop = "CKD G3b Moderately to severely decreased GFR.";
                        else if (g1 <= 29 && g1 >= 15)
                            Dop = "CKD G4 Severely decreased GFR.";
                        else if (g1 < 15)
                            Dop = "CKD G5 Kidney failure";
                        else
                            Dop = "check that the data you entered is correct";

                        Dop1 = "MDRD - ";

                        Dop2 = "CKD-EPI (Scr) -";

                        Dop4 = " ml/min";

                        Dop3 = "KG -";

                        Dop6 = "CKD-EPI (Scys)* - ";

                        Dop7 = "CKD-EPI (Scr & Scys)* - ";

                        mResult.setText(Dop7 + r + Dop4 + " \n " + Dop6 + g1 + Dop4 + " \n " + Dop2 + i + Dop4 + " \n " + Dop3 + d + Dop4 + " \n " + Dop1 + m + Dop4 + " \n " + Dop);

                    }
                }
            }
        });
    }

        public void Cliar (View view){
            mKreatinin.setText("");
            mAge.setText("");
            mVes.setText("");
            mCistatine.setText("");
            mResult.setText("");

    }
}
