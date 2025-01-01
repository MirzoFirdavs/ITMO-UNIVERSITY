fun sendMessageToClient(
    client: Client?,
    message: String?,
    mailer: Mailer,
) {
    val email: String? = client?.personalInfo?.email
    val text: String = message ?: "Hello!"

    email?.let {
        mailer.sendMessage(email, text)
    }
}
