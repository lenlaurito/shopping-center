package com.synacy.shoppingcenter.exception.handler

import com.synacy.shoppingcenter.exception.ResourceConflictException

import org.postgresql.util.PSQLException
import org.springframework.http.HttpStatus

trait DatabaseExceptionHandler {
	
	def handleResourceConflictException(ResourceConflictException e) {
		response.status = HttpStatus.CONFLICT.value()
		respond([error: e.getMessage()])
	}
	
}
