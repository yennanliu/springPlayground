package com.yen.SpotifyPlayList.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;

import java.io.IOException;

@Service
@Slf4j
public class AlbumService {

    @Autowired
    private AuthService authService;

    private String accessToken;
    //private String accessToken = "BQDIiCYIHV5g1LI98m4HwuMRZJleQZxVvJ8yzvtk8qMcwjHMGQOXrd7Lq-oMwHwQcJ3s6QOfNlOiZ8OerLUSjr69qVNkSnXE7egzmpbN-YNbrhxym6o";

    private SpotifyApi spotifyApi;

    public AlbumService(){
    }

//    public AlbumService(){
//        //this.accessToken = accessToken;
//        this.accessToken = this.authService.getToken();
//        this.spotifyApi = new SpotifyApi.Builder()
//                .setAccessToken(this.accessToken)
//                .build();
//    }

    public Album getAlbum(String albumId) throws SpotifyWebApiException {

        Album album = null;
        try {
            // TODO : move below to controller / config
            this.spotifyApi = this.getSpotifyApi();
            final GetAlbumRequest getAlbumRequest = this.spotifyApi
                    .getAlbum(albumId)
                    .build();
            album = getAlbumRequest.execute();
            log.info("album = " + album);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyWebApiException("getAlbum error: " + e.getMessage());
        }
        return album;
    }

    public Paging<TrackSimplified> getAlbumTrack(String albumId) throws SpotifyWebApiException {

        Paging<TrackSimplified> trackSimplifiedPaging = null;

        try {
            // TODO : move below to controller / config
            this.spotifyApi = this.getSpotifyApi();
            final GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi
                    .getAlbumsTracks(albumId)
                    .build();
            trackSimplifiedPaging = getAlbumsTracksRequest.execute();
            log.info("Track count: " + trackSimplifiedPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyWebApiException("getAlbumTrack error: " + e.getMessage());
        }
        return trackSimplifiedPaging;
    }

    public SpotifyApi getSpotifyApi() {

        log.info(">>> (getSpotifyApi) accessToken = " + this.accessToken);
        // lazy approach
        if (this.accessToken == null) {
            this.accessToken = this.authService.getToken();
            log.info(">>> (getSpotifyApi) new accessToken = " + accessToken);
            this.spotifyApi = new SpotifyApi.Builder()
                    .setAccessToken(this.accessToken)
                    .build();
        }
        return this.spotifyApi;
    }

}
