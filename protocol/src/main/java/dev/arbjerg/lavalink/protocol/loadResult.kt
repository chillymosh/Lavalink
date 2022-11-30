package dev.arbjerg.lavalink.protocol

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException

data class LoadResult(
    val loadType: ResultStatus,
    val tracks: List<Track>,
    val playlistInfo: PlaylistInfo?,
    val playlist: Playlist?,
    val exception: Exception?
) {
    companion object {
        fun trackLoaded(track: Track) = LoadResult(ResultStatus.TRACK_LOADED, listOf(track), null, null, null)
        fun playlistLoaded(playlist: Playlist, tracks: List<Track>) = LoadResult(
            ResultStatus.PLAYLIST_LOADED,
            tracks,
            PlaylistInfo(playlist.info.name, playlist.info.selectedTrack),
            playlist,
            null
        )
        fun searchResultLoaded(tracks: List<Track>) = LoadResult(ResultStatus.SEARCH_RESULT, tracks, null, null, null)
        val noMatches = LoadResult(ResultStatus.NO_MATCHES, emptyList(), null, null, null)
        fun loadFailed(exception: FriendlyException) =
            LoadResult(ResultStatus.LOAD_FAILED, emptyList(), null, null, Exception.fromFriendlyException(exception))

    }
}

data class Exception(
    val message: String?,
    val severity: FriendlyException.Severity,
    val cause: String
) {
    companion object {
        fun fromFriendlyException(e: FriendlyException) = Exception(
            e.message,
            e.severity,
            e.toString()
        )
    }
}

enum class ResultStatus {
    TRACK_LOADED,
    PLAYLIST_LOADED,
    SEARCH_RESULT,
    NO_MATCHES,
    LOAD_FAILED
}