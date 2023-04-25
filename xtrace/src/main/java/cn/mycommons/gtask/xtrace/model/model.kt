package cn.mycommons.gtask.xtrace.model

import java.util.Date
import java.util.concurrent.ConcurrentHashMap


class ProjectBuildRecord {

    var name: String = ""
    var root: String? = null
    var parentRoot: String? = null
    var begin = Date()
    var end: Date? = null

    var tasks = ConcurrentHashMap<String, TaskBuildRecord>()
}

class TaskBuildRecord(val name: String) {
    var project: String = ""
    var begin = Date()
    var end: Date? = null
    var duration: Long = 0
    var outcome: String? = ""
    var thread: String = ""
}