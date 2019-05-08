package com.itangcent.idea.plugin.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.itangcent.idea.plugin.api.call.ApiCaller
import com.itangcent.idea.plugin.api.export.DocParseHelper
import com.itangcent.idea.plugin.api.export.EasyApiConfigReader
import com.itangcent.idea.plugin.api.export.postman.PostmanConfigReader
import com.itangcent.idea.psi.RecommendClassRuleConfig
import com.itangcent.intellij.config.ConfigReader
import com.itangcent.intellij.context.ActionContext
import com.itangcent.intellij.extend.guice.singleton
import com.itangcent.intellij.extend.guice.with
import com.itangcent.intellij.file.DefaultLocalFileRepository
import com.itangcent.intellij.file.LocalFileRepository
import com.itangcent.intellij.psi.ClassRuleConfig
import com.itangcent.intellij.psi.DefaultClassRuleConfig
import com.itangcent.intellij.setting.ReadOnlySettingManager
import com.itangcent.intellij.setting.SettingManager

class ApiCallAction : ApiExportAction("Call Api") {

    override fun onBuildActionContext(builder: ActionContext.ActionContextBuilder) {
        super.onBuildActionContext(builder)

        builder.bind(LocalFileRepository::class) { it.with(DefaultLocalFileRepository::class).singleton() }
        builder.bind(SettingManager::class) { it.with(ReadOnlySettingManager::class).singleton() }
        builder.bind(DocParseHelper::class) { it.singleton() }
        builder.bind(ClassRuleConfig::class) { it.with(RecommendClassRuleConfig::class).singleton() }
        builder.bind(ConfigReader::class) { it.with(EasyApiConfigReader::class).singleton() }
        builder.bind(ApiCaller::class) { it.singleton() }

    }

    override fun actionPerformed(actionContext: ActionContext, project: Project?, anActionEvent: AnActionEvent) {
        val apiCaller = actionContext.instance(ApiCaller::class)
        apiCaller.showCallWindow()
    }
}
