package android.apps.forecast.backbase.com.backbaseforecast.helpers;

import android.apps.forecast.backbase.com.backbaseforecast.base.Keys;
import android.apps.forecast.backbase.com.backbaseforecast.models.BookmarkModel;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
        return gson.fromJson(strBookmarks, type);
    }

    public static void setBookmarks(Context context, List<BookmarkModel> Bookmarks) {
        setGson(context, Keys.BOOKMARK_LIST_KEY, gson.toJson(Bookmarks));
    }
}
