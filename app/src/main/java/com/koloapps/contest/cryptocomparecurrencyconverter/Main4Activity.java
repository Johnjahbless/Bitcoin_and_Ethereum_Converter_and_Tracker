package com.koloapps.contest.cryptocomparecurrencyconverter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Main4Activity extends AppCompatActivity {
CardView walletID;
    DecimalFormat df = new DecimalFormat("####0.0000000");
CardView withdrawCardView;
EditText wallet;
 double total = 0.00000;
    CountDownTimer countDownTimer;

TextView scoreText;
TextView mining;
TextView counterText;
    public RewardedVideoAd mAd;
    public RewardedVideoAd mAd1;
    public RewardedVideoAd mAd2;
    public int counter;
    ProgressDialog progressDialog;
String fileName = "empty";
String totalScore = " ";
    public Animation fade;
    AdView mAdView;

    AlertDialog alertDialogg;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fade = AnimationUtils.loadAnimation(this, R.anim.rotate);
        findViewById(R.id.circle).startAnimation(fade);
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle(getString(R.string.sending_data));
        progressDialog.setMessage(getString(R.string.waitt));
        just();
        //checkConnection();

        walletID = (CardView) findViewById(R.id.walletID);
        wallet = (EditText) findViewById(R.id.editText);
      withdrawCardView = (CardView) findViewById(R.id.cardWitdraw);
      scoreText = (TextView) findViewById(R.id.score);
      mining = (TextView) findViewById(R.id.textmine);
      counterText = (TextView) findViewById(R.id.countertext);
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3517746418699749~7280583326");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.loadAd(getString(R.string.ad_unit_id), new AdRequest.Builder().build());
        mAd1 = MobileAds.getRewardedVideoAdInstance(this);
        mAd1.loadAd(getString(R.string.ad_unit_id1), new AdRequest.Builder().build());
        mAd2 = MobileAds.getRewardedVideoAdInstance(this);
        mAd2.loadAd(getString(R.string.ad_unit_id2), new AdRequest.Builder().build());
        loadRewardedVideoAd();
        loadRewardedVideoAd1();
        loadRewardedVideoAd2();



        // double u = total;
     //scoreText.setText(total);


    try {
            FileInputStream fileIn = openFileInput(totalScore);
            InputStreamReader inputStreamReader = new InputStreamReader(fileIn);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String myScore = null;

            while ((myScore = bufferedReader.readLine()) != null) {
                stringBuilder.append(myScore);
            }

            fileIn.close();
            inputStreamReader.close();

            scoreText.setText(stringBuilder.toString());
            String score = scoreText.getText().toString();
        total = Double.valueOf(score);




        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getBaseContext(),
                        R.string.available, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd();

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getBaseContext(),
                        R.string.btcincrese, Toast.LENGTH_SHORT).show();
                total += 0.000002;
                scoreText.setText(df.format (total));
                saveScore();
                withdrawCardView.setVisibility(View.GONE);
                walletID.setVisibility(View.GONE);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                loadRewardedVideoAd();

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                loadRewardedVideoAd();
                Toast.makeText(getBaseContext(),
                        R.string.notmining, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onRewardedVideoCompleted() {

            }

        });



        mAd1.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getBaseContext(),
                        R.string.available, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd1();

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getBaseContext(),
                        R.string.btcincrese, Toast.LENGTH_SHORT).show();
                total += 0.000003;
                scoreText.setText(df.format (total));
                saveScore();
                withdrawCardView.setVisibility(View.GONE);
                walletID.setVisibility(View.GONE);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                loadRewardedVideoAd1();

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                loadRewardedVideoAd1();
                Toast.makeText(getBaseContext(),
                        R.string.notmining, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onRewardedVideoCompleted() {

            }

        });



        mAd2.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getBaseContext(),
                        R.string.available, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd2();

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getBaseContext(),
                        R.string.btcincrese, Toast.LENGTH_SHORT).show();
                total += 0.000004;
                scoreText.setText(df.format(total));
                saveScore();
                withdrawCardView.setVisibility(View.GONE);
                walletID.setVisibility(View.GONE);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                loadRewardedVideoAd2();

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                loadRewardedVideoAd2();
                Toast.makeText(getBaseContext(),
                        R.string.notmining, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onRewardedVideoCompleted() {
            }

        });



    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-3517746418699749/3549332544", new AdRequest.Builder().build());
    }
    private void loadRewardedVideoAd1() {
        mAd1.loadAd("ca-app-pub-3517746418699749/8196020533", new AdRequest.Builder().build());
    }
    private void loadRewardedVideoAd2() {
        mAd2.loadAd("ca-app-pub-3517746418699749/1747104209", new AdRequest.Builder().build());
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;

        }else{
            return false;
        }
    }

    private void checkConnection() {
        if (isOnline()) {

just();

           // alertDialogg.hide();
          //  mining.setText(R.string.mining_started);

        }else {
            counter = 0;
            countDownTimer.cancel();
          // mining.setText("Mining stopped");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.not_connectd);
            alertDialogBuilder
                    .setMessage(R.string.turn_on)
                    .setCancelable(false)
                    .setPositiveButton(R.string.retryy,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   checkConnection();

                                }
                            })

                    .setNegativeButton(R.string.dismis, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
             alertDialogg = alertDialogBuilder.create();
            alertDialogg.show();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



        }
    }

    public void witdraw(View view) {
        withdrawCardView.setVisibility(View.VISIBLE);
        walletID.setVisibility(View.GONE);

    }

    public void setup(View view) {
        walletID.setVisibility(View.VISIBLE);
        withdrawCardView.setVisibility(View.GONE);

        try {
            FileInputStream fileIn = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileIn);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
    stringBuilder.append(line);
            }

            fileIn.close();
