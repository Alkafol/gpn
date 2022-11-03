package com.gpn.vkgroupvalidator.tools;

public class vkMethodException extends Exception{
    private final String vkMessage;

    public vkMethodException(String vkMessage){
        this.vkMessage = vkMessage.substring(1, vkMessage.length() - 1);
    }

    public String getVkMessage() {
        return vkMessage;
    }
}
