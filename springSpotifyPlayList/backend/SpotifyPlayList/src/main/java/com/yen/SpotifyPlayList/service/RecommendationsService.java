package com.yen.SpotifyPlayList.service;

import com.yen.SpotifyPlayList.model.dto.GetRecommendationsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

import java.io.IOException;

@Service
@Slf4j
public class RecommendationsService {

    @Autowired
    private AuthService authService;

    private SpotifyApi spotifyApi;

    public RecommendationsService() {

    }

    public Recommendations getRecommendation(GetRecommendationsDto getRecommendationsDto) throws SpotifyWebApiException {

        Recommendations recommendations = null;
        try {
            // TODO : move below to controller / config
            this.spotifyApi = authService.getSpotifyClient();

            // TODO : enable param and update request logic
//            final GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
//                      .limit(10)
//            //          .market(CountryCode.SE)
//                      .max_popularity(50)
//                      .min_popularity(10)
//            //          .seed_artists("0LcJLqbBmaGUft1e9Mm8HV")
//            //          .seed_genres("electro")
//                      .seed_tracks("01iyCAUm8EvOFqVWYJ3dVX")
//            //          .target_popularity(20)
//            .build();

            final GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
                    .limit(getRecommendationsDto.getAmount())
                    .market(getRecommendationsDto.getMarket())
                    .max_popularity(getRecommendationsDto.getMaxPopularity())
                    .min_popularity(getRecommendationsDto.getMinPopularity())
                    .seed_artists(getRecommendationsDto.getSeedArtistId())
                    .seed_genres(getRecommendationsDto.getSeedGenres())
                    .seed_tracks(getRecommendationsDto.getSeedTrack())
                    .target_popularity(getRecommendationsDto.getTargetPopularity())
                    .build();
            recommendations = getRecommendationsRequest.execute();
            log.info("recommendations = " + recommendations);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyWebApiException("getRecommendation error: " + e.getMessage());
        }
        return recommendations;
    }

}
