package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftCommandClass;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;

@TeleOp
public class CompTeleOp extends OpMode {

    private GamepadEx drivePad;
    private GamepadEx gamepadEx2;
    private TeleOpDriveCommand driveCommand;
    private LiftSubsystem armLift;
    private LiftCommandClass liftCommand;
    private GamepadEx armPad;


    @Override
    public void init(){
        drivePad = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        armPad = new GamepadEx(gamepad1);
        IMU imu = hardwareMap.get(IMU.class,"imu");

        IMU.Parameters imuParams= new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(imuParams);

        DriveSubsystem driveSubsystem = new DriveSubsystem(hardwareMap,drivePad,imu);
        LiftSubsystem liftSubsystem = new LiftSubsystem(hardwareMap);
        ArmSubsystem armSubsystem = new ArmSubsystem(hardwareMap);


        driveCommand = new TeleOpDriveCommand(driveSubsystem);
        liftCommand = new LiftCommandClass(liftSubsystem, armSubsystem);
        Button hold = new GamepadButton(armPad, GamepadKeys.Button.B);
        hold.whenHeld(liftCommand);
    }

    @Override
    public void loop(){
        drivePad.readButtons();
        gamepadEx2.readButtons();
        armPad.readButtons();
//        driveCommand.schedule();
        CommandScheduler.getInstance().run();
    }


}
