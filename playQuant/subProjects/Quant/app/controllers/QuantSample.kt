package controllers

import k.aop.annotations.JsonApi
import k.controllers.JsonpController
import k.reply.ReplyBase
import play.mvc.Result

/**
 * Created by kk on 16/8/23.
 */

class QuantSample : JsonpController() {


    @JsonApi
    fun QuantTest() : Result {

        return ok(ReplyBase())
    }

}