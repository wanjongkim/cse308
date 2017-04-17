/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Shawn
 */
@Stateless
public class DataTimerSessionBean {

    private final String key = "318fa165649de5b30b74568e44512dce";
    //@Schedule(dayOfWeek = "*")
    @Schedule(minute = "5")
    public void timer() {
        retrieveDataFromServer();
    }
    
    private void retrieveDataFromServer() {
        JSONParser parser = new JSONParser();
        try {
            String urlForMoviesNowPlaying = "https://api.themoviedb.org/3/movie/now_playing?api_key="+key+"&language=en-US&page=1";
            URL u = new URL(urlForMoviesNowPlaying);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String movieInfo = br.readLine();
            JSONObject entireJSON = (JSONObject) parser.parse(movieInfo);
            JSONArray results = (JSONArray) entireJSON.get("results");
            con.disconnect();
            br.close();
            for(int i=0; i<results.size(); i++) {
                JSONObject singleMovie = (JSONObject) results.get(i);
                String movieID = singleMovie.get("id").toString();
                String urlForMovieDetails = "https://api.themoviedb.org/3/movie/"+movieID+"?api_key="+key+"&append_to_response=videos,images";
                URL u2 = new URL(urlForMovieDetails);
                HttpURLConnection con2 = (HttpURLConnection) u2.openConnection();
                con2.setRequestMethod("GET");
                BufferedReader br2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
                String movieInfo2 = br2.readLine();
                JSONObject entireJSON2 = (JSONObject) parser.parse(movieInfo2);
                String homepage = entireJSON2.get("homepage").toString();
                String imdbID = entireJSON2.get("imdb_id").toString();
                String title = entireJSON2.get("title").toString();
                String releaseDate = entireJSON2.get("release_date").toString();
                String posterPath = entireJSON2.get("poster_path").toString();
                String runtime = entireJSON2.get("runtime").toString();
                String status = entireJSON2.get("status").toString();
                String overview = entireJSON2.get("overview").toString();
                JSONArray genres = (JSONArray) entireJSON2.get("genres");
                List<String> l = new ArrayList();
                for(int j=0; j<genres.size(); j++) {
                    JSONObject o = (JSONObject) genres.get(j);
                    l.add(o.get("name").toString());
                    System.out.println(l.get(j));
                }
                JSONObject videos = (JSONObject) entireJSON2.get("videos");
                JSONArray videoResults = (JSONArray) videos.get("results");
                List<String> l2 = new ArrayList();
                for(int j=0; j<videoResults.size(); j++) {
                    JSONObject o = (JSONObject) videoResults.get(j);
                    String videoKey = o.get("key").toString();
                    l2.add(videoKey);
                }
                JSONObject images = (JSONObject) entireJSON2.get("images");
                JSONArray imageResults = (JSONArray) images.get("backdrops");
                List<String> l3 = new ArrayList();
                for(int j=0; j<imageResults.size(); j++) {
                    JSONObject o = (JSONObject) imageResults.get(j);
                    String imageKey = o.get("file_path").toString();
                    l3.add(imageKey);
                }
                con2.disconnect();
                br2.close();
            }
        } catch(Exception ex) {
            System.out.println("Error!");
        }
    }
}
