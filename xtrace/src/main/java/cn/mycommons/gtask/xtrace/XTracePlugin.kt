package cn.mycommons.gtask.xtrace

import cn.mycommons.gtask.xtrace.model.ProjectBuildRecord
import org.gradle.api.Plugin
import org.gradle.api.Project

class XTracePlugin : Plugin<Project> {

    private val all = ProjectBuildRecord()

    override fun apply(target: Project) {
        XTraceManager.init(target)
        XTraceManager.setup()
    }
}