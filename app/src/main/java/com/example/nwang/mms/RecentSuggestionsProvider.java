package com.example.nwang.mms;


import android.content.SearchRecentSuggestionsProvider;

/**
 * Save recent search suggestion to a local db
 */
public class RecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY =
            RecentSuggestionsProvider.class.getName();

    public static final int MODE = DATABASE_MODE_QUERIES;

    public RecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
