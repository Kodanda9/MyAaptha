package com.cpetsol.cpetsolutions.myaaptha.helper.navigation;

import android.content.Context;

import com.cpetsol.cpetsolutions.myaaptha.R;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by msahakyan on 22/10/15.
 */
public class ExpandableListDataSource {

    /**
     * Returns fake data of films
     *
     * @param context
     * @return
     */
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();

        List<String> filmGenres = Arrays.asList(context.getResources().getStringArray(R.array.film_genre));

        List<String> actionFilms = Arrays.asList(context.getResources().getStringArray(R.array.actionFilms));
        List<String> schManagementFilms = Arrays.asList(context.getResources().getStringArray(R.array.schManagement));
        List<String> reportFilms = Arrays.asList(context.getResources().getStringArray(R.array.reports));
        List<String> comedyFilms = Arrays.asList(context.getResources().getStringArray(R.array.allenbps_array));
//        expandableListData.put(filmGenres.get(0), actionFilms);
//        expandableListData.put(filmGenres.get(1), schManagementFilms);
//        expandableListData.put(filmGenres.get(2), reportFilms);
////        expandableListData.put(filmGenres.get(3), thrillerFilms);
//        expandableListData.put(filmGenres.get(3), comedyFilms);
        expandableListData.put(filmGenres.get(0), comedyFilms);

        return expandableListData;
    }
}
