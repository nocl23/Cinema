package com.example.android.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.waitlist.data.WaitlistContract;

import java.util.ArrayList;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor the db cursor with waitlist data to display
     */
    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String name = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_TITLE));
        String realisation = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_REALISATION));
        String scenario = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_SCENARIO));
        String musique = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_MUSIQUE));
        String description = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_DESCRIPTION));
        String horaire = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_HORAIRE));


        long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));

        holder.nameTextView.setText(name);
        holder.realisation_view.setText(realisation + " /10");
        holder.scenario_view.setText(scenario + " /10");
        holder.musique_view.setText(musique +" /10");
        holder.description_view.setText(description);
        holder.horaire_view.setText(horaire);

        holder.itemView.setTag(id);
    }

    public void swapCursor(Cursor newCursor) {

        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView realisation_view;
        TextView scenario_view;
        TextView description_view;
        TextView horaire_view;
        TextView musique_view;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            realisation_view = (TextView) itemView.findViewById(R.id.realisation_view);
            scenario_view = (TextView) itemView.findViewById(R.id.scenario_view);
            description_view = (TextView) itemView.findViewById(R.id.description_view);
            horaire_view = (TextView) itemView.findViewById(R.id.time_view);
            musique_view = (TextView) itemView.findViewById(R.id.musique_view);

        }

    }
}