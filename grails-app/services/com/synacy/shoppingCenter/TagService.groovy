package com.synacy.shoppingCenter

/**
 * Created by steven on 5/21/17.
 */
interface TagService {

    Tag fetchTagById(Long tagId)

    List<Tag> fetchAllTag()

    Tag createTag(String tagName)

    Tag updateTag(Long tagId, String tagName)

    void deleteTag(Long tagId)
}
