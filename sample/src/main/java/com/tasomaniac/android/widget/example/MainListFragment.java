package com.tasomaniac.android.widget.example;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.tasomaniac.android.widget.DelayedProgressDialog;

public class MainListFragment extends ListFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new PairAdapter(getActivity(), new Pair[]{
                Pair.create("Regular ProgressDialog", "Will be shown immediately after you click\n"),
                Pair.create("Delayed ProgressDialog", "ProgressDialog that waits a minimum time to be dismissed before showing\n"),
                Pair.create("With custom delay", "Basic progress dialog with 2 seconds delay. (Default it 500ms)\n"),
                Pair.create("Min Show Time", "Once visible, the progress bar will be visible for a minimum amount of time to avoid flashes in the UI.\nIn this example, the ProgressDialog will be dismissed right after it is shown. Because of the minShowTime, it will be visible for 1 second even after it is dismissed.\n"),
                Pair.create("No Min Show Time", "This does not have minShowTime (and causes flashes in the UI) to see the difference.\n")
        }));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int margin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
        getListView().setPadding(margin, margin, margin, margin);
        getListView().setClipToPadding(false);
    }

    @Override
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
                dialog.setMinShowTime(1000);
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
