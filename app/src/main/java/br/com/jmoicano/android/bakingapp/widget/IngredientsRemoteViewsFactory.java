package br.com.jmoicano.android.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import br.com.jmoicano.android.bakingapp.R;
import br.com.jmoicano.android.bakingapp.data.model.Ingredient;

import static br.com.jmoicano.android.bakingapp.widget.IngredientWidget.getIngredients;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<Ingredient> ingredients;

    public IngredientsRemoteViewsFactory(Context context) {
        this.context = context;
        ingredients = getIngredients();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredients = getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null)
            return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_list_item);
        remoteViews.setTextViewText(R.id.tv_ingredient_name, ingredients.get(position).getIngredientName());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
