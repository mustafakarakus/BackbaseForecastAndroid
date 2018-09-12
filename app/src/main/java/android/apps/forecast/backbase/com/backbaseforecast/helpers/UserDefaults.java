package android.apps.forecast.backbase.com.backbaseforecast.helpers;

import android.apps.forecast.backbase.com.backbaseforecast.base.Keys;
import android.apps.forecast.backbase.com.backbaseforecast.models.BookmarkModel;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class UserDefaults {

    private static Gson gson = new Gson();

    public static String getGson(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setGson(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static List<BookmarkModel> getBookmarks(Context context) {
        String strBookmarks = getGson(context, Keys.BOOKMARK_LIST_KEY);
        Type type = new TypeToken<List<BookmarkModel>>() { }.getType();
        List<BookmarkModel> bookmarks = gson.fromJson(strBookmarks, type);
        return bookmarks != null ?  bookmarks : new ArrayList<BookmarkModel>();
    }

    public static void setBookmarks(Context context, List<BookmarkModel> Bookmarks) {
        setGson(context, Keys.BOOKMARK_LIST_KEY, gson.toJson(Bookmarks));
    }

    public static void removeBookmark(Integer id,Context context) {
        List<BookmarkModel> bookmarks =  getBookmarks(context);
        for (BookmarkModel bookmark:bookmarks) {
            if(bookmark.getId().equals(id)){
              bookmarks.remove(bookmark);
              break;
            }
        }
        setBookmarks(context,bookmarks);
    }
    public static String getUnit(Context context) {
        String unit = getGson(context, Keys.UNIT_KEY);
        if(unit.length() == 0){
            return "metric";
        }
        return unit;
    }

    public static void setUnit(Context context, String unit) {
        setGson(context, Keys.UNIT_KEY, unit);
    }
}
