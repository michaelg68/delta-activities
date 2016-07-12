package com.delta.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class DetailActivity extends Activity {

    private Button mButtonReturnToSecondActivity = null;
    private Button mButtonPerformSelected = null;
    private Spinner mSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String detailValue = extras.getString("KeyForSending");
            if (detailValue != null) {
                Toast.makeText(this, detailValue, Toast.LENGTH_SHORT).show();
            }
        }

        mSpinner = (Spinner) findViewById(R.id.spinnerSelection);
        mButtonReturnToSecondActivity = (Button) findViewById(R.id.returnToSecondActivity);
        mButtonReturnToSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                String itemSelected = mSpinner.getSelectedItem().toString();
                returnIntent.putExtra("KeyForReturning", itemSelected);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        mButtonPerformSelected = (Button) findViewById(R.id.performImplicit);
        mButtonPerformSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mSpinner.getSelectedItemPosition();
                Intent implicitIntent = null;
                switch (position) {
                    case 0:
                        //nothing selected
                        break;
                    case 1:
                        //go to a web site
                        implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ynet.co.il"));
                        break;
                    case 2:
                        //dial a number
                        implicitIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:035724078"));
                        break;
                    case 3:
                        //load a map
                        implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:30.2715,-97.742"));
                        break;
                    case 4:
                        //take a picture
                        implicitIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                        break;
                    case 5:
                        //show the first contact
                        implicitIntent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/100"));
                        break;
                }
                if (implicitIntent != null) {
                    if (isIntentAvailable(implicitIntent)) {
                        startActivity(implicitIntent);
                    } else {
                        Toast.makeText(view.getContext(), "No application available" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean isIntentAvailable(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
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
