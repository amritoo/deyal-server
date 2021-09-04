package app.deyal.deyal_server.model;

public interface Message {

    String emailSignature = "\nWith best regards,\nDeyal Team.";

    //For Emails
    String registerEmail = "Hello,\n" +
            "Your email was provided for registration on Deyal app and you were successfully registered.\n" +
            "Thank you for your interest in the Deyal app.\n" +
            "\n" +
            "If it wasn't you, please contact us at deyal.app@gmail.com.\n" + emailSignature;

    String otpEmail = "Hello,\n" +
            "Here is your one time code (OTP). Please verify it in the Deyal app within 10 minutes.\n" +
            "Code: ";

    String passwordChangeEmail = "Hello,\n" +
            "Your password has been changed.\n" +
            "\n" +
            "If it wasn't you, then please contact us at deyal.app@gmail.com.\n" + emailSignature;

    String emailChangeEmailOld = ". You can no longer use this email to login.\n" +
            "\n" +
            "If it wasn't you, then please contact us at deyal.app@gmail.com.\n" + emailSignature;

    String emailChangeEmailNew = "Hello,\n" +
            "Your account email has been changed. Please use this email to login to your account.\n" +
            "\n" +
            "If it wasn't you, then please contact us at deyal.app@gmail.com.\n" + emailSignature;

    //For Notifications
    String registerNotification = "Your Account has been created. For any help, please goto help option.";

    String profileUpdateNotification = "Your profile has been updated.";

    String passwordChangeNotification = "Account password changed.";

    String missionCreatedNotification = "You have created a new Mission.";

    String missionUpdatedNotification = "Your mission was updated (by Admin).";

    String missionRequestedNotification = "Someone requested one of your created missions.";

    String missionAssignedNotification = "You have been assigned to complete one of your pending requests.";

    String missionSubmittedNotification = "One of your created missions have a new submission. Please judge it.";

    String missionApprovedNotification = "Hurray! your submission was approved. Please collect your rewards from the client.";

    String missionRejectedNotification = "Sadly, your submission was rejected. Please contact the client if you want to know why.";

    String missionCompletedNotificationClient = "Hurray! One of your created missions was successfully completed.";

    String missionCompletedNotificationContractor = "Hurray! One of your missions was successfully completed.";

}
