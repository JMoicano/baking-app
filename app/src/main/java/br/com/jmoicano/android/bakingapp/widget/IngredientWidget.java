package br.com.jmoicano.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import br.com.jmoicano.android.bakingapp.R;
import br.com.jmoicano.android.bakingapp.data.model.Ingredient;


/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    private static final String INGREDIENTS_EXTRA = "ingredients";

    private static List<Ingredient> mIngredients;

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds, List<Ingredient> ingredients) {
        for (int id : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ingredients, id);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<Ingredient> ingredients,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        Intent intent = new Intent(context, IngredientsRemoteViewsService.class);
        views.setRemoteAdapter(R.id.lv_ingredients, intent);

        if (ingredients != null) {
            mIngredients = ingredients;
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_ingredients);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void sendBroadcast(Context context, List<Ingredient> ingredients) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) ingredients);
        intent.putExtras(bundle);
        intent.setComponent(new ComponentName(context, IngredientWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            List<Ingredient> ingredients = intent.getParcelableArrayListExtra(INGREDIENTS_EXTRA);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(intent.getComponent());
            IngredientWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds, ingredients);
            super.onReceive(context, intent);
        }
    }


    public static List<Ingredient> getIngredients() {
        return mIngredients;
    }

}

