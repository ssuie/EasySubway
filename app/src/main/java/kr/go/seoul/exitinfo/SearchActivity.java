package kr.go.seoul.exitinfo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    public static MyMenu menu = new MyMenu();
    ArrayList<String> items = new ArrayList<>();
    ListViewAdapter adapter = new ListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        items.addAll(menu.getMenu("subway"));

        final AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.searchText);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,items);
        edit.setAdapter(arrayAdapter);

        // 메뉴 선택 시 해당 메뉴 Recipe Activity. start()
        edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                String itemTitle = parent.getItemAtPosition(position).toString();
                Log.i(itemTitle, itemTitle);
                Bundle bundleData = new Bundle();
                bundleData.putString("STR_DATA", itemTitle);
                intent.putExtra("TEST", bundleData);
                startActivity(intent);
               */
                Intent intent = new Intent();
                intent.putExtra("INPUT_TEXT", edit.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        ListView list = (ListView)findViewById(R.id.listViewSearch);

        // 메뉴 선택 시 해당 메뉴 Recipe Activity. start()
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              ListViewItem item = (ListViewItem)parent.getItemAtPosition(position);
                String itemTitle = item.getTitle();

                Intent intent = new Intent(SearchActivity.this, MainActivity.class);

                Bundle bundleData = new Bundle();
                bundleData.putString("STR_DATA", itemTitle);
                intent.putExtra("TEST", bundleData);

                startActivity(intent);
            }
        });

        list.setAdapter(adapter);

        Iterator<String> iterator = items.iterator();

        // 모든 메뉴를 리스트에 동적 추가
        while(iterator.hasNext()) {
            String menuTitle = iterator.next();
            adapter.addItem(menuTitle);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}