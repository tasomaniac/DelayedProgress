package com.tasomaniac.android.widget.example;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.tasomaniac.android.widget.DelayedProgressDialog;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            MainListFragment fragment = new MainListFragment();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
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
                        .setMessage(Html.fromHtml("ProgressDialog that waits a minimum time to be dismissed before showing.<br>Once visible, the ProgressDialog will be visible for a minimum amount of time to avoid \"flashes\" in the UI.<br><br>Brought to you by <a href=\"http://www.tasomaniac.com\">Said Tahsin Dane</a>."))
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

    @SuppressWarnings("deprecation")
    public static class MyAdapter extends ArrayAdapter<Pair> {
        public MyAdapter(Context context, Pair[] list) {
            super(context, android.R.layout.simple_list_item_2, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TwoLineListItem twoLineListItem;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                twoLineListItem = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
            } else {
                twoLineListItem = (TwoLineListItem) convertView;
            }

            twoLineListItem.getText1().setText(getItem(position).first.toString());
            twoLineListItem.getText2().setText(getItem(position).second.toString());

            return twoLineListItem;
        }
    }

    public static class MainListFragment extends ListFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setListAdapter(new MyAdapter(getActivity(), new Pair[]{
                    Pair.create("Regular ProgressDialog", ""),
                    Pair.create("Delayed ProgressDialog", "ProgressDialog that waits a minimum time to be dismissed before showing"),
                    Pair.create("With custom delay", "Basic progress dialog with default 2 seconds delay. (Default it 500ms)"),
                    Pair.create("Min Show Time", "Once visible, the progress bar will be visible for a minimum amount of time to avoid flashes in the UI.\nWhen you click this, the ProgressDialog will be delayed 1 seconds and then dismissed after exactly 100ms. Because of the minShowTime, it will be visible for 500ms."),
                    Pair.create("No Min Show Time", "This does not have minShowTime (and causes flashes in the UI) to see the difference.")
            }));
        }

        public void onListItemClick(ListView listView, View view, int position, long id) {

            switch (position) {
                case 0:
                    ProgressDialog.show(getActivity(), null, "Loading", true, true);
                    break;
                case 1:
                    DelayedProgressDialog.showDelayed(getActivity(), null, "Loading", true, true);
                    break;
                case 2: {
                    DelayedProgressDialog dialog = DelayedProgressDialog.make(getActivity(),
                            null, "Loading", true, true);
                    dialog.setMinDelay(2000);
                    dialog.show();
                    break;
                }
                case 3: {
                    final DelayedProgressDialog dialog = DelayedProgressDialog.make(getActivity(),
                            null, "Loading", true, true);
                    dialog.setMinDelay(1000);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Items are loaded", Toast.LENGTH_SHORT).show();
                        }
                    }, 1100);
                    break;
                }
                case 4: {
                    final DelayedProgressDialog dialog = DelayedProgressDialog.make(getActivity(),
                            null, "Loading", true, true);
                    dialog.setMinDelay(1000);
                    dialog.setMinShowTime(0);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Items are loaded", Toast.LENGTH_SHORT).show();
                        }
                    }, 1100);
                    break;
                }
            }
        }
    }
}
