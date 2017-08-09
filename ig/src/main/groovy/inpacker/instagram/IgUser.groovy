package inpacker.instagram

import com.google.gson.annotations.SerializedName
import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable class IgUser {
    @SerializedName("instagramId")
    String instagramId

    @SerializedName("username")
    String username

    @SerializedName("isPrivate")
    boolean isPrivate

    @SerializedName("fullName")
    String fullName

    @SerializedName("biography")
    String biography

    @SerializedName("profilePic")
    String profilePic

    @SerializedName("count")
    int count

    @SerializedName("isVerified")
    boolean isVerified
}
