package controllers

import k.aop.annotations.Comment
import k.aop.annotations.JsonApi
import k.common.apidoc.BuiltModuleInfo
import k.controllers.JsonpController
import k.reply.StringListReply
import play.mvc.Result
import javax.inject.Inject

/**
 * Created by kk on 16/8/25.
 */

@Comment("加载的 Module 和 DI Binding 信息")
class Samples
@Inject
constructor(
        var environment: play.api.Environment,
        var configuration: play.api.Configuration) : JsonpController() {

    @Comment("列出 Play 的内置 Module")
    @JsonApi(ReplyClass = StringListReply::class)
    fun BuiltInPlayModules(): Result {
        val reply = StringListReply()
        reply.lines = BuiltModuleInfo.BuiltInModuleNames(this.environment, this.configuration)
        return ok(reply)
    }

    @Comment("列出 Play 的 DI Binding 信息")
    @JsonApi(ReplyClass = StringListReply::class)
    fun BindingInfo(): Result {
        var reply = StringListReply()
        reply.lines = BuiltModuleInfo.BuiltInBindings(this.environment, this.configuration)
        return ok(reply)
    }

}