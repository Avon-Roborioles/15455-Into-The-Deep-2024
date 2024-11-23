package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.ProxyScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntakeToClearPos;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Outtake.ArmDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Outtake.ArmHighDunkCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.FollowerTeleOpCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeDown;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeUp;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.PassIntoBucket;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Outtake.LiftDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Outtake.LiftHighBasketCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.ExtendMotorSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.SpinIntakeSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.VerticalIntakeSubsystem;

public class Robot {
    public GamepadEx drivePad;
    public GamepadEx gamepadEx2;

    public DriveSubsystem driveSubsystem;
    public TeleOpDriveCommand driveCommand;
    public FollowerSubsystem followerSubsystem;
    public FollowerTeleOpCommand followerTeleOpCommand;


    public SpinIntakeSubsystem spinIntakeSubsystem;
    public VerticalIntakeSubsystem verticalIntakeSubsystem;
    public ExtendMotorSubsystem extendMotorSubsystem;

    public ExtendIntake extendIntake;
    public SpinIntake spinIntake;
    public RetractIntake retractIntake;
    public PassIntoBucket passIntoBucket;
    public MoveIntakeUp moveIntakeUp;
    public MoveIntakeDown moveIntakeDown;
    public ExtendIntakeToClearPos extendIntakeToClearPos;
    public ParallelCommandGroup verticalAndSpin;


    public LiftSubsystem liftSubsystem;
    public LiftHighBasketCommand liftCommand;
    public LiftDownCommand liftDownCommand;


    public ArmSubsystem armSubsystem;
    public ArmHighDunkCommand armCommand;
    public ArmDownCommand armDownCommand;

    public Robot(HardwareMap hMap, GamepadEx drivePad, GamepadEx gamepadEx2, Telemetry telemetry,AllianceColor color){
        this.drivePad = drivePad;
        this.gamepadEx2 = gamepadEx2;


        /*
        ====================Intake====================
         */
        verticalIntakeSubsystem = new VerticalIntakeSubsystem(hMap,telemetry);
        spinIntakeSubsystem = new SpinIntakeSubsystem(hMap,color,telemetry);
        extendMotorSubsystem = new ExtendMotorSubsystem(hMap,telemetry);

        extendIntake = new ExtendIntake(extendMotorSubsystem);
        retractIntake = new RetractIntake(extendMotorSubsystem);
        spinIntake = new SpinIntake(spinIntakeSubsystem);
        passIntoBucket = new PassIntoBucket(spinIntakeSubsystem);
        moveIntakeDown = new MoveIntakeDown(verticalIntakeSubsystem);
        moveIntakeUp = new MoveIntakeUp(verticalIntakeSubsystem);
        extendIntakeToClearPos = new ExtendIntakeToClearPos(extendMotorSubsystem);
        CommandGroupBase.clearGroupedCommands();

        verticalAndSpin = new ParallelCommandGroup(
                new SequentialCommandGroup(
                        moveIntakeDown,
                        new WaitCommand(1000),
                        moveIntakeUp
                ),

                new FunctionalCommand(
                        spinIntakeSubsystem::spinWheelsUp,
                        () ->{},
                        (Boolean b) ->{
//                                    robot.spinIntakeSubsystem.stopIntakeWheels();

                        },
                        ()-> true,
                        spinIntakeSubsystem
                )
        );


        /*
        ====================Lift/Dunk====================
         */
        liftSubsystem = new LiftSubsystem(hMap,telemetry);
        armSubsystem = new ArmSubsystem(hMap,telemetry);

        liftCommand = new LiftHighBasketCommand(liftSubsystem);
        liftDownCommand = new LiftDownCommand(liftSubsystem);
        armCommand = new ArmHighDunkCommand(armSubsystem);
        armDownCommand = new ArmDownCommand(armSubsystem);

        /*
        ====================Drive====================
         */
        IMU imu = hMap.get(IMU.class,"imu");
        driveSubsystem = new DriveSubsystem(hMap,drivePad,imu);
        driveCommand = new TeleOpDriveCommand(driveSubsystem);

        followerSubsystem = new FollowerSubsystem(hMap);
        followerSubsystem.setTelemetry(telemetry);
        followerTeleOpCommand = new FollowerTeleOpCommand(followerSubsystem,telemetry,drivePad);
        CommandGroupBase.clearGroupedCommands();
    }


    public void setBot(Bot bot){
        driveSubsystem.setBot(bot);
        spinIntakeSubsystem.setBot(bot);
        verticalIntakeSubsystem.setBotType(bot);
        followerSubsystem.getFollower().setBot(bot);
        extendMotorSubsystem.setBot(bot);
    }
}







