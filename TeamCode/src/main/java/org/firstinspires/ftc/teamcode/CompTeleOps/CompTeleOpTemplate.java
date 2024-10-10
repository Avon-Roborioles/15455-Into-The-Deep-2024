package org.firstinspires.ftc.teamcode.CompTeleOps;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Test.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;


public abstract class CompTeleOpTemplate extends OpMode {

    private GamepadEx drivePad;
    private GamepadEx gamepadEx2;
    private TeleOpDriveCommand driveCommand;

    private ExtendIntake extendIntake;
    private SpinIntake spinIntake;
    private RetractIntake retractIntake;
    private SequentialCommandGroup fullIntakeRoutine;

    protected AllianceColor allianceColor;

    @Override
    public final void init(){

        setAllianceColor();


        drivePad = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        IMU imu = hardwareMap.get(IMU.class,"imu");

        IMU.Parameters imuParams= new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        while(!imu.initialize(imuParams)){
            continue;
        }

        DriveSubsystem driveSubsystem = new DriveSubsystem(hardwareMap,drivePad,imu);
        driveCommand = new TeleOpDriveCommand(driveSubsystem);

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, allianceColor, telemetry, () -> true, gamepad1);

        extendIntake = new ExtendIntake(intake);
        spinIntake = new SpinIntake(intake);
        retractIntake = new RetractIntake(intake);

        drivePad.getGamepadButton(GamepadKeys.Button.X).whenActive(extendIntake);
        drivePad.getGamepadButton(GamepadKeys.Button.Y).whileActiveOnce(spinIntake);
        drivePad.getGamepadButton(GamepadKeys.Button.B).whenActive(retractIntake);


        //fullIntakeRoutine = new SequentialCommandGroup(extendIntake,spinIntake,retractIntake);
        //extendTrigger.whenActive(spinIntake);
        driveSubsystem.setDefaultCommand(driveCommand);
    }

    @Override
    public final void loop(){
        drivePad.readButtons();
        gamepadEx2.readButtons();



        CommandScheduler.getInstance().run();
    }


    @Override
    public void stop(){
        CommandScheduler.getInstance().cancelAll();
    }

    public abstract void setAllianceColor();
}
