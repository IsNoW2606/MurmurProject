package orgl.task;

import orgl.utility.StringUtils;

public enum TaskType {
    REGISTER,
    CONNECT,
    CONFIRM,
    MSG,
    MSGS,
    FOLLOW,
    SEND,
    ACK,
    CR;

    public static TaskType fromRequestString(String request) {
        return valueOf(StringUtils.getFirstWord(request));
    }
}
