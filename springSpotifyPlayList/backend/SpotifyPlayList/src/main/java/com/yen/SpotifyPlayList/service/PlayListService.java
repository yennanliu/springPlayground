package com.yen.SpotifyPlayList.service;

import com.yen.SpotifyPlayList.model.dto.AddSongToPlayListDto;
import com.yen.SpotifyPlayList.model.dto.CreatePlayListDto;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import java.io.IOException;

@Service
@Slf4j
public class PlayListService {

    @Autowired
    private AuthService authService;

    @Autowired
    private ProfileService profileService;

    private SpotifyApi spotifyApi;


    public PlayListService(){

    }

    public Playlist getPlaylistById(String playlistId) throws SpotifyWebApiException {

        Playlist playlist = null;
        try {
            // TODO : move below to controller / config
            this.spotifyApi = authService.getSpotifyApi();
            final GetPlaylistRequest getPlaylistRequest = spotifyApi
                    .getPlaylist(playlistId)
                    .build();
            playlist = getPlaylistRequest.execute();
            log.info("playlist = " + playlist);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyWebApiException("getPlaylistById error: " + e.getMessage());
        }
        return playlist;
    }

    public Playlist createPlayList(CreatePlayListDto createPlayListDto) throws SpotifyWebApiException {

        Playlist playlist = null;

        try {
            // TODO : move below to controller / config
            this.spotifyApi  = authService.authClientWithAuthCode(
                    authService.getSpotifyApi(),
                    createPlayListDto.getAuthCode()
            );

            /** NOTE !!! use the same client (spotifyApi)
             *  for getting current user id and create playlist
             */
            // get current user profile
            String userId = profileService.getCurrentUserId(this.spotifyApi); //"62kytpy7jswykfjtnjn9zv3ou";

            /**
             * NOTE !!! via refresh token,
             *   we CAN REUSE same Auth code make multiple requests
             *   that need "user redirect auth"
             *
             *   Flow:
             *    step 1) auth with redirect (with defined scope)
             *    step 2) make a first request (e.g. get current user ID)
             *    step 3) update client with refresh token
             *    step 4) make the other request (e.g. create playlist)
             *
             *    Code ref :
             *     - https://github.com/spotify-web-api-java/spotify-web-api-java/blob/cfd0dae1262bd7f95f90c37b28d27b9c944d471a/examples/authorization/authorization_code/AuthorizationCodeRefreshExample.java#L22
             */
            String refreshToken = this.spotifyApi.getRefreshToken();
            spotifyApi.setRefreshToken(refreshToken);

            final CreatePlaylistRequest createPlaylistRequest = spotifyApi
                    .createPlaylist(userId, createPlayListDto.getName())
                    .build();

            // create new playList
            playlist = createPlaylistRequest.execute();
            log.info("playlist is created !  " + playlist + " name = " + playlist.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyWebApiException("createPlayList error: " + e.getMessage());
        }
        return playlist;
    }

    public SnapshotResult addSongToPlayList(AddSongToPlayListDto addSongToPlayListDto){

        SnapshotResult snapshotResult = null;

        //final String accessToken = "BQBZi1FrY15l2dgIzlFw1EiLEIka9wHwG0vWFHCrULeeOZujlk982wwW0-DOxyu9x7BBsgKH6Vtaklut095LxOW3DanaY-CvCwEGXw94V1ayHJum-tU";
        // playList ID can be received from create playList resp
        final String playlistId = "7KP94mNZB4goH7bwjplrsH";
        final String[] uris = new String[]{"spotify:track:0Sxq05leQaZXCktX05Kr7b"};

        addSongToPlayListDto.setPlaylistId(playlistId);
        addSongToPlayListDto.setSongUris(uris);
        log.info("(addSongToPlayList) addSongToPlayListDto = " + addSongToPlayListDto);

        try {
            this.spotifyApi  = authService.authClientWithAuthCode(
                    authService.getSpotifyApi(),
                    addSongToPlayListDto.getAuthCode()
            );
            final AddItemsToPlaylistRequest addItemsToPlaylistRequest = this.spotifyApi
                    .addItemsToPlaylist(addSongToPlayListDto.getPlaylistId(), addSongToPlayListDto.getSongUris())
                    //.position(0)
                    .build();
            snapshotResult = addItemsToPlaylistRequest.execute();
            log.info("Snapshot ID: " + snapshotResult.getSnapshotId());
            log.info("addSongToPlayList OK");
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("addSongToPlayList Error: " + e.getMessage());
        }
        return snapshotResult;
    }

}
