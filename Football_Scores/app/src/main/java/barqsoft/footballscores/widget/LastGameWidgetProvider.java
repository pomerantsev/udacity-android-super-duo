package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by pavel on 12/7/15.
 */
public class LastGameWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int homeCrestResourceId = R.drawable.arsenal;
        String homeName = "Arsenal";
        int homeGoals = 1;
        int awayGoals = 0;
        String scoreString = Utilies.getScores(homeGoals, awayGoals);
        String timeString = "18:00";
        int awayCrestResourceId = R.drawable.chelsea;
        String awayName = "Chelsea";

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_last_game);

            views.setImageViewResource(R.id.home_crest, homeCrestResourceId);
            views.setTextViewText(R.id.home_name, homeName);
            views.setTextViewText(R.id.score_textview, scoreString);
            views.setTextViewText(R.id.data_textview, timeString);
            views.setImageViewResource(R.id.away_crest, awayCrestResourceId);
            views.setTextViewText(R.id.away_name, awayName);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                views.setContentDescription(R.id.home_name,
                        context.getString(R.string.teams_description, homeName, awayName));
                views.setContentDescription(R.id.away_name, "\u00A0");
                views.setContentDescription(R.id.data_textview,
                        context.getString(R.string.time_description, timeString));
                views.setContentDescription(R.id.score_textview,
                        Utilies.getScoreDescription(context, homeGoals, awayGoals));
            }

            Intent launchIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
