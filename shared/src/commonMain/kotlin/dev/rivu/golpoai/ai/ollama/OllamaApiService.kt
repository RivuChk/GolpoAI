package dev.rivu.golpoai.ai.ollama

import dev.rivu.golpoai.logging.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class OllamaApiService(private val baseUrl: String) {
    
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        
        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    dev.rivu.golpoai.logging.Logger.d(message)
                }
            }
            level = LogLevel.INFO
        }
        
        install(HttpTimeout) {
            requestTimeoutMillis = 60000 // 60 seconds
            connectTimeoutMillis = 10000 // 10 seconds
        }
    }
    
    suspend fun generateResponse(request: OllamaRequest): Result<OllamaResponse> {
        return try {
            val response = httpClient.post("$baseUrl/api/generate") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            
            if (response.status.isSuccess()) {
                Result.success(response.body<OllamaResponse>())
            } else {
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error generating response", e)
            Result.failure(e)
        }
    }
    
    suspend fun getModels(): Result<OllamaTagsResponse> {
        return try {
            val response = httpClient.get("$baseUrl/api/tags")
            
            if (response.status.isSuccess()) {
                Result.success(response.body<OllamaTagsResponse>())
            } else {
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching models", e)
            Result.failure(e)
        }
    }
    
    fun close() {
        httpClient.close()
    }
}