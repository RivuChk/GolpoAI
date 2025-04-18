package dev.rivu.genai.logging

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity


public object KermitLogger {
    val logger = Logger
        .withTag("GenAI")

}

public val Logger = KermitLogger.logger
