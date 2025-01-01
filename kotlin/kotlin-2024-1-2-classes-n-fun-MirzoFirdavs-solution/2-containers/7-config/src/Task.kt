import java.io.InputStream
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Config(fileName: String) {
    private val configProps: Map<String, String> = extractContent(fileName)

    companion object {
        private fun extractContent(fileName: String): Map<String, String> {
            val content = getResource(fileName) ?: throw IllegalArgumentException("File not found")
            val propsMap = mutableMapOf<String, String>()

            content.reader().forEachLine {
                val entry = it.split("=")
                if (entry.size < 2 || entry[1].isBlank()) {
                    throw IllegalArgumentException("Invalid configuration value for key: ${entry[0]}")
                }
                propsMap[entry[0].trim()] = entry[1].trim()
            }

            return propsMap
        }
    }

    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, Any> {
        require(configProps.containsKey(property.name)) {
            "File not found"
        }

        return ReadOnlyProperty { _, _ -> configProps.getValue(property.name) }
    }
}

@Suppress(
    "RedundantNullableReturnType",
    "UNUSED_PARAMETER",
)
fun getResource(fileName: String): InputStream? {
    // do not touch this function
    val content =
        """
        |valueKey = 10
        |otherValueKey = stringValue 
        """.trimMargin()

    return content.byteInputStream()
}
