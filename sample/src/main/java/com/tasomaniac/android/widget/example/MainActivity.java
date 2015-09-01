package com.tasomaniac.android.widget.example;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.tasomaniac.android.widget.DelayedProgressDialog;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            MainListFragment fragment = new MainListFragment();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
        }
    }

    public static class MainListFragment extends ListFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setListAdapter(new PairAdapter(getActivity(), new Pair[]{
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
