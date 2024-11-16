import com.sap.gateway.ip.core.customdev.util.Message

Message setStartTime(Message message) {
    def startTime= System.currentTimeMillis()
    message.setProperty("startTime", startTime);
    return message
}

Message setExecutionTimeAsBody(Message message) {
    def startTime = message.getProperty("startTime") as Long
    message.setBody(Long.toString(System.currentTimeMillis()-startTime))
    return message
}