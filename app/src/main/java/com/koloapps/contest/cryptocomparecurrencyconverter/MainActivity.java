package com.koloapps.contest.cryptocomparecurrencyconverter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.koloapps.contest.cryptocomparecurrencyconverter.APICALL.APIClient;
import com.koloapps.contest.cryptocomparecurrencyconverter.APICALL.APIService;
import com.koloapps.contest.cryptocomparecurrencyconverter.Model.BTC;
import com.koloapps.contest.cryptocomparecurrencyconverter.Model.CryptoCrcy;
import com.koloapps.contest.cryptocomparecurrencyconverter.Model.ETH;
import com.koloapps.contest.cryptocomparecurrencyconverter.Model.Utils;
import com.koloapps.contest.cryptocomparecurrencyconverter.util.IabHelper;
import com.koloapps.contest.cryptocomparecurrencyconverter.util.IabResult;
import com.koloapps.contest.cryptocomparecurrencyconverter.util.Inventory;
import com.koloapps.contest.cryptocomparecurrencyconverter.util.Purchase;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    CryptoCrcy cryptoCrcy;
    BTC btc;
    ETH eth;
    private static final String TAG =
            "com.koloapps.contest.cryptocomparecurrencyconverter";

    IabHelper mHelper;
    static final String ITEM_SKU = "removeads";
    private InterstitialAd mInterstitialAd;
    double BtcGetUsd, EthGetUSD;
    ProgressDialog progressDialog;
    TextView textView_btc;
    TextView textView_eth;
    TextView btc_eur;
    TextView eth_eur;
    TextView btc_ngn;
    TextView eth_ngn;
    TextView btc_thb;
    TextView eth_thb;
    TextView btc_brl;
    TextView eth_brl;
    TextView btc_ghs;
    TextView eth_ghs;
    TextView btc_zar;
    TextView eth_zar;
    TextView btc_kes;
    TextView eth_kes;
    TextView btc_gbp;
    TextView eth_gbp;
    TextView btc_ing;
    TextView eth_ing;
    AdView mAdView;
    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    DecimalFormat df = new DecimalFormat("####0.00");
    double first_selected, first_selected1, second_selected, second_selected1, ghs_selected, zar_selected, kes_selected, gbp_selected,
            ing_selected, brl_selected, thb_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3517746418699749~7280583326");
        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       // Locale locale = new Locale("pt-rBR", "de" + "es", "hi-rIN" + "ru-rRU");
        //Configuration config = getBaseContext().getResources().getConfiguration();
        //config.locale = locale;
        //getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3517746418699749/8344708392");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());



        //initialized progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.retriving);
        progressDialog.setMessage("Please wait ....");
        //start progressDialog
        progressDialog.show();
        LoadCryptoCompaire();

        textView_btc = (TextView) findViewById(R.id.btc_Text);
        textView_eth = (TextView) findViewById(R.id.eth_Text);
        btc_eur = (TextView) findViewById(R.id.btc_eur);
        eth_eur = (TextView) findViewById(R.id.eth_eur);
        btc_ngn = (TextView) findViewById(R.id.btc_ngn);
        eth_ngn = (TextView) findViewById(R.id.eth_ngn);
        btc_thb = (TextView) findViewById(R.id.btc_thb);
        eth_thb = (TextView) findViewById(R.id.eth_thb);
        btc_brl = (TextView) findViewById(R.id.btc_brl);
        eth_brl = (TextView) findViewById(R.id.eth_brl);
        btc_ghs = (TextView) findViewById(R.id.btc_ghs);
        eth_ghs = (TextView) findViewById(R.id.eth_ghs);
        btc_zar = (TextView) findViewById(R.id.btc_zar);
        eth_zar = (TextView) findViewById(R.id.eth_zar);
        btc_kes = (TextView) findViewById(R.id.btc_kes);
        eth_kes = (TextView) findViewById(R.id.eth_kes);
        btc_gbp = (TextView) findViewById(R.id.btc_gbp);
        eth_gbp = (TextView) findViewById(R.id.eth_gbp);
        btc_ing = (TextView) findViewById(R.id.btc_ing);
        eth_ing = (TextView) findViewById(R.id.eth_ing);


        TextView textView = (TextView) findViewById(R.id.retry);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        //initialized cardView
        CardView cardView_btc = (CardView) findViewById(R.id.btc_card);
        CardView cardView_eth = (CardView) findViewById(R.id.eth_card);
        CardView cardView_btc_eur = (CardView) findViewById(R.id.eur_cardbtc);
        CardView cardView_eth_eur = (CardView) findViewById(R.id.eur_cardeth);
        CardView cardView_btc_ghs = (CardView) findViewById(R.id.ghs_cardbtc);
        CardView cardView_eth_ghs = (CardView) findViewById(R.id.ghs_cardeth);
        CardView cardView_btc_zar = (CardView) findViewById(R.id.zar_cardbtc);
        CardView cardView_eth_zar = (CardView) findViewById(R.id.zar_cardeth);
        CardView cardView_btc_kes = (CardView) findViewById(R.id.kes_cardbtc);
        CardView cardView_eth_kes = (CardView) findViewById(R.id.kes_cardeth);
        CardView cardView_btc_gbp = (CardView) findViewById(R.id.gbp_cardbtc);
        CardView cardView_eth_gbp = (CardView) findViewById(R.id.gbp_cardeth);
        CardView cardView_btc_ing = (CardView) findViewById(R.id.ing_cardbtc);
        CardView cardView_eth_ing = (CardView) findViewById(R.id.ing_cardeth);

        CardView cardView_btc_ngn = (CardView) findViewById(R.id.ngn_cardbtc);
        CardView cardView_eth_ngn = (CardView) findViewById(R.id.ngn_cardeth);
        CardView cardView_btc_brl = (CardView) findViewById(R.id.brl_cardbtc);
        CardView cardView_eth_brl = (CardView) findViewById(R.id.brl_cardeth);
        CardView cardView_btc_thb = (CardView) findViewById(R.id.thb_cardbtc);
        CardView cardView_eth_thb = (CardView) findViewById(R.id.thb_cardeth);
        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmkDy9FPMEqdytnI5JSmSiT+SFLiNFEjyiWMa0zDJ1Q6WuN5dPtcg37RyQM7dsaQzU/KipaEC9v/CYpgTQFV4Ou96Z67V+TLSFMP33sNKhXTNgBc7OOTfjb4pWKzWVHxZKakqvHHGajCvevOUqmz6ZzS+ejASfvuybt9IgYlI1yPc2UgPFt62UGK+cqUnSL51yebwMIdig3CPnuz23kevyi9ym9FpDPbMQIXzarM9apu8/9p/1Y+2zaDJPE+rUCaIRoTsVcV/CwawCOREjmVIrHjn6VshItPE3Fuxi7aln29k5akuhD1Rf0LNuGKzUU+v6eQ0MD9JOKYy8tSNowM5LwIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billingfailed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app BillingOK");
                                           }
                                       }
                                   });

        //imitialised no network coonection error layout
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        //initialised swipe refresh layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
        (android.R.color.black));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                frameLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
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
                shuffle();


            }
        });


        textView.setOnClickListener(new View.OnClickListener() {


                                        @Override
                                        public void onClick(View view) {
                                            Intent mintent = new Intent(MainActivity.this, Main3Activity.class);
                                            mintent.putExtra("btc", BtcGetUsd);
                                            mintent.putExtra("eth", EthGetUSD);
                                            startActivity(mintent);
                                            frameLayout.setVisibility(View.GONE);
                                            linearLayout.setVisibility(View.VISIBLE);

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


                                    }
        );


        cardView_btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_eth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_btc_eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_ghs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_ghs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_btc_brl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_brl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_ngn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_ngn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_thb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_thb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_zar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_zar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_btc_kes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_kes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_btc_gbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_gbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });

        cardView_btc_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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
        });
        cardView_eth_ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parse both BTC & ETH value to ConvertionActivity
                Intent intent = new Intent(getApplicationContext(), ConvertionActivity.class);
                intent.putExtra("btc", BtcGetUsd);
                intent.putExtra("eth", EthGetUSD);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
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




    //when layout is pulled down, method for response
    private void shuffle() {
        frameLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        LoadCryptoCompaire();
    }


    //defined method for the response
    public void LoadCryptoCompaire() {
        //initialized the APIClient
        APIClient apiClient = new APIClient();
        try {
            //defined APIService to get client response
            APIService apiService = APIClient.getClient().create(APIService.class);
            //call client response
            Call<CryptoCrcy> btcCall = apiService.getBTC();
            btcCall.enqueue(new Callback<CryptoCrcy>() {
                @Override
                public void onResponse(Call<CryptoCrcy> call, Response<CryptoCrcy> response) {
                    //initialized respond class
                    cryptoCrcy = new CryptoCrcy();
                    cryptoCrcy = response.body();

                    //initialized the json object class
                    btc = new BTC();
                    eth = new ETH();

                    btc = cryptoCrcy.getBTC();
                    eth = cryptoCrcy.getETH();

                    //parse object to variable
                    BtcGetUsd = btc.getUSD();
                    EthGetUSD = eth.getUSD();
                    //display result in TextViews with Local Currency Symbols
                    textView_btc.setText("1 BTC : " + Utils.getCurrencySymbol("USD") + BtcGetUsd);
                    textView_eth.setText("1 ETH : " + Utils.getCurrencySymbol("USD") + EthGetUSD);

                    first_selected = BtcGetUsd;
                    first_selected1 = EthGetUSD;
                    second_selected = 1.16095;
                    second_selected1 = 0.0033;
                    ghs_selected = 0.22547;
                    zar_selected = 0.07097;
                    kes_selected = 0.00962;
                    gbp_selected = 1.31281;
                    ing_selected = 0.15040;
                    brl_selected = 0.30903;
                    thb_selected = 0.030;
                    double uu = (first_selected / second_selected) * 1;
                    btc_eur.setText("1 BTC : " + Utils.getCurrencySymbol("EUR") + df.format(uu));


                    double uuu = (first_selected1 / second_selected) * 1;
                    eth_eur.setText("1 ETH : " + Utils.getCurrencySymbol("EUR") + df.format(uuu));
                    //nigeria
                    double ngn = (first_selected / second_selected1) * 1;
                    btc_ngn.setText("1 BTC : " + Utils.getCurrencySymbol("NGN") + df.format(ngn));


                    double ngneth = (first_selected1 / second_selected1) * 1;
                    eth_ngn.setText("1 ETH : " + Utils.getCurrencySymbol("NGN") + df.format(ngneth));

                    //thiland
                    double thb = (first_selected / thb_selected) * 1;
                    btc_thb.setText("1 BTC : " + Utils.getCurrencySymbol("THB") + df.format(thb));


                    double thbeth = (first_selected1 / thb_selected) * 1;
                    eth_thb.setText("1 ETH : " + Utils.getCurrencySymbol("THB") + df.format(thbeth));

                    //brazil
                    double brl = (first_selected / brl_selected) * 1;
                    btc_brl.setText("1 BTC : " + Utils.getCurrencySymbol("BRL") + df.format(brl));


                    double brleth = (first_selected1 / brl_selected) * 1;
                    eth_brl.setText("1 ETH : " + Utils.getCurrencySymbol("BRL") + df.format(brleth));


                    //Ghana
                    double ghs = (first_selected / ghs_selected) * 1;
                    btc_ghs.setText("1 BTC : " + Utils.getCurrencySymbol("GHS") + df.format(ghs));


                    double ghseth = (first_selected1 / ghs_selected) * 1;
                    eth_ghs.setText("1 ETH : " + Utils.getCurrencySymbol("GHS") + df.format(ghseth));

                    //South africa
                    double zar = (first_selected / zar_selected) * 1;
                    btc_zar.setText("1 BTC : " + Utils.getCurrencySymbol("ZAR") + df.format(zar));


                    double zareth = (first_selected1 / zar_selected) * 1;
                    eth_zar.setText("1 ETH : " + Utils.getCurrencySymbol("ZAR") + df.format(zareth));

                    //kenyan
                    double kes = (first_selected / kes_selected) * 1;
                    btc_kes.setText("1 BTC : " + Utils.getCurrencySymbol("KES") + df.format(kes));


                    double keseth = (first_selected1 / kes_selected) * 1;
                    eth_kes.setText("1 ETH : " + Utils.getCurrencySymbol("KES") + df.format(keseth));

                    //British
                    double gbp = (first_selected / gbp_selected) * 1;
                    btc_gbp.setText("1 BTC : " + Utils.getCurrencySymbol("GBP") + df.format(gbp));


                    double gbpeth = (first_selected1 / gbp_selected) * 1;
                    eth_gbp.setText("1 ETH : " + Utils.getCurrencySymbol("GBP") + df.format(gbpeth));

                    //Chinese
                    double ing = (first_selected / ing_selected) * 1;
                    btc_ing.setText("1 BTC : " + Utils.getCurrencySymbol("CNY") + df.format(ing));


                    double ingeth = (first_selected1 / ing_selected) * 1;
                    eth_ing.setText("1 ETH : " + Utils.getCurrencySymbol("CNY") + df.format(ingeth));

                    //stop progressDialog
                    progressDialog.dismiss();

                    //stop swipe view refresh
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), R.string.swipe_down, Toast.LENGTH_LONG).show();


                }

                //when network connection is not successful
                @Override
                public void onFailure(Call<CryptoCrcy> call, Throwable t) {
                    t.printStackTrace();
                    frameLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), R.string.swipe_down, Toast.LENGTH_LONG).show();

                }
            });


        } catch (Exception e) {

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
            }

        }
    };
    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        return;
                    } else {
                        // handle error
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hy, Download this Bitcoin and Ethereum android application, it's very useful, you can track Bitcoin and Ethereum exchange prices and can also convert to your local currency, i Love it! Am sure you will also Check it out using this link here.... https://play.google.com/store/apps/details?id=com.koloapps.contest.cryptocomparecurrencyconverter");
            startActivity(shareIntent);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
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


        } else if (id == R.id.remove) {
            mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001,
                    mPurchaseFinishedListener, "mypurchasetoken");
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
            Intent intent = new Intent(this, Main3Activity.class);
            intent.putExtra("btc", BtcGetUsd);
            intent.putExtra("eth", EthGetUSD);
            startActivity(intent);
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

