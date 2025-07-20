package com.behnamuix.hogwarts.Harrypoter.audioManager

import android.content.Context
import android.media.MediaPlayer
import com.behnamuix.hogwarts.R

class SongPlayer(ctx: Context) {
    var mediplayer: MediaPlayer? = null
    var url =
        "https://dl.just-music.ir/music//Soundtrack/Harry%20Potter/2001%20-%20Harry%20Potter%20and%20the%20Sorcerers%20Stone%20[John%20Williams]/320/05%20-%20Diagon%20Alley%20-%20The%20Gringotts%20Vault.mp3"

    init {

        mediplayer = MediaPlayer().apply {
            setDataSource(url)
            prepare()
        }
        mediplayer?.isLooping = true


    }

    fun play() {
        if (mediplayer?.isPlaying == true) {
            mediplayer?.pause()

        } else {

            mediplayer?.start()
        }
    }

    fun pause() {
        if (mediplayer?.isPlaying == true) {
            mediplayer?.pause()

        }

    }

    fun isPlaying(): Boolean {
        if (mediplayer?.isPlaying == true) {
            return true

        } else {
            return false
        }
    }

    fun stop() {
        mediplayer?.stop()

    }

    fun destroy() {
        mediplayer?.stop()
        mediplayer?.release()
        mediplayer = null


    }
}