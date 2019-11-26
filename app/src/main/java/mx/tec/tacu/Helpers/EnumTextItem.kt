package mx.tec.tacu.Helpers

class EnumTextItem<T>(val id: T, val text: String) {
    override fun toString(): String {
        return text
    }
}