package inpacker.instagram

import groovy.transform.Immutable

@Immutable class IgUser {
    String instagramId
    String username
    boolean isPrivate
    String fullName
    String biography
    String profilePic
    int count
    boolean isVerified
}
