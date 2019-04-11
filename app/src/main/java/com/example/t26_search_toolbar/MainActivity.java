package com.example.t26_search_toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListview;
    private TextView mTextview;
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //In order for android search menu to appear in toolbar, we need to use setSupportActionBar
        setSupportActionBar(mToolbar);


        mListview= (ListView) findViewById(R.id.list);
        mTextview= (TextView) findViewById(R.id.textView);



        //array adapter that will hold the string array data
        mAdapter= new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.months_array));

        // set the listview to the adapter so that the data will show up in the list
        mListview.setAdapter(mAdapter);

        // Now let’s make the listview clickable by calling ListView.setOnItemClickListener,
        // once user clicks on any item from the list we will show the selected item inside a toast message
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //when the user searches for something and it’s not available in that listview,
        // instead of showing an empty screen we show a textview that says (No results)

        mListview.setEmptyView(mTextview);

    }// End of MainActivity onCreate


    //for the search menu, first thing we need to do is override onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // next will be inflate the menu file (menu.xml) that have the search icon,
        // so that we can have access to the search menu item
        MenuInflater mMenuInflater= getMenuInflater();
        mMenuInflater.inflate(R.menu.menu, menu);

       // getMenuInflater().inflate(R.menu.menu, menu);

        //declare the search menu item
        MenuItem mSearch = menu.findItem(R.id.action_search);

        //Now that we have declared the search menu item,
        // we can declare SearchView that we are going to use it to perform the actual search
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        //In order to detect/listen to the user when performing a search request,
        // we will use SearchView.setOnQueryTextListener
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Inside onQueryTextChange method is where we will listen to the user typed query and
                // instantly show the result from the android adapter that holds our string array data
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }
}

