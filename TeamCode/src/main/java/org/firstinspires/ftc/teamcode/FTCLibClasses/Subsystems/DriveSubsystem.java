package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.FunctionalCommand;
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
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Test.SubmersibleToBasket;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.function.DoubleSupplier;

public class DriveSubsystem extends SubsystemBase {

    private final MecanumDrive drive;
    private final DoubleSupplier forward;
    private final DoubleSupplier strafe;
    private final DoubleSupplier rotate;
    private final ToggleButtonReader toggleFieldToBotCentric;
    private final DoubleSupplier heading;

    private boolean isAuto = false;

    private Command curCommand=null;
    private boolean isCurCommandScheduled =false;

    private Telemetry telemetry = null;

    public DriveSubsystem(HardwareMap hMap, GamepadEx gamepad, IMU imu){
        MotorEx leftFront = new MotorEx(hMap,RobotConfig.DriveConstants.frontLeftWheelName);
        MotorEx rightFront =  new MotorEx(hMap,RobotConfig.DriveConstants.frontRightWheelName);
        MotorEx backLeft = new MotorEx(hMap,RobotConfig.DriveConstants.backLeftWheelName);
        MotorEx backRight = new MotorEx(hMap,RobotConfig.DriveConstants.backRightWheelName);

        backLeft.setInverted(true);
        backRight.setInverted(true);

//        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        this.drive = new MecanumDrive(leftFront, rightFront,backRight,backLeft);
        forward = gamepad::getLeftY;
        strafe = gamepad::getLeftX;
        rotate = gamepad::getRightX;
        toggleFieldToBotCentric = new ToggleButtonReader(gamepad, GamepadKeys.Button.RIGHT_BUMPER);
        heading = () -> imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

    }

    public DriveSubsystem(MotorEx[] motors, DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier rotate, DoubleSupplier heading, ToggleButtonReader toggleFieldToBotCentric){
        this.drive = new MecanumDrive(
                motors[0],
                motors[1],
                motors[2],
                motors[3]
        );
        this.forward = forward;
        this.strafe =strafe;
        this.rotate = rotate;
        this.heading = heading;
        this.toggleFieldToBotCentric = toggleFieldToBotCentric;
    }
    public boolean getDriveMode(){
        return toggleFieldToBotCentric.getState();
    }
    public void setTelemetry(Telemetry t){
        telemetry = t;
    }

//    @Override
//    public void periodic() {
//        if (!isAuto) {
//            driverControlDrive();
//        } else if(!isCurCommandScheduled){
//            curCommand.schedule();
//            isCurCommandScheduled = true;
//        } else {
//            isCurCommandScheduled = false;
//            isAuto = false;
//        }
//        if (telemetry!=null) {
//            telemetry.addData("isDrive Auto", isAuto);
//        }
//    }


    public void driverControlDrive(){

        drive.driveFieldCentric(
                -strafe.getAsDouble(),
                -forward.getAsDouble(),
                rotate.getAsDouble(),
                heading.getAsDouble()
        );

    }

    public void goForwardAuto(){
        drive.driveFieldCentric(0,1,0,heading.getAsDouble());
    }

    public void goBackwardsAuto(){
        drive.driveFieldCentric(0,-1,0,heading.getAsDouble());
    }

    public void goLeftAuto(){
        drive.driveFieldCentric(-1,0,0,heading.getAsDouble());
    }

    public void goRightAuto(){
        drive.driveFieldCentric(1,0,0,heading.getAsDouble());

    }

    public void doSubmersibleToBasket() {
        if(!(curCommand instanceof SubmersibleToBasket)) {
            if (curCommand != null) {
                curCommand.cancel();
            }
            curCommand = new SubmersibleToBasket(this);
            isCurCommandScheduled = false;
        }
        isAuto = true;
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





