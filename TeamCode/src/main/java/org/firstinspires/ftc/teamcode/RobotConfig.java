package org.firstinspires.ftc.teamcode;


//USE THIS CLASS TO PUT ALL CONSTANTS LIKE HARDWARE NAMES AND CONSTANTS
public class RobotConfig {
    public static class DriveConstants {
        public static final String frontLeftWheelName = "frontLeft";
        public static final String frontRightWheelName = "frontRight";
        public static final String backLeftWheelName = "backLeft";
        public static final String backRightWheelName = "backRight";

        public static final double forwardMultiplier = 0.0029805169566404065;
        public static final double lateralMultiplier = 0.002965149766084306;

        //IN KILOGRAMS!!!
        public static final double mass = 12.05;
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
        public static final double verticalServoUpPosition = .9817;
        public static final double verticalServoDownPosition = .8239;


        public static final String extendMotorName = "extendMotor";
        public static final int motorMaxPosition = 2300;
        public static final int motorMinPosition = 0;
        public static final double motorSlowRetractionRawPower = .2;
        public static final double motorExtendSpeed = 1;
        public static final double motorRetractSpeed = 1;
        public static final double motorPCoefficient = .005;
        //The farthest in the intake can be before lowering it.
        public static final double intakeClearBucketPos = 1700;
        public static final int motorDegreeOfError = 200;

    }

    public static class OuttakeConstants{
        public static String armLiftName = "armLift";
        public static int armLiftHighDunkPos = -4910;
        public static int armLiftDefaultPos = 0;


        public static String armOuttakeName = "armOuttake";
        public static int armSwingHighDunkPos = 432;
        public static int armSwingDefaultPos = 0;
    }

}
