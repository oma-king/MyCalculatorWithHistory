package com.example.mycalculatorwithhistory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class lsvHistoryCursorAdapter extends CursorAdapter {
    public lsvHistoryCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_history, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String calculation = cursor.getString(cursor.getColumnIndexOrThrow("Operation"));
        String result = cursor.getString(cursor.getColumnIndexOrThrow("Result"));

        TextView Operation = view.findViewById(R.id.Operation);
        TextView Result = view.findViewById(R.id.Result);

        Operation.setText(calculation);
        Result.setText(result);
    }
}
