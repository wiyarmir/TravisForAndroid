package es.guillermoorellana.travisforandroid.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.ui.activity.MainActivity;

public class TravisWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int... appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent svcIntent = new Intent(context, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_repos);

            // Non-deprecated version requires API>14
            //noinspection deprecation
            widget.setRemoteAdapter(appWidgetId, R.id.build_list, svcIntent);

            Intent action = new Intent(context, MainActivity.class);
            widget.setPendingIntentTemplate(
                    R.id.build_list,
                    PendingIntent.getActivity(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT)
            );

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
