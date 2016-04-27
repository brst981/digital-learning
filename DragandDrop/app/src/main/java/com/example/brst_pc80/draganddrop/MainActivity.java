package com.example.brst_pc80.draganddrop;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

public class MainActivity extends Activity {
DynamicListView mListView;
    public static int [] prgmImages={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView=(DynamicListView)findViewById(R.id.dynamiclistview);

        // setSupportActionBar(toolbar);
       final custom myAdapter = new custom(this,prgmNameList,prgmImages);
       AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(myAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(myAdapter);

        mListView.enableDragAndDrop();
        mListView.setDraggableManager(new TouchViewDraggableManager(R.id.textView1));

        mListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               // mListView.startDragging(position);
                                Toast.makeText(getApplicationContext(),"call",Toast.LENGTH_SHORT).show();

                                //Your code to run in GUI thread here
                            }//public void run() {
                        });
                        return true;
                    }
                }
        );
        mListView.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            //myAdapter.remove(position);
                            Log.e("swipee", "swipee");
                        }
                    }
                }
        );

        /*SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(myAdapter, MainActivity.this,
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            //mAdapter.remove(position);
                        }
                    }
                }
        );
        swipeUndoAdapter.setAbsListView(mListView);
        mListView.setAdapter(swipeUndoAdapter);
        mListView.enableSimpleSwipeUndo();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
