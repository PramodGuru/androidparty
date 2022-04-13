package com.pdguru.androidparty.networking

import com.squareup.moshi.Moshi
import dev.zacsweers.moshix.reflect.MetadataKotlinJsonAdapterFactory

class MoshiFactory {
    companion object {
        fun createMoshi(): Moshi {
            return Moshi.Builder()
                .add(MetadataKotlinJsonAdapterFactory())
                .build()
        }
    }
}
