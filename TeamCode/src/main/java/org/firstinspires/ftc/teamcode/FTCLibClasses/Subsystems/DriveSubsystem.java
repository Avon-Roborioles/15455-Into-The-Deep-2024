package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.function.DoubleSupplier;

public class DriveSubsystem extends SubsystemBase {

    private  MecanumDrive drive;
    private  DoubleSupplier forward;
    private  DoubleSupplier strafe;
    private  DoubleSupplier rotate;
    private  ToggleButtonReader toggleFieldToBotCentric;
    private  DoubleSupplier heading;

        private double startHeading;


    private GamepadEx gamepadEx;

    public DriveSubsystem(HardwareMap hMap, GamepadEx gamepad, IMU imu){
        MotorEx leftFront = new MotorEx(hMap,RobotConfig.DriveConstants.frontLeftWheelName);
        MotorEx rightFront =  new MotorEx(hMap,RobotConfig.DriveConstants.frontRightWheelName);
        MotorEx backLeft = new MotorEx(hMap,RobotConfig.DriveConstants.backLeftWheelName);
        MotorEx backRight = new MotorEx(hMap,RobotConfig.DriveConstants.backRightWheelName);


        leftFront.setInverted(true);
        rightFront.setInverted(true);
        backLeft.setInverted(true);
        backRight.setInverted(true);



        this.drive = new MecanumDrive(leftFront, rightFront,backLeft,backRight);
        forward = gamepad::getLeftY;
        strafe = gamepad::getLeftX;
        rotate = gamepad::getRightX;
        toggleFieldToBotCentric = new ToggleButtonReader(gamepad, GamepadKeys.Button.RIGHT_BUMPER);
        startHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        heading = () -> imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)-startHeading;


        this.gamepadEx = gamepad;
    }


    public String getTelemetry(){
        return "";
    }


    public void driverControlDrive(){

        drive.driveFieldCentric(
                strafe.getAsDouble(),
                forward.getAsDouble(),
                rotate.getAsDouble(),
                heading.getAsDouble()
        );

        if (gamepadEx.gamepad.left_bumper&&gamepadEx.gamepad.right_bumper){
            startHeading = heading.getAsDouble();
        }

    }




//
//    public void doTeleOp(){
//        if (curCommand!=null){
//            curCommand.cancel();
//            curCommand = null;
//            isCurCommandScheduled =false;
//        }
//        isAuto = false;
//    }



}