inputStreamReader.close();
wallet.setText(stringBuilder.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }



    }

    public void start(View view) {

    }

    public void save(View view) {
        String name = wallet.getText().toString();

        if (name.equals(String.valueOf(""))) {
            wallet.setError(getString(R.string.wallet));
            walletID.setVisibility(View.VISIBLE);
        } else {
            try {
                FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(name);
                outputStreamWriter.close();
                walletID.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), R.string.settings_saved, Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void withdrwaBTC(View view) {
        try {
            progressDialog.show();
            {
                wait(9000);
                withdrawCardView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.threashold, Toast.LENGTH_LONG).show();

        }
    }

    public void dismis(View view) {
        withdrawCardView.setVisibility(View.GONE);

    }

    public void card1(View view) {
        if (mAd.isLoaded()) {
            mAd.show();
        }
        withdrawCardView.setVisibility(View.GONE);
        walletID.setVisibility(View.GONE);


    }
    public void card2(View view) {
        if (mAd.isLoaded()) {
            mAd.show();
        }else {
            Toast.makeText(getBaseContext(),
                    R.string.miningfail, Toast.LENGTH_LONG).show();
        }
        withdrawCardView.setVisibility(View.GONE);
        walletID.setVisibility(View.GONE);
    }

    public void card3(View view) {
        if (mAd1.isLoaded()) {
            mAd1.show();
        }else {
            Toast.makeText(getBaseContext(),
                    R.string.miningfail, Toast.LENGTH_LONG).show();
        }


        withdrawCardView.setVisibility(View.GONE);
        walletID.setVisibility(View.GONE);
    }
    public void card4(View view) {
        if (mAd2.isLoaded()) {
            mAd2.show();
        }else {
            Toast.makeText(getBaseContext(),
                    R.string.miningfail, Toast.LENGTH_LONG).show();
        }

        withdrawCardView.setVisibility(View.GONE);
        walletID.setVisibility(View.GONE);
    }
   private void saveScore() {
       DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
       sym.setDecimalSeparator('.');
       df.setDecimalFormatSymbols(sym);

     //  String totaltext = scoreText.getText().toString();
       String totaltext = (df.format(total));

           if (totalScore.equals(String.valueOf(""))) {

           } else {
               try {
                   FileOutputStream fileOutputStream = openFileOutput(totalScore, MODE_PRIVATE);
                   OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                   outputStreamWriter.write(totaltext);
                   outputStreamWriter.close();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

       }

    public void clear(View view) {
        walletID.setVisibility(View.GONE);
        withdrawCardView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        mAd.pause(this);
        mAd1.pause(this);
        mAd2.pause(this);
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        mAd.resume(this);
        mAd1.resume(this);
        mAd2.resume(this);
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        mAd1.destroy(this);
        mAd2.destroy(this);
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            countDownTimer.cancel();
            onBackPressed();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
        }
        if (id == R.id.remove) {
            onBackPressed();
            countDownTimer.cancel();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });

        }

        if (id == R.id.share) {
            countDownTimer.cancel();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hy, Download this Bitcoin and Ethereum android application, it's very useful, you can track Bitcoin and Ethereum exchange prices and can also convert to your local currency, i Love it! Am sure you will also Check it out using this link here.... https://play.google.com/store/apps/details?id=com.koloapps.contest.cryptocomparecurrencyconverter");
            startActivity(shareIntent);

        }
        if (id == R.id.settings) {



            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.notifications_allow);
            alertDialogBuilder
                    .setMessage("")
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), R.string.settings_saved, Toast.LENGTH_LONG).show();


                                }
                            })

                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), R.string.settings_saved, Toast.LENGTH_LONG).show();
                            dialog.cancel();

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });

        }

        if (id == R.id.exit) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.exitApplication);
            alertDialogBuilder
                    .setMessage(R.string.googleplaystore)
                    .setCancelable(true)
                    .setPositiveButton(R.string.exit,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    countDownTimer.cancel();
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);

                                }
                            })

                    .setNegativeButton(R.string.rate, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("market://details?id=com.koloapps.contest.cryptocomparecurrencyconverter"));
                            startActivity(intent);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });


        }
        if (id == R.id.rate) {
            countDownTimer.cancel();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.koloapps.contest.cryptocomparecurrencyconverter"));
            startActivity(intent);

        }  if (id == R.id.offline){
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
            mInterstitialAd = new InterstitialAd(getApplicationContext());
            mInterstitialAd.setAdUnitId(getString(R.string.admob_interstetial_ad));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
        }


        return super.onOptionsItemSelected(item);


        // Inflate the menu; this adds items to the action bar if it is present.


    }

    public void round(View view) {
        withdrawCardView.setVisibility(View.GONE);
        walletID.setVisibility(View.GONE);


        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }


    public void just() {
         countDownTimer = new CountDownTimer(65000, 1000) {
            public void onTick(long millisUntilFinished) {
                counter++;
                counterText.setText(String.valueOf(counter + "s"));

            }

            public void onFinish() {

                counter = 0;
                total += 0.0000001;
                saveScore();
                scoreText.setText(df.format(total));
                counterText.setText(String.valueOf(counter + "s"));

                Toast.makeText(getBaseContext(),
                        R.string.btcincrese, Toast.LENGTH_SHORT).show();
                mInterstitialAd = new InterstitialAd(getApplicationContext());
                mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_ad1));
                AdRequest adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                });


                checkConnection();
            }

        }.start();
    }
}
