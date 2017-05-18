package com.synacy.shoppingcenter.tag

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
