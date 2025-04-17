package dev.rivu.golpoai.logging

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity


public object KermitLogger {
    val logger = Logger
        .withTag("GolpoAI")

}

public val Logger = KermitLogger.logger
