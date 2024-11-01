package org.firstinspires.ftc.teamcode.CompOpModes.CompTeleOps;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ProxyScheduleCommand;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.ArmDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.ArmHighDunkCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeDown;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeUp;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.PassIntoBucket;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftHighBasketCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Test.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;

import java.util.HashMap;


public abstract class CompTeleOpTemplate extends OpMode {

    private GamepadEx drivePad;
    private GamepadEx gamepadEx2;
    private TeleOpDriveCommand driveCommand;

    private ExtendIntake extendIntake;
    private SpinIntake spinIntake;
    private RetractIntake retractIntake;
    private PassIntoBucket passIntoBucket;
    private MoveIntakeUp moveIntakeUp;
    private MoveIntakeDown moveIntakeDown;
    private SequentialCommandGroup fullIntakeRoutine;

    protected AllianceColor allianceColor;

    private LiftSubsystem liftSubsystem;
    private LiftHighBasketCommand liftCommand;
    private LiftDownCommand liftDownCommand;


    private ArmSubsystem armSubsystem;
    private ArmHighDunkCommand armCommand;
    private ArmDownCommand armDownCommand;


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
        driveSubsystem.setTelemetry(telemetry);

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap, allianceColor, telemetry, () -> true, gamepad1);

        spinIntake = new SpinIntake(intake);
        extendIntake = new ExtendIntake(intake);
        retractIntake = new RetractIntake(intake);
        passIntoBucket = new PassIntoBucket(intake);
        moveIntakeUp = new MoveIntakeUp(intake);
        moveIntakeDown = new MoveIntakeDown(intake);

        SequentialCommandGroup groupRetractIntake = new SequentialCommandGroup(moveIntakeUp,retractIntake,passIntoBucket);
        SequentialCommandGroup groupExtendIntake = new SequentialCommandGroup(
                extendIntake,
                new SequentialCommandGroup(
                        moveIntakeDown,
                        new ProxyScheduleCommand(spinIntake)
                        )
        );
//        Command inlineSpinIntake = new SelectCommand(
//                new HashMap<Object,Command>() {{
//                    put(IntakeSubsystem.SampleState.NO_SAMPLE,new FunctionalCommand(
//                            () ->{},
//                            () -> {intake.spinWheelsUp();},
//                            (Boolean b) ->{intake.stopIntakeWheels();},
//                            () ->{return intake.}
//                    ))
//                }
//
//                }
//        )

        liftSubsystem = new LiftSubsystem(hardwareMap,telemetry);
        armSubsystem = new ArmSubsystem(hardwareMap,telemetry);

        liftCommand = new LiftHighBasketCommand(liftSubsystem);
        liftDownCommand = new LiftDownCommand(liftSubsystem);
        armCommand = new ArmHighDunkCommand(armSubsystem);
        armDownCommand = new ArmDownCommand(armSubsystem);

        SequentialCommandGroup dunk = new SequentialCommandGroup(liftCommand,armCommand);
        SequentialCommandGroup armAndLiftDown = new SequentialCommandGroup(liftDownCommand,armDownCommand);


        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(groupExtendIntake);
        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(groupRetractIntake);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(dunk);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(armAndLiftDown);


        //fullIntakeRoutine = new SequentialCommandGroup(extendIntake,spinIntake,retractIntake);
        //extendTrigger.whenActive(spinIntake);
        driveSubsystem.setDefaultCommand(driveCommand);


    }

    @Override
    public final void loop(){
        drivePad.readButtons();
        gamepadEx2.readButtons();

        telemetry.addData("Retract Finished",retractIntake.isFinished());
        CommandScheduler.getInstance().run();
    }




    public abstract void setAllianceColor();
}
