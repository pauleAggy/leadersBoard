/*
 *@Author MANDENGUE PAULE AGGY
 * this class will handle API calls and API responses
 */

package com.mpa.leaderboard;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import static com.mpa.leaderboard.Constants.*;


public class ApiHandler {
    private ApiHandler(){}

    /*
     *This function helps to build the URL
     */
    public static URL constructUrl(String title) {
        URL url = null;
        String requestUrl = null;
        if(title.equals("iq"))
            requestUrl =LEADER_BASE_API_URL.concat(LEADER_KEY_IQ);
        else
            if(title.equals("learner"))
                requestUrl =LEADER_BASE_API_URL.concat(LEADER_KEY_LEARNER);
        Uri uri = Uri.parse( requestUrl).buildUpon()
                .build();
        try {
            url = new URL(uri.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
        }
    /*
     *This function helps to collect JSON DATAS
     */
    public static String collectJson(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e){
            Log.d("Error", e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    public static ArrayList<IqLeader> returnIqLeadersFromJson(String json) {
        final String NAME = "name";
        final String SCORE = "score";
        final String COUNTRY = "country";
        final String BADGE_URL = "badgeUrl";

        ArrayList<IqLeader> iqLeaders = new ArrayList<IqLeader>();
        try {
            JSONObject jsonIqLeader = null;
            JSONArray jsonIqLeaders = new JSONArray(json);
            if(jsonIqLeaders != null) {
                for (int i = 0; i < jsonIqLeaders.length(); i++) {
                    jsonIqLeader = jsonIqLeaders.getJSONObject(i);
                    if(jsonIqLeader != null) {
                    if (jsonIqLeader.has(NAME)
                            && jsonIqLeader.has(COUNTRY)
                            && jsonIqLeader.has(BADGE_URL)
                            && jsonIqLeader.has(SCORE) ) {

                    IqLeader iqLeader = new IqLeader(
                            jsonIqLeader.getString(NAME),
                            jsonIqLeader.getString(COUNTRY),
                            jsonIqLeader.getString(BADGE_URL),
                            jsonIqLeader.getInt(SCORE));
                    iqLeaders.add(iqLeader);
                    }
                }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return iqLeaders;
    }

    public static ArrayList<LearnerLeader> returnLearnerLeadersFromJson(String json) {
        final String NAME = "name";
        final String HOURS = "hours";
        final String COUNTRY = "country";
        final String BADGE_URL = "badgeUrl";

        ArrayList<LearnerLeader> learnerLeaders = new ArrayList<LearnerLeader>();
        try {
            JSONObject jsonLearnerLeader = null;
            JSONArray jsonLearnerLeaders = new JSONArray(json);
            if(jsonLearnerLeaders != null) {
                for (int i = 0; i < jsonLearnerLeaders.length(); i++){
                    jsonLearnerLeader = jsonLearnerLeaders.getJSONObject(i);
                    if(jsonLearnerLeader != null) {
                        if(jsonLearnerLeader.has(NAME)
                                && jsonLearnerLeader.has(COUNTRY)
                                && jsonLearnerLeader.has(BADGE_URL)
                                && jsonLearnerLeader.has(HOURS)){

                        LearnerLeader learnerLeader = new LearnerLeader(
                                jsonLearnerLeader.getString(NAME),
                                jsonLearnerLeader.getString(COUNTRY),
                                jsonLearnerLeader.getString(BADGE_URL),
                                jsonLearnerLeader.getInt(HOURS));
                        learnerLeaders.add(learnerLeader);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return learnerLeaders;
    }
}
