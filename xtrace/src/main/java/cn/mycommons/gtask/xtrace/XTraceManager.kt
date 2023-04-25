package cn.mycommons.gtask.xtrace

import cn.mycommons.gtask.xtrace.model.ProjectBuildRecord
import cn.mycommons.gtask.xtrace.model.TaskBuildRecord
import cn.mycommons.gtask.xtrace.model.TraceEvent
import cn.mycommons.gtask.xtrace.sth.refObj
import com.google.gson.GsonBuilder
import org.gradle.BuildAdapter
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val TIME_US = 1_000L

object XTraceManager {

    lateinit var rootProject: Project

    lateinit var gradle: Gradle

    var all = ProjectBuildRecord()

    fun init(project: Project) {
        rootProject = project
        gradle = rootProject.gradle
    }

    fun setup() {
        all.tasks.clear()
        gradle.addBuildListener(object : BuildAdapter() {
            override fun buildFinished(result: BuildResult) {
                gradle.removeListener(this)
                gradle.taskGraph.removeTaskExecutionListener(listener)
                report()
            }
        })
        gradle.taskGraph.addTaskExecutionListener(listener)
    }

    private val listener = object : TaskExecutionListener {

        override fun beforeExecute(task: Task) {
            val record = TaskBuildRecord(task.path)
            all.tasks[task.path] = record
        }

        override fun afterExecute(task: Task, state: TaskState) {
            val record = all.tasks[task.path]
            if (record != null) {
                record.project = task.project.name
                record.thread = Thread.currentThread().name
                record.outcome = state.refObj().dField("outcome")?.value()?.toString()
                record.end = Date()
            }
        }
    }

    fun report() {
        all.end = Date()
        if (all.root == null || all.parentRoot == null) {
            all.name = gradle.rootProject.name
            all.root = gradle.rootProject.projectDir.absolutePath
            // all.parentRoot = all.root
            all.parentRoot = gradle.parent?.rootProject?.projectDir?.absolutePath
        }

        val events = all.tasks.flatMap {
            listOf(
                TraceEvent(it.value.name).apply {
                    ph = "X"
                    cat = listOf(all.name).joinToString(",")
                    ts = it.value.begin.time * TIME_US
                    dur = (it.value.end!!.time - it.value.begin.time) * TIME_US
                    // pid = all.name
                    pid = it.value.project
                    tid = it.value.thread
                    args["project"] = it.value.project
                    // args["root"] = all.root
                    args["rootName"] = all.name
                    args["parentRoot"] = all.parentRoot
                    args["thread"] = it.value.thread
                    args["outcome"] = it.value.outcome
                    args["duration"] = (it.value.end!!.time - it.value.begin.time)
                },
            )
        }

        val uuid = SimpleDateFormat("yyyyMMdd-HHmmss-SSS", Locale.getDefault()).format(Date())
        val dir = File(all.parentRoot, "build/reports/xtrace/")
        dir.mkdirs()
        val file = File(dir, "xtrace-${all.name}-$uuid.json")
        // file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(map))
        file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(events))

        rootProject.logger.quiet("xtrace: ${file.absolutePath}")
    }

    private fun genJson() {
        val map = linkedMapOf<String, Any?>(
            "name" to all.name,
            "begin" to all.begin.time,
            "end" to all.end?.time,
            "duration" to ((all.end?.time ?: 0) - all.begin.time)
        )
        map["list"] = all.tasks.map {
            if (it.value.end == null) {
                it.value.end = Date()
            }

            it.value.duration = it.value.end!!.time - it.value.begin.time

            linkedMapOf<String, Any?>(
                "name" to it.value.name,
                "begin" to it.value.begin.time,
                "end" to it.value.end!!.time,
                "duration" to it.value.duration,
                "outcome" to it.value.outcome,
                "thread" to it.value.thread,
            )
        }
    }
}