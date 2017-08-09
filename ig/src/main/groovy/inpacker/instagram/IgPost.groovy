package inpacker.instagram

import groovy.transform.Immutable

@Immutable class IgPost {

    String username, url, type, id
    long createdTime

    boolean isVideo() {
        return type == 'video'
    }

    boolean isImage() {
        return type == 'image'
    }

    String extension() {
        return isVideo() ? ".mp4" : isImage() ? ".jpg" : ""
    }
}
