package com.example.bakingcakes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.Models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Helper methods related to requesting and receiving Cake detailed from the given json.
 */
@SuppressWarnings("StringConcatenationInLoop")
public final class Utils {

    private static final String LOG_TAG = Utils.class.getName();
    //in this project we were given JSON data about 4 recipes of cakes (only):
    public final static String GIVEN_JSON_DATA = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    // for some reason in the GIVEN_JSON_DATA we don't have any cake images, thi final product,
    // so I've searched in the https://www.pexels.com and have found those:
    private final static String NUTELLA_PIE_URL = "https://user-images.githubusercontent.com/36941009/52073866-6b9a0700-253d-11e9-9d97-a55775bc0290.jpg";

    private final static String BROWNIES_URL = "https://user-images.githubusercontent.com/36941009/52075659-b1f16500-2541-11e9-8577-c8e4c7d0de59.jpeg";
    private final static String CHEESECAKE = "https://user-images.githubusercontent.com/36941009/52075732-d9483200-2541-11e9-9208-e86d378bc5cd.jpg";

    //this one from here: https://unsplash.com/photos/B4QQAYBn8fU :
    private final static String YELLOW_CAKE_URL = "https://user-images.githubusercontent.com/36941009/52075772-f977f100-2541-11e9-8260-1fa604a77ddc.jpg";
    public static String cakesNamesToShare;

    private Utils() {
    }

    private static List<Cake> extractFeatureFromJson(String jsonResponse) {
        int cakeId;
        String cakeName;
        String servings;
        String cakeImage;
        cakesNamesToShare = "";

        /*If the JSON string is empty or null, then return early.**/
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        /*Create an empty ArrayList that we can start adding cakes to**/
        List<Cake> cakes = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                cakeId = result.optInt("id");
                cakeName = result.getString("name");
                servings = result.getString("servings");

                JSONArray ingredients = result.getJSONArray("ingredients");

                List<Ingredient> listOfIngredients = extractIngredientsFromJson(ingredients);

                JSONArray cakeSteps = result.getJSONArray("steps");
                Step[] steps = extractStepsFromJson(cakeSteps);

                cakeImage = setUpImage(cakeId);

                // save cakes names to share via fab from the MainActivity
                cakesNamesToShare = cakesNamesToShare + "\n" + cakeName;

                Cake cake = new Cake(cakeId, cakeName, listOfIngredients, steps, servings, convertToBitmapImage(cakeImage));
                cakes.add(cake);
            }

        } catch (JSONException e) {
            /* an error is thrown when executing any of the above statements in the "try" block,
             // catch the exception here, so the app doesn't crash. Print a log message
             // with the message from the exception.**/
            Log.e("Utils", "Problem parsing the news JSON results", e);
        }
        /* Return the list of cakes  **/
        return cakes;
    }

    public static String saveCakesNamesToShare() {
        // save cakes names to share from  the MainActivity
        return cakesNamesToShare;
    }


    private static String setUpImage(int cakeId) {

        String imageString = "";
        switch (cakeId) {
            case 1:
                imageString =NUTELLA_PIE_URL;
                return imageString;
            case 2:
                imageString = BROWNIES_URL;
                return imageString;
            case 3:
                imageString = YELLOW_CAKE_URL;
                return imageString;
            case 4:
                imageString = CHEESECAKE;
                return imageString;
        }
        return imageString;
    }
    /**
     * method to convert String thumbnail (which holds URL link of the image of the current cake)
     * to Bitmap of this image
     * https://stackoverflow.com/questions/6932369/inputstream-from-a-url
     * https://www.codota.com/code/java/methods/android.graphics.BitmapFactory/decodeStream
     **/
    private static Bitmap convertToBitmapImage(String thumbnail) {
        Bitmap bitmap = null;
        try {
            InputStream stream = new URL(thumbnail).openStream();
            bitmap = BitmapFactory.decodeStream(stream);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Failed to create bitmap", e);

        }
        return bitmap;
    }

    /**
     * Returns new URL object from the given string URL GIVEN_JSON_DATA.
     */
    private static URL createUrl() {

        URL url = null;
        try {
            url = new URL(GIVEN_JSON_DATA);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        /* If the URL is null, then return early. **/
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /*If the request was successful (response code 200 or urlConnection.HTTP_OK),
             // then read the input stream and parse the response. **/
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                /* Closing the input stream could throw an IOException, which is why
                 // the makeHttpRequest(URL url) method signature specifies than an IOException
                 // could be thrown. **/
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /* the fetchData() helper method that ties all the steps together -
      creating a URL, sending the request, processing the response.
      Since this is the only “public” Utils method that the MovieAsyncTask needs to interact with,
      make all other helper methods in Utils “private”.**/

    /**
     * Query and return a list of Cake objects.
     */
    public static List<Cake> fetchData() {

        Log.i(LOG_TAG, "Test:  fetchData called");

//
//        /** To force the background thread to sleep for 2 seconds,
//         * we are temporarily simulating a very slow network response time.
//         * We are “pretending” that it took a long time to fetch the response.
//         * That allows us to see the loading spinner on the screen for a little longer
//         * than it normally would appear for.**/
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        /* Create URL object **/
        URL url = createUrl();

        /* Perform HTTP request to the URL and receive a JSON response back **/
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        /* Extract relevant fields from the JSON response and create a list of Cakes
          Return the list of cakes **/

        return extractFeatureFromJson(jsonResponse);
    }

    private static List<Ingredient> extractIngredientsFromJson(JSONArray ingredientsArray) throws JSONException {
        /*Create an empty ArrayList that we can start adding cakes to**/
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject jsonObject = ingredientsArray.getJSONObject(i);
            Double ingredientQuantity = jsonObject.getDouble("quantity");
            String ingredientMeasure = jsonObject.getString("measure");
            String ingredientName = jsonObject.getString("ingredient");

            Ingredient ingredient = new Ingredient(ingredientQuantity,
                    ingredientMeasure,
                    ingredientName);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private static Step[] extractStepsFromJson(JSONArray stepsArray) throws JSONException {
        Step[] steps = new Step[stepsArray.length()];

        for (int i = 0; i < stepsArray.length(); i++) {
            JSONObject jsonObject = stepsArray.getJSONObject(i);
            int stepId = jsonObject.getInt("id");
            String stepShortDescription = jsonObject.getString("shortDescription");
            String stepDescription = jsonObject.getString("description");
            String stepVideoUrl = jsonObject.getString("videoURL");
            String stepThumbnailUrl = jsonObject.getString("thumbnailURL");

            Step step = new Step(stepId,
                    stepShortDescription,
                    stepDescription,
                    stepVideoUrl,
                    stepThumbnailUrl);

            steps[i] = step;
        }
        return steps;
    }

    /**
     * This method returns the entire result from the HTTP response.
     * AND\ud851-Sunshine-student\S03.02-Solution-RecyclerViewClickHandling
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
