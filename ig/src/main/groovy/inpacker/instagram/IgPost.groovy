package inpacker.instagram

import groovy.transform.Immutable

@Immutable class IgPost {

    String username, url, type, id
    long createdTime

    def isVideo() {
        return type == 'video'
    }

    def isImage() {
        return type == 'image'
    }

    def extension() {
        return isVideo() ? ".mp4" : isImage() ? ".jpg" : ""
    }
}
