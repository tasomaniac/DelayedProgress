package com.tasomaniac.android.widget.example;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Dialog d = new AlertDialog.Builder(this)
                        .setTitle(R.string.action_about)
                        .setMessage(Html.fromHtml("ProgressBar and ProgressDialog that waits a minimum time to be dismissed before showing.<br>Once visible, the ProgressDialog will be visible for a minimum amount of time to avoid \"flashes\" in the UI.<br><br>Brought to you by <a href=\"http://www.tasomaniac.com\">Said Tahsin Dane</a>."))
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                ((TextView) d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                return true;
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tasomaniac/DelayedProgress")));
                return true;
            default:
                return false;
        }
    }
}
