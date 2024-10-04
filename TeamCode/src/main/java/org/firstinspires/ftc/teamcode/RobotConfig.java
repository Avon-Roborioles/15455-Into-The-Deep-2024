package org.firstinspires.ftc.teamcode;


//USE THIS CLASS TO PUT ALL CONSTANTS LIKE HARDWARE NAMES AND CONSTANTS
public class RobotConfig {
    public static class DriveConstants {
        public static final String frontLeftWheelName = "frontLeft";
        public static final String frontRightWheelName = "frontRight";
        public static final String backLeftWheelName = "backLeft";
        public static final String backRightWheelName = "backRight";
    }

    public static class IntakeConstants {
        public static final double colorSensorRedToGreenThreshold =.3;
        public static final String colorSensor1Name = "leftColorSensor";
        public static final String colorSensor2Name = "rightColorSensor";

        public static final String intakeServoName = "intakeCRServo";
        public static final double intakeServoUpDirection = 1;

        public static final String verticalServoName = "verticalServo";
        public static final double verticalServoMinDegrees = 0;
        public static final double verticalServoMaxDegrees = 1800;
        public static final double verticalServoUpPosition = .7;
        public static final double verticalServoDownPosition = .4;


        public static final String extendMotorName = "extendMotor";
    }


}
