package inpacker.px

import groovy.transform.Immutable

@Immutable class PxUser {
    String id, username, firstname, lastname, city, country, userpicUrl, coverUrl
    int photosCount

}
