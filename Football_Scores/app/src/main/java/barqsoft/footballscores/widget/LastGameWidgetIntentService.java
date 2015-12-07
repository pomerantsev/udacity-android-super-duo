package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by pavel on 12/7/15.
 */
public class LastGameWidgetIntentService extends IntentService {
    private static final String[] GAME_COLUMNS = {
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL
    };
    private static final int INDEX_TIME = 0;
    private static final int INDEX_HOME_TEAM = 1;
    private static final int INDEX_AWAY_TEAM = 2;
    private static final int INDEX_HOME_GOALS = 3;
    private static final int INDEX_AWAY_GOALS = 4;

    public LastGameWidgetIntentService() {
        super("LastGameWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, LastGameWidgetProvider.class)
        );
        Cursor data = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, GAME_COLUMNS,
                null, null, null);

        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        String homeName = data.getString(INDEX_HOME_TEAM);
        int homeCrestResourceId = Utilies.getTeamCrestByTeamName(this, homeName);
        int homeGoals = data.getInt(INDEX_HOME_GOALS);
        int awayGoals = data.getInt(INDEX_AWAY_GOALS);
        String scoreString = Utilies.getScores(this, homeGoals, awayGoals);
        String timeString = data.getString(INDEX_TIME);
        String awayName = data.getString(INDEX_AWAY_TEAM);
        int awayCrestResourceId = Utilies.getTeamCrestByTeamName(this, awayName);
        data.close();

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_last_game);

            views.setImageViewResource(R.id.home_crest, homeCrestResourceId);
            views.setTextViewText(R.id.home_name, homeName);
            views.setTextViewText(R.id.score_textview, scoreString);
            views.setTextViewText(R.id.data_textview, timeString);
            views.setImageViewResource(R.id.away_crest, awayCrestResourceId);
            views.setTextViewText(R.id.away_name, awayName);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                views.setContentDescription(R.id.home_name,
                        getString(R.string.teams_description, homeName, awayName));
                views.setContentDescription(R.id.away_name, "\u00A0");
                views.setContentDescription(R.id.data_textview,
                        getString(R.string.time_description, timeString));
                views.setContentDescription(R.id.score_textview,
                        Utilies.getScoreDescription(this, homeGoals, awayGoals));
            }

            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
