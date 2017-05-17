package com.synacy.shoppingCenter.trait

import com.synacy.shoppingCenter.exception.InvalidDataPassed

/**
 * Created by steven on 5/18/17.
 */
trait ExceptionHandlerTrait {

    def handleInvalidDataPassed(InvalidDataPassed e) {
        response.status = HttpStatus.NOT_ACCEPTABLE.value()
        respond([error: e.getMessage()])
    }

}
