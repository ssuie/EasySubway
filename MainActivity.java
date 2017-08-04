package com.example.soyeon.gateinfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import kr.go.seoul.trafficsubway.Common.WebViewInterfaceTypeB;

public class MainActivity extends AppCompatActivity {

    private String openAPIKey = "50707a52796f396f3733786d595179";
    private String subwayLocationAPIKey = "45425364766f396f373947755a6f41";
    private WebView lineMapWebview;
    private WebViewInterfaceTypeB mWebViewInterface;
    Button btn_search;
    SearchView search;
    Bitmap resize;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if (getIntent() != null && getIntent().getStringExtra("OpenAPIKey") != null)
            openAPIKey = getIntent().getStringExtra("OpenAPIKey");
        if (getIntent() != null && getIntent().getStringExtra("SubwayLocationAPIKey") != null)
            subwayLocationAPIKey = getIntent().getStringExtra("SubwayLocationAPIKey");


        subwayButtonTypeB.setOpenAPIKey(key);                           //지하철 기본 apikey(실시간 도착정보 조회가 가능한 키로 사용시 setsubwayLocationAPIKey 생략가능
        subwayButtonTypeB.setsubwayLocationAPIKey(subwayKey);           //지하철 실시간 도착정보용 apikey(B타입과 Mini타입에서만 사용)
        subwayTypeMini.setOpenAPIKey(key);
        subwayTypeMini.setsubwayLocationAPIKey(subwayKey);
*/
        btn_search = (Button) findViewById(R.id.btn_search);
        //search = (SearchView) findViewById(R.id.search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_search.setVisibility(view.GONE);
                //search.setVisibility(view.VISIBLE);
            }
        });



        lineMapWebview = (WebView) findViewById(R.id.line_map_webview);
        lineMapWebview.setWebViewClient(new WebViewClient());
        lineMapWebview.getSettings().setJavaScriptEnabled(true);
        lineMapWebview.getSettings().setBuiltInZoomControls(true);
        lineMapWebview.getSettings().setSupportZoom(true);
        lineMapWebview.getSettings().setDisplayZoomControls(false);
        lineMapWebview.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebViewInterface = new WebViewInterfaceTypeB(this, lineMapWebview, openAPIKey, subwayLocationAPIKey);
        lineMapWebview.addJavascriptInterface(mWebViewInterface, "Android");
        lineMapWebview.loadUrl("file:///android_asset/mSeoul_Subway.html");
    }
}
