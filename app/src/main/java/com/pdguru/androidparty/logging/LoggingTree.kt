package com.pdguru.androidparty.logging

import timber.log.Timber

class LoggingTree(private val applicationName: String) : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        with(element) {
            return "$applicationName:$fileName:$lineNumber)#$methodName"
        }
    }
}
