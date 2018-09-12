package android.apps.forecast.backbase.com.backbaseforecast.adapters;

import android.apps.forecast.backbase.com.backbaseforecast.models.BookmarkModel;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookmarkAdapter extends BaseAdapter {


    private List<BookmarkModel> bookmarks;
    private Context context;

    public BookmarkAdapter(Context context, List<BookmarkModel> bookmarks) {
        super();
        this.bookmarks = bookmarks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookmarks.size();
    }

    @Override
    public Object getItem(int position) {
        return bookmarks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView text1 = rowView.findViewById(android.R.id.text1);
        BookmarkModel bookmarkModel = bookmarks.get(position);
        text1.setText(bookmarkModel.getName());
        text1.setTextColor(Color.BLACK);
        rowView.setTag(bookmarkModel.getId());
        return rowView;
    }

}
