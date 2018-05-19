package com.koloapps.contest.cryptocomparecurrencyconverter;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.koloapps.contest.cryptocomparecurrencyconverter.Model.Utils;

import java.text.DecimalFormat;

public class Main3Activity extends AppCompatActivity {
    DecimalFormat df = new DecimalFormat("####0.00");
    EditText first;
    TextView second;
    Spinner spinner, spinner2;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    //defind String Array for spinner
    String[] spinner_first = {"ETH- Ethereum", "Calculate ETH", "BTC- Bitcoin", "Calculate BTC" };
    String[] spinner_second = {"USD - US Dollar", "EUR", "NAIRA - Nigeria", "GBP - British Pound",
            "ING - Indian Ruppe", "AUD - Austrialian Dollar", "CAD - Canadian Dollar",
            "SGD - Singapore Dollar", "CHF - Swiss Frame", "MYR - Malaysian Riggit", "JPY - Japanese Yen",
            "CNY - Chinese Yuan Renminbi", "NZD - New Zealand Dollar", "ZAR - South Africa Rand", "BRL - Brazilian Real",
            "SAR - Saudi Arabian Riyal", "KES - Kenyan Shilling", "KRW - South Korean Won", "GHS - Ghanaian Cedi",
            "ARS - Argentine Peso", "RUB - Russian Ruble"};

    //defined variable for spinner selected value
    double first_selected, second_selected;

    //defined variable for the value parsed from MainActivity
    double BtcGetUsd, EthGetUSD;

    //defined variable to store EditText value
    double getText;
    double BTCget;
    double ETHget;

