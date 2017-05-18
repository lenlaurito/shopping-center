/**
 * Created by kenichigouang on 5/16/17.
 */

package com.synacy.shoppingcenter.type

enum Location {
    FIRST_FLOOR("1st Floor"),
    SECOND_FLOOR("2nd Floor"),
    THIRD_FLOOR("3rd Floor"),
    FOURTH_FLOOR("4th Floor")

    String name

    Location(String name) {
        this.name = name
    }

    String getKey() {
        name()
    }
}
