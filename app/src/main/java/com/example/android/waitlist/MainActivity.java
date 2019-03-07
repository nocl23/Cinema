package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mFilm;
    private EditText mRealisation;
    private EditText mScenario;
    private EditText mMusique;
    private EditText mDescription;
    private DatePicker mDate ;
    private TimePicker mHoraire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        mFilm = (EditText) this.findViewById(R.id.film_title_edit_text);
        mRealisation = (EditText) this.findViewById(R.id.realisation_edit_text);
        mRealisation.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        mScenario = (EditText) this.findViewById(R.id.scenario_edit_text);
        mScenario.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        mMusique = (EditText) this.findViewById(R.id.musique_edit_text);
        mMusique.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        mDescription = (EditText) this.findViewById(R.id.description_edit_text);
        mDate = (DatePicker) this.findViewById(R.id.date_edit_text);
        mHoraire = (TimePicker) this.findViewById(R.id.horaire_edit_text);

        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DB helper (this will create the DB if run for the first time)
        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        // Get all guest info from the database and save in a cursor
        Cursor cursor = getAllGuests();

        // Create an adapter for that cursor to display the data
        mAdapter = new GuestListAdapter(this, cursor);

        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {
        if (mFilm.getText().length() == 0 ||
                mRealisation.getText().length() == 0 ||
                mDescription.getText().length() == 0 || mMusique.getText().length() == 0 ||
        mScenario.getText().length() == 0) {
            return;
        }

        Date filmHoraire = new Date(mDate.getYear(),mDate.getMonth(),mDate.getDayOfMonth(),mHoraire.getHour(),mHoraire.getMinute());


        // Add guest info to mDb
        addNewGuest(
                mFilm.getText().toString(),
                Integer.parseInt(mRealisation.getText().toString()),
                Integer.parseInt(mScenario.getText().toString()),
                filmHoraire.toString(),
                Integer.parseInt(mMusique.getText().toString()),
                mDescription.getText().toString());

        mAdapter.swapCursor(getAllGuests());

        //clear UI text fields
        mFilm.clearFocus();
        mRealisation.clearFocus();
        mMusique.clearFocus();
        mScenario.clearFocus();
        mDescription.clearFocus();
        mHoraire.clearFocus();
        mDate.clearFocus();

        mFilm.getText().clear();
        mRealisation.getText().clear();
        mMusique.getText().clear();
        mScenario.getText().clear();
        mDescription.getText().clear();

    }



    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllGuests() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TITLE
        );
    }

    /**
     *
     * @param title
     * @param realisation
     * @param scenario
     * @param musique
     * @param description
     * @return
     */
    private long addNewGuest(String title, int realisation, int scenario, String date, int musique, String description) {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_TITLE, title);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_REALISATION, realisation);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_SCENARIO, scenario);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_HORAIRE,date);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_MUSIQUE, musique);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_DESCRIPTION, description);
        mAdapter.notifyDataSetChanged();


        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

}