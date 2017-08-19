package inpacker.instagram

import groovy.transform.Immutable

@Immutable class IgUser {
    String instagramId, username, fullName, biography, profilePic
    boolean isPrivate, isVerified
    int count
}
