package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

@TeleOp
public class CompTeleOp extends OpMode {

    private GamepadEx drivePad;
    private GamepadEx gamepadEx2;
    private TeleOpDriveCommand driveCommand;


    @Override
    public void init(){
        drivePad = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        IMU imu = hardwareMap.get(IMU.class,"imu");

        IMU.Parameters imuParams= new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(imuParams);

        DriveSubsystem driveSubsystem = new DriveSubsystem(hardwareMap,drivePad,imu);
        driveCommand = new TeleOpDriveCommand(driveSubsystem);
    }

    @Override
    public void loop(){
        drivePad.readButtons();
        gamepadEx2.readButtons();
        driveCommand.schedule();
        CommandScheduler.getInstance().run();
    }


}
