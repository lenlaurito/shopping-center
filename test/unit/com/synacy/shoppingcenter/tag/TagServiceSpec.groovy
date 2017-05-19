package com.synacy.shoppingcenter.tag

//import grails.test.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TagService)
@Mock([Tag])
//@Build([Tag])
class TagServiceSpec extends Specification {

	void "fetchById should return the tag with the given id"() {
		given:
		Tag tag = new Tag(name: "Restaurant")
		tag.save()

		when:
		Tag fetchedTag = service.fetchTagById(tag.id)

		then:
		fetchedTag.id == tag.id
		fetchedTag.name == tag.name
	}
	
	void "fetchAllTags should return all tags"() {
		given:
		Tag tag1 = new Tag(id: 1L, name: "Specialty").save()
		Tag tag2 = new Tag(id: 2L, name: "Restaurant").save()
		Tag tag3 = new Tag(id: 3L, name: "Bakeshop").save()

		when:
		List<Tag> fetchedTags = service.fetchAllTags()

		then:
		fetchedTags.size() == 3
		fetchedTags.get(0) == tag1
		fetchedTags.get(1) == tag2
		fetchedTags.get(2) == tag3
	}

	void "createNewTag should create and return the new tag with the correct details"() {
		given:
		String name = "Restaurant"

		when:
		Tag createdTag = service.createNewTag(name)
		println createdTag.id

		then:
		createdTag.name == name
		Tag.exists(createdTag.id)
	}
	
	void "updateTag should update the existing Tag with corresponding details"() {
		given:
		Tag tagToUpdate = new Tag(name: "Jollibee")
		tagToUpdate.save()
		String changedName = "McDonalds"

		when:
		Tag updatedTag = service.updateTag(tagToUpdate, changedName)

		then:
		updatedTag.name == changedName
	}
	
	void "deleteTag should delete tag"() {
		given:
		String name = "Jollibee"
		String description = "Bida ang saya."
		Tag tagToDelete = new Tag(name: name, description: description)

		when:
		service.deleteTag(tagToDelete)
		println tagToDelete.id

		then:
		!Tag.exists(tagToDelete.id)
	}
	
}
