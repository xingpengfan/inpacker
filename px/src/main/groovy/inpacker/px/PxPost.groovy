package inpacker.px

import groovy.transform.Immutable

@Immutable class PxPost {
    String id, name, description, createdAt
    int width, height
    String url, imageFormat

    String extension() {
        ".jpg"
    }
}
