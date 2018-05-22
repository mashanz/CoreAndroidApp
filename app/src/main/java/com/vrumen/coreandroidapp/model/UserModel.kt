package com.vrumen.coreandroidapp.model

import java.io.Serializable

/**
 * Created by bilinedev on 22/05/18.
 * There are many bug here (may be)
 * just google it <(^o^<)
 */
data class UserModel(
        var id: String? = "",
        var name: String? = "",
        var role: String? = ""
) : Serializable
