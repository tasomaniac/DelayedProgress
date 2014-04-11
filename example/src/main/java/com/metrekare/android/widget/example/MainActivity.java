package com.metrekare.android.widget.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import com.metrekare.android.widget.ContentLoadingProgressDialog;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new MyAdapter(this, new Pair[]{
                Pair.create("Regular ProgressDialog", ""),
                Pair.create("Delayed ProgressDialog", "ProgressDialog that waits a minimum time to be dismissed before showing"),
                Pair.create("With custom delay", "Basic progress dialog with default 2 seconds delay. (Default it 500ms)"),
                Pair.create("Min Show Time", "Once visible, the progress bar will be visible for a minimum amount of time to avoid flashes in the UI.\nWhen you click this, the ProgressDialog will be delayed 1 seconds and then dismissed after exactly 100ms. Because of the minShowTime, it will be visible for 500ms."),
                Pair.create("No Min Show Time", "This does not have minShowTime (and causes flashes in the UI) to see the difference.")
        }));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position)
        {
            case 0:
                ProgressDialog.show(this, null, "Loading", true, true);
                break;
            case 1:
                ContentLoadingProgressDialog.showDelayed(this, null, "Loading", true, true);
                break;
            case 2:
                ContentLoadingProgressDialog.makeDelayed(this, null, "Loading", true, true).minDelay(2000).show();
                break;
            case 3: {
                final ContentLoadingProgressDialog dialog = ContentLoadingProgressDialog.makeDelayed(this, null, "Loading", true, true).minDelay(1000);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Items are loaded", Toast.LENGTH_SHORT).show();
                    }
                }, 1100);
                break;
            }
            case 4: {
                final ContentLoadingProgressDialog dialog = ContentLoadingProgressDialog.makeDelayed(this, null, "Loading", true, true).minDelay(1000).minShowTime(0);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Items are loaded", Toast.LENGTH_SHORT).show();
                    }
                }, 1100);
                break;
            }
        }
    }

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
                        .setMessage(Html.fromHtml("ProgressDialog that waits a minimum time to be dismissed before showing.<br>Once visible, the ProgressDialog will be visible for a minimum amount of time to avoid \"flashes\" in the UI.<br><br>Brought to you by the <a href=\"http://www.tasomaniac.com\">Said Tahsin Dane</a> from <a href=\"https://www.metrekare.com\">Metrekare.com</a> (A destruptive real-estate startup from Turkey)."))
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                ((TextView) d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                return true;
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/metrekare/ContentLoadingProgressDialog")));
                return true;
            case R.id.action_android_app:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.metrekare.android.app")));
                return true;
            default:
                return false;
        }
    }

    public class MyAdapter extends ArrayAdapter<Pair<String, String>>
    {
        public MyAdapter(Context context, Pair<String, String>[] list) {
            super(context, android.R.layout.simple_list_item_2, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TwoLineListItem twoLineListItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                twoLineListItem = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
            } else {
                twoLineListItem = (TwoLineListItem) convertView;
            }

            twoLineListItem.getText1().setText(getItem(position).first);
            twoLineListItem.getText2().setText(getItem(position).second);

            return twoLineListItem;        }
    }
}
