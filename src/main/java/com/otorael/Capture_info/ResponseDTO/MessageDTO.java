package com.otorael.Capture_info.ResponseDTO;

public class MessageDTO {

    private String notification;
    private String message;

    /**
     *
     * @param notification THROWS quick success notifications
     * @param message Shows description message for the response
     *
     */
    public MessageDTO(String notification, String message) {
        this.notification = notification;
        this.message = message;
    }

    /**
     *
     *
     * @return the notification
     */
    public String getNotification() {
        return notification;
    }


    /**
     *
     *
     * @param notification THROWS quick success notifications
     *
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    /**
     *
     *
     * @return the message
     *
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     *
     * @param message Shows description message for the response
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
