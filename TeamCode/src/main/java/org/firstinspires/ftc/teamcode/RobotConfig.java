package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

//USE THIS CLASS TO PUT ALL CONSTANTS LIKE HARDWARE NAMES AND CONSTANTS
public class RobotConfig {
    public static class DriveConstants {
        public static final String frontLeftWheelName = "frontLeft";
        public static final String frontRightWheelName = "frontRight";
        public static final String backLeftWheelName = "backLeft";
        public static final String backRightWheelName = "backRight";

        public static final double forwardMultiplier = 0.0029805169566404065;
        public static final double lateralMultiplier = 0.002965149766084306;

        public static final IMU.Parameters practiceIMUOrientation = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );

        public static final IMU.Parameters compIMUOrientation = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        //IN KILOGRAMS!!!
        public static final double practiceBotMass = 14.318;
        public static final double compBotMass = 12.05;
    }

    public static class IntakeConstants {
        public static final double colorSensorRedToGreenThreshold =.35;
        public static final String colorSensor1Name = "leftColorSensor";
        public static final String colorSensor2Name = "rightColorSensor";

        public static final String intakeServoName = "intakeCRServo";
        public static final double intakeServoUpDirection = 1;

        public static final String verticalServoName = "verticalServo";
        public static final double verticalServoMinDegrees = 0;
        public static final double verticalServoMaxDegrees = 300;
        public static final double verticalServoUpPosition = .3322;
        public static final double verticalServoDownPosition = 0;


        public static final String extendMotorName = "extendMotor";
        public static final int motorMaxPosition = 540;
        public static final int motorMinPosition = 80;
        public static final double motorSlowRetractionRawPower = .2;
        public static final double motorExtendSpeed = 1;
        public static final double motorRetractSpeed = -1;
        public static final double motorPCoefficient = .005;
        //The farthest in the intake can be before lowering it.
        public static final double intakeClearBucketPos = -1800;
        public static final int motorDegreeOfError = 25;
        public static final int motorClearPos = 322;

    }

    public static class OuttakeConstants{
        public static final String armLiftName = "armLift";
        public static final int armLiftHighDunkPos = -2000;
        public static final int armLiftDefaultPos = 0;


        public static final String armOuttakeName = "armOuttake";
        public static int armSwingHighDunkPos = 400;
        public static int armSwingLowDunkPos = 503;
        public static int armSwingDefaultPos = 50;
    }

    public static class CameraConstants{
        public static final float fx = 790.088f;
        public static final float fy = 790.088f;
        public static final float cx = 320.002f;
        public static final float cy = 240.001f;
    }

    public static class GlobalConstants {
        public static Pose lastPose = new Pose(0,0,0);
    }

}
