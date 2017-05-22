package com.synacy.shoppingcenter

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TagService)
@Mock([Tag])
class TagServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    // void "fetchAllTags should return the correct number of tags"() {
    //      given:
            //    Tag tag1 = new Tag(name: "Cosmetics")
            //    Tag tag2 = new Tag(name: "Clothing")
            //    tag1.save()
            //    tag2.save()
    //
    //      when:
    //            List<Tag> tags = service.fetchAllTags()
    //
    //      then:
    //            tags.size() == 2
    // }

    // void "fetchAllTags should return the tags with correct information"() {
    //      given:
    //            Tag tag1 = new Tag(name: "Cosmetics")
    //            Tag tag2 = new Tag(name: "Clothing")
    //            tag1.save()
    //            tag2.save()
    //
    //      when:
    //            List<Tag> tags = service.fetchAllTags()
    //
    //      then:
    //            tags.get(0) == tag1
    //            tags.get(1) == tag2
    // }

    // void "fetchTagById should return the correct tag"() {
    //      given:
    //            Tag tag = new Tag(name: "Cosmetics")
    //            tag.save()
    //
    //       when:
    //            Tag fetchedTag = service.fetchTagById(tag.id)
    //
    //       then:
    //            fetchedTag.id == tag.id
    //            fetchedTag.name == tag.name
    // }

    // void "createTag should save tag with correct info"() {
    //      given:
    //            String name = "Cosmetics"
    //
    //       when:
    //            Tag createdTag = service.createTag(name)
    //
    //       then:
    //            Tag.exists(createdTag.id)
    //            createdTag.name == name
    // }

    // void "updateTag should save new tag details in existing tag"() {
    //      given:
    //            String name = "Hardware"
    //            Tag tag = new Tag(name: "Cosmetics")
    //            tag.save()
    //
    //       when:
    //            Tag updatedTag = service.updateTag(tag, name)
    //
    //       then:
    //            Tag.exists(tag.id)
    //            updatedTag.name == name
    // }

    // void "deleteTag should remove tag"() {
    //      given:
    //            Tag tag = new Tag(name: "Cosmetics")
    //            tag.save()
    //
    //       when:
    //            service.deleteTag(tag)
    //
    //       then:
    //            !Tag.exists(tag.id)
    // }

    // FAIL

    void "fetchTagsFromIds should return all correct tag objects by id"() {
         given:
               Tag tag1 = new Tag(name: "Cosmetics")
               Tag tag2 = new Tag(name: "Clothing")
               tag1.save()
               tag2.save()

               List<Long> tagIds = [tag1.id, tag2.id]

          when:
               Tag<List> tags = service.fetchTagsFromIds(tagIds)

          then:
               tags.get(0) == tag1
               tags.get(1) == tag2
    }
}
