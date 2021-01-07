package com.majazeh.emall.pattern

import com.majazeh.emall.data.api.response.Explode


class ExplodeSingleton {

    var explode: Explode? = null

    companion object {

        private var INSTANCE: ExplodeSingleton? = null

        fun getInstance(): ExplodeSingleton? {
            if (INSTANCE == null)
                create()
            return INSTANCE
        }

        @Synchronized
        private fun create() {
            if (INSTANCE == null)
                INSTANCE = ExplodeSingleton()
        }

    }
}