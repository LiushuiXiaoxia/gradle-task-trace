package cn.mycommons.gtask.xtrace.model


/***
 * https://docs.google.com/document/d/1CvAClvFfyA5R-PhYUmn5OOQtYMH4h6I0nSsKchNAySU/preview
 */
class TraceEvent(val name: String) {

    var ph: String = ""
    var cat: String = ""

    var ts: Long = 0
    var tid: String? = null
    var pid: String? = null

    var dur: Long? = null

    var args: MutableMap<String, Any?> = linkedMapOf()
}