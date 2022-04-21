package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private ListView listView;
    private Bundle bundle;
    private Button back;
    private Intent toSearch;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        this.setTitle("Product List");

        listView = findViewById(R.id.searchResult);
        bundle = getIntent().getExtras();
        list = bundle.getStringArrayList("list");
        back = findViewById(R.id.cancel);

        toSearch = new Intent(this, Search.class);
        toSearch.addFlags(toSearch.FLAG_ACTIVITY_CLEAR_TOP);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item, list);
        listView.setAdapter(adapter);

        back.setOnClickListener(v -> {
            startActivity(toSearch);
        });
    }
}