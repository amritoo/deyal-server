package app.deyal.deyal_server.model;

public class Message {

    //For Emails
    public static String registerEmail = "Hello,\n" +
            "Your email was provided for registration on Deyal app and you were successfully registered.\n" +
            "Thank you for your interest in Deyal app.\n" +
            "If it was not you, please contact us at deyal.app@gmail.com.\n" +
            "\n" +
            "With best regards,\n" +
            "Deyal Team.";

    public static String otpEmail = "Hello,\n" +
            "Here is your one time code. Please use it to verify in Deyal app within 15 minutes.\n" +
            "Code: ";

    public static String passwordChangeEmail = "Hello,\n" +
            "Your password has been changed.\n" +
            "If it wasn't you, then please contact us at deyal.app@gmail.com.\n" +
            "\n" +
            "With best regards,\n" +
            "Deyal Team.";

    //For Notifications
    public static String registerNotification = "Your Account has been created. For any help, please goto help option.";

    public static String profileUpdateNotification = "Your profile has been updated.";

    public static String passwordChangeNotification = "Password has been changed.";

    public static String missionCreatedNotification = "You have created a new Mission.";

    public static String missionUpdatedNotification = "Your mission was Updated (by Admin).";

    public static String missionRequestedNotification = "Someone requested one of your created mission.";

    public static String missionAssignedNotification = "You have been assigned to complete one of your pending requests.";

    public static String missionSubmittedNotification = "One of your created missions have a new submission. Please judge it.";

    public static String missionApprovedNotification = "Your submission has been approved. Please collect your rewards from the client.";

    public static String missionRejectedNotification = "Your submission has been rejected. Please contact the client if you want to know why.";

    public static String missionCompletedNotification = "One of your created mission was successfully completed.";

}
