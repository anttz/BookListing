package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tzouanakos on 13/03/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param books    The objects to represent in the ListView.
     */
    public BookAdapter(Context context, int resource, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);
        TextView titleTextview = (TextView) convertView.findViewById(R.id.title_textview);
        titleTextview.setText(currentBook.getmTitle());
        TextView authorTextview = (TextView) convertView.findViewById(R.id.author_textview);
        authorTextview.setText(currentBook.getmAuthor());
        return convertView;
    }
}
