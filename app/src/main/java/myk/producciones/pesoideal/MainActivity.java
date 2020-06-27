package myk.producciones.pesoideal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    TextView msgTextView;
    EditText pesoView;
    EditText alturaView;
    private InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        // Prueba ca-app-pub-3940256099942544/1033173712
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-9391440864407001/5977530151");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });




        Button consultarButton;
        consultarButton = (Button) findViewById(R.id.consultarButton);

        msgTextView = (TextView) findViewById(R.id.textResultView);
        msgTextView.setText(R.string.consultar);

        pesoView = (EditText) findViewById(R.id.pesoValue);
        alturaView = (EditText) findViewById(R.id.alturaValue);


        final ImageView personSalud = (ImageView) findViewById(R.id.imageView);

        final int[] saludArray = {
                R.mipmap.gordo,
                R.mipmap.fit,
                R.mipmap.flaco,
        };

        float peso, altura;

        consultarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    //Log.d("TAG", "The interstitial wasn't loaded yet.");
                }


                //if(pesoView.getText().length() != 0 && alturaView.getText().length() != 0 && !pesoView.getText().equals(".") && !alturaView.getText().equals(".")){
                if(checkFloat(pesoView) && checkFloat(alturaView)){

                    Double bmi = CalcularBmi(Float.valueOf(pesoView.getText().toString()),Float.valueOf(alturaView.getText().toString()));
                    if(bmi>25){
                        msgTextView.setText(R.string.sobrepeso);
                        personSalud.setImageResource(saludArray[0]);
                    }else if (bmi>=18.5){
                        msgTextView.setText(R.string.pesoideal);
                        personSalud.setImageResource(saludArray[1]);
                    }else{
                        msgTextView.setText(R.string.bajopeso);
                        personSalud.setImageResource(saludArray[2]);
                    }
                }else{
                    msgTextView.setText("Favor diligencia todos tus datos!");
                }
            }
        });


    }

    public Double CalcularBmi(float peso, float altura){
        Double bmi = (peso / (Math.pow(altura, 2)));
        return bmi;
    }

    public boolean checkFloat(EditText value) {
        try {
            Float.valueOf(value.getText().toString());
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

}
