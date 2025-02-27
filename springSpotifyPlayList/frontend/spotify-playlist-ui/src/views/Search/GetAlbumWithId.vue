<template>
  <div class="container">
    <h1>Search Album With ID</h1>
    <!-- Album ID Form -->
    <form class="album-form text-center">
      <div class="form-group">
        <p style="text-align: center;">Enter Album ID, example: 1VuIx4XMmSs1hGZk2uCzvO</p><br>
        <label for="albumId" class="form-label">Enter Album ID</label>
        <input
          type="text"
          class="form-control form-control-lg"
          v-model="albumId"
          placeholder="Album ID"
          required
        />
      </div>
      <button type="button" class="btn btn-outline-light btn-lg" @click="fetchAlbum">
        Submit
      </button>
    </form>

    <!-- Error message -->
    <div v-if="fetchAlbumError" class="error-message text-center" style="color: red;">
      {{ fetchAlbumError }}
    </div>

    <!-- Album Details -->
    <div v-if="album && !fetchAlbumError" class="album-details mt-5">
      <h1 class="album-title">
        Album: {{ album.name }} | Artist: {{ album.artists[0].name }}
      </h1>

      <!-- Album Image -->
      <div class="text-center">
        <img
          v-if="album.images && album.images.length > 0"
          :src="album.images[0].url"
          class="album-cover"
          :alt="album.name"
        />
      </div>

      <!-- Album URL -->
      <p class="album-url text-center">
        Album URL:
        <a :href="album.externalUrls.externalUrls.spotify" target="_blank">
          View on Spotify
        </a>
      </p>

      <!-- Tracks List -->
      <div class="track-list mt-4">
        <h3 class="text-center">Tracks</h3>
        <div v-for="track in album.tracks.items" :key="track.id" class="track-card">
          <p class="track-name">Track: {{ track.name }}</p>

          <!-- Track URL -->
          <p class="track-url">
            URL:
            <a :href="track.externalUrls.externalUrls.spotify" target="_blank">
              Listen on Spotify
            </a>
          </p>

          <!-- Track Preview -->
          <p class="track-preview">
            Preview:
            <audio controls @play="playAudio($event)">
              <source :src="track.previewUrl" type="audio/mpeg" />
              Your browser does not support the audio element.
            </audio>
          </p>

          <hr class="track-divider" />
        </div>
      </div>
    </div>

    <!-- Loading Placeholder -->
    <div v-else-if="!fetchAlbumError" class="loading-text text-center mt-5">Loading...</div>
  </div>
</template>

<script>
export default {
  props: ["baseURL"],
  data() {
    return {
      albumId: "1VuIx4XMmSs1hGZk2uCzvO", // default value
      album: null,
      fetchAlbumError: null, // Error message state
      currentPlayingAudio: null // Track currently playing audio
    };
  },
  methods: {
    async fetchAlbum() {
      try {
        this.fetchAlbumError = null; // Reset error state
        const response = await fetch(
          `${this.baseURL}/album/${this.albumId}`
        );
        if (!response.ok) {
          throw new Error("Failed to fetch album");
        }
        const data = await response.json();
        this.album = data;
        console.log("this.album =", JSON.stringify(this.album));
      } catch (error) {
        console.error(error);
        this.fetchAlbumError = error.message; // Set error message
        this.album = null; // Clear album data on error
      }
    },
    playAudio(event) {
      if (this.currentPlayingAudio && this.currentPlayingAudio !== event.target) {
        this.currentPlayingAudio.pause();
        this.currentPlayingAudio.currentTime = 0; // Reset the previous audio
      }
      this.currentPlayingAudio = event.target; // Set the new playing audio
    },
  },
};
</script>

<style scoped>
/* Main container and form styling */
.container {
  max-width: 900px;
  margin: 0 auto;
}

.album-form {
  margin-top: 30px;
}

.form-label {
  color: #fff;
  font-size: 1.5rem;
}

.form-control-lg {
  font-size: 1.2rem;
  border-radius: 10px;
  padding: 10px;
}

.album-title {
  font-family: "Roboto", sans-serif;
  color: #1db954;
  font-weight: 700;
  font-size: 2.5rem;
  text-align: center;
  margin-top: 30px;
}

/* Error message styling */
.error-message {
  font-size: 1.2rem;
  color: red;
  margin-top: 10px;
}

/* Album image styling */
.album-cover {
  max-width: 400px;
  max-height: 400px;
  object-fit: cover;
  border-radius: 15px;
  border: 4px solid #1db954;
  margin-top: 20px;
}

/* URL and button styling */
.album-url a {
  font-size: 1.2rem;
  color: #1db954;
  text-decoration: none;
}

.btn-outline-light {
  border-color: #1db954;
  color: #1db954;
  font-size: 1.2rem;
  font-weight: bold;
}

.btn-outline-light:hover {
  background-color: #1db954;
  color: #191414;
}

/* Tracks list styling */
.track-list {
  margin-top: 30px;
}

.track-card {
  background-color: #191414;
  border-radius: 15px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
}

.track-name {
  font-size: 1.5rem;
  font-weight: bold;
  color: #fff;
}

.track-url a {
  font-size: 1rem;
  color: #1db954;
  text-decoration: none;
}

.track-preview {
  margin-top: 10px;
}

.track-divider {
  border-color: #1db954;
}

/* Loading text styling */
.loading-text {
  font-size: 1.5rem;
  color: #686868;
}
</style>