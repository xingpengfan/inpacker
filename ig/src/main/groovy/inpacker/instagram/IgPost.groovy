package inpacker.instagram

import groovy.transform.Immutable

@Immutable class IgPost {

    String username, url, type, id
    long createdTime

    boolean isVideo() {
        type == 'video'
    }

    boolean isImage() {
        type == 'image'
    }

    String extension() {
        isVideo() ? ".mp4" : isImage() ? ".jpg" : ""
    }
}
