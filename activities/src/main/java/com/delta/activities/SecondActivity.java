package com.delta.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {

    private Button mButtonGoFirstActivity = null;
    private Button mButtonDetail = null;
    private TextView mySelection = null;
    public static final int DETAIL_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mySelection = (TextView) findViewById(R.id.userSelection);

        mButtonGoFirstActivity = (Button) findViewById(R.id.goFirstActivity);
        mButtonGoFirstActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FirstActivity.class);
                startActivity(intent);
            }
        });

        mButtonDetail = (Button) findViewById(R.id.goDetailActivity);
        mButtonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("KeyForSending","Some data from the Second Activity");
                startActivityForResult(intent, DETAIL_REQUEST);
            }
        });

        //an implicit Intent receiver
        Intent httpIntent = getIntent();
        String action = httpIntent.getAction();
        if((action != null) && action.equals(Intent.ACTION_VIEW)) {
            Uri data = httpIntent.getData();
            if(data != null) {
                mySelection.setText(data.toString());
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == DETAIL_REQUEST) {
            if (data.hasExtra("KeyForReturning")) {
                String myValue = data.getStringExtra("KeyForReturning");
                mySelection.setText(myValue);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