    ImageView imageView;
    View line;
  EditText getBTC;
  TextView showId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // get value parsed from MainActivity
        BtcGetUsd = getIntent().getExtras().getDouble("btc");
        EthGetUSD = getIntent().getExtras().getDouble("eth");
        final double uuuu = EthGetUSD;
       final double uuu = BtcGetUsd;
        getBTC = (EditText) findViewById(R.id.getBTC);
        showId = (TextView) findViewById(R.id.showId);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main3Activity.this);
        alertDialogBuilder.setTitle(R.string.offline_title);
        alertDialogBuilder
                .setMessage(R.string.offline_help)
                .setCancelable(true)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        })

                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();





        if (BtcGetUsd == 0.00) {
            getBTC.setText("9734");
        }else {
            getBTC.setText(df.format(uuuu));
        }



        //initialized views
        first = (EditText) findViewById(R.id.firstEdit);
        second = (TextView) findViewById(R.id.secondEdit);
        imageView = (ImageView) findViewById(R.id.img);
        line = findViewById(R.id.view);
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3517746418699749~7280583326");
        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //initialized spinnerFirst
        spinner = (Spinner) findViewById(R.id.spinnerFirst);
        //define ArrayAdapter1 for the Spinner and String Array defined (spinner_first)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Main3Activity.this,
                R.layout.support_simple_spinner_dropdown_item, spinner_first);
        //parse arrayAdapter1
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = spinner.getSelectedItemPosition();

                if (pos == 0) {
                    //get Spinner selected item value
                    first_selected = ETHget;
                    if (EthGetUSD == 0.00) {
                        getBTC.setText("789");
                    }else {
                        getBTC.setText(df.format(uuuu));
                    }


                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    showId.setText("1 ETH ($): ");
                    imageView.setImageResource(R.drawable.eth_coin);
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));
                } if (pos == 1) {
                    first_selected = ETHget;
                    if (EthGetUSD == 0.00) {
                        getBTC.setText("789");
                    }else {
                        getBTC.setText(df.format(uuuu));
                    }


                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    showId.setText("1 ETH ($): ");
                    imageView.setImageResource(R.drawable.eth_coin);
                    line.setBackgroundColor(getResources().getColor(R.color.ethColor));

                }
                if (pos == 2) {
                    //get Spinner selected item value
                    first_selected = BTCget;
                    if (BtcGetUsd == 0.00){
                        getBTC.setText("9739.12");
                    } else{
                        getBTC.setText(df.format(uuu));
                    }


                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    showId.setText("1 BTC ($): ");
                    imageView.setImageResource(R.drawable.btc_coin);
                    line.setBackgroundColor(getResources().getColor(R.color.btcColor));
                } if (pos == 3) {
                    first_selected = BTCget;
                    if (BtcGetUsd == 0.00){
                        getBTC.setText("9739.12");
                    } else{
                        getBTC.setText(df.format(uuu));
                    }


                    //Convert
                    double uu = (first_selected / second_selected) * getText;

                    //display in TextView
                    second.setText(df.format(uu));
                    showId.setText("1 BTC ($): ");
                    imageView.setImageResource(R.drawable.btc_coin);
                    line.setBackgroundColor(getResources().getColor(R.color.btcColor));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //initialized spinnersecond
        spinner2 = (Spinner) findViewById(R.id.spinnersecond);

        //define ArrayAdapter2 for the Spinner and String Array defined (spinner_second)
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(Main3Activity.this,
                R.layout.support_simple_spinner_dropdown_item, spinner_second);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //get spinner2 selected item value to string
                String sel = spinner2.getSelectedItem().toString();

                //compare the value
                //if true second_selected should set currency exchange rate. e.g EUR = 1.16095
                if (sel == "USD - US Dollar") {
                    second_selected = 1;
                    //Convert
                    double uu = (first_selected / second_selected) * getText;
                    //display the result in TextView with Local Currency Symbol
                    second.setText(Utils.getCurrencySymbol("USD") + df.format(uu));
                } else if (sel == "EUR") {
                    second_selected = 1.16095;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("EUR") + df.format(uu));
                } else if (sel == "NAIRA - Nigeria") {
                    second_selected = 0.0033;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("NGN") + df.format(uu));
                } else if (sel == "GBP - British Pound") {
                    second_selected = 1.31281;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("GBP") + df.format(uu));
                } else if (sel == "ING - Indian Ruppe") {
                    second_selected = 0.01538;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText( df.format(uu));
                } else if (sel == "AUD - Austrialian Dollar") {
                    second_selected = 0.76834;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("AUD") + df.format(uu));
                } else if (sel == "CAD - Canadian Dollar") {
                    second_selected = 0.78099;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("CAD") + df.format(uu));
                } else if (sel == "SGD - Singapore Dollar") {
                    second_selected = 0.73270;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("SGD") + df.format(uu));
                } else if (sel == "CHF - Swiss Franc") {
                    second_selected = 1.00224;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("CHF") + df.format(uu));
                } else if (sel == "MYR - Malaysian Riggit") {
                    second_selected = 0.23565;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("MYR") + df.format(uu));
                } else if (sel == "JPY - Japanese Yen") {
                    second_selected = 0.00880;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("JPY") + df.format(uu));
                } else if (sel == "CNY - Chinese Yuan Renminbi") {
                    second_selected = 0.15040;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("CNY") + df.format(uu));
                } else if (sel == "NZD - New Zealand Dollar") {
                    second_selected = 0.68779;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("NZD") + df.format(uu));
                } else if (sel == "ZAR - South Africa Rand") {
                    second_selected = 0.07097;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("ZAR") + df.format(uu));
                } else if (sel == "BRL - Brazilian Real") {
                    second_selected = 0.30903;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("BRL") + df.format(uu));
                } else if (sel == "SAR - Saudi Arabian Riyal") {
                    second_selected = 0.26665;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("SAR") + df.format(uu));
                } else if (sel == "KES - Kenyan Shilling") {
                    second_selected = 0.00962;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("KES") + df.format(uu));
                } else if (sel == "KRW - South Korean Won") {
                    second_selected = 0.00089;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("KRW") + df.format(uu));
                } else if (sel == "GHS - Ghanaian Cedi") {
                    second_selected = 0.22547;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("GHS") + df.format(uu));
                } else if (sel == "ARS - Argentine Peso") {
                    second_selected = 0.05687;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("ARS") + df.format(uu));
                } else if (sel == "RUB - Russian Ruble") {
                    second_selected = 0.01719;
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(Utils.getCurrencySymbol("RUB") + df.format(uu));
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                String fd = editable.toString();

               // String getbtc = editable.toString();





                if (fd.length() > 0) {
                    getText = Double.valueOf(fd);
                   // BTCget = Double.valueOf(getbtc);
                    //ETHget = Double.valueOf(getbtc);
                    double uu = (first_selected / second_selected) * getText;
                    second.setText(df.format(uu));

                }
            }
        });
       getBTC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // String fd = editable.toString();

                String getbtc = editable.toString();
                //getText = Double.valueOf(fd);
                BTCget = Double.valueOf(getbtc);
                ETHget = Double.valueOf(getbtc);
                double uu = (first_selected / second_selected) * getText;
                second.setText(df.format(uu));


                if (getBTC.length() <= 2) {



                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main3Activity.this);
                    alertDialogBuilder.setTitle("Please Enter a value greater 1000");
                    alertDialogBuilder
                            .setMessage("")
                            .setCancelable(false)
                            .setPositiveButton("Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {


                                        }
                                    })

                            .setNegativeButton("", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();



                }
            }
        });


    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
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
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
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
        if (id == R.id.remove) {
            onBackPressed();
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

        if (id == R.id.share) {
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

                    .setNegativeButton(R.string.no_connection_found, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(), R.string.settings_saved, Toast.LENGTH_LONG).show();
                            dialog.cancel();

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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

        if (id == R.id.exit) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.exitApplication);
            alertDialogBuilder
                    .setMessage(R.string.googleplaystore)
                    .setCancelable(true)
                    .setPositiveButton(R.string.exit,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
        if (id == R.id.rate) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.koloapps.contest.cryptocomparecurrencyconverter"));
            startActivity(intent);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

        }if (id == R.id.help) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.offline_title);
            alertDialogBuilder
                    .setMessage(R.string.offline_help)
                    .setCancelable(true)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            })

                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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


        } if (id == R.id.news) {
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

        } if (id == R.id.offline){
            finish();
        }


        if (id == R.id.about) {
            Intent intent = new Intent(this, Main2Activity.class);
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

}


