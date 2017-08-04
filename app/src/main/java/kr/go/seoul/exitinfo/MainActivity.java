package kr.go.seoul.exitinfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String key = "48435751656d696335367153677846";      //吏��븯泥� �떎�떆媛� �젙蹂� �젣�쇅�븯怨� �궗�슜媛��뒫
    URL apiURL = null;

    EditText edit;
    Button btn;
    TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit);
        btn = (Button)findViewById(R.id.btn);
        text = (TextView)findViewById(R.id.text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessNetworkSubwayFirstLastTimeInfoThread thread = new ProcessNetworkSubwayFirstLastTimeInfoThread();
                thread.execute(edit.getText().toString());

            }
        });
    }

    class ProcessNetworkSubwayFirstLastTimeInfoThread extends AsyncTask<String, Void, String> {

        String subwayId = "";
        String ectrcNo ="";
        String subwayNm="";
        String cfrBuild="";

        public ProcessNetworkSubwayFirstLastTimeInfoThread() {
        }

        protected String doInBackground(String... strings) {
            this.executeClient(strings);
            return "";
        }

        protected void onPostExecute(String result) {
            text.setText(cfrBuild);
        }

        void executeClient(String[] strings) {

            InputStream in = null;
            XmlPullParserFactory factory;
            XmlPullParser xpp;

            try
            {
                apiURL = new URL("http://swopenapi.seoul.go.kr/api/subway/" + key + "/xml/gateInfo/0/20/"+edit.getText().toString());
                in = apiURL.openStream();
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                xpp = factory.newPullParser();
                xpp.setInput(in, "UTF-8");
                int eventType1 = xpp.getEventType();
                String tagName = "";
                String updnLine = "";

                for (boolean isItemTag = false; eventType1 != 1; eventType1 = xpp.next()) {
                    if (eventType1 == 2) {
                        tagName = xpp.getName();
                        if (tagName.equals("row")) {
                            isItemTag = true;
                        }
                    } else if (eventType1 == 4) {
                        if (isItemTag && !tagName.equals("") && !xpp.getText().equals("")) {
                            if (tagName.equals("subwayId")) {
                                subwayId = xpp.getText();
                            } else if (tagName.equals("subwayNm")) {
                                cfrBuild += xpp.getText()+") : ";
                            } else if (tagName.equals("ectrcNo")) {
                                cfrBuild += xpp.getText()+"번 출구(";
                            } else if(tagName.equals("cfrBuild")) {
                                cfrBuild += xpp.getText() + "\n";
                            } else if(tagName.equals("updnLine")) {
                                updnLine = xpp.getText();
                            }
                        }
                    } else if (eventType1 == 3) {
                        tagName = xpp.getName();
                        if (tagName.equals("row")) {
                            isItemTag = false;
                            if (subwayId.equals("1065")) {
                                if (updnLine.equals("0")) {
                                    updnLine = "1";
                                } else {
                                    updnLine = "0";
                                }
                            }
                        } else {
                            tagName = "";
                        }
                    }
                }
            } catch (MalformedURLException var22) {
                var22.printStackTrace();
            } catch (IOException var23) {
                var23.printStackTrace();
            } catch (XmlPullParserException var24) {
                var24.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException var21) {
                        var21.printStackTrace();
                    }
                }

            }

        }
    }
}
