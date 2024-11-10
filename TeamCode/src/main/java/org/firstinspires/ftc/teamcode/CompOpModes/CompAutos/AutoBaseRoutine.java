package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import android.util.Size;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ProxyScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.AprilTagReader;
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
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.NoAprilTagFoundException;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.PedroPathAutoCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


abstract class AutoBaseRoutine extends OpMode {

    private Follower follower;
    protected FollowerSubsystem followerSubsystem;
    protected AllianceColor allianceColor;

    private AprilTagReader aprilTagReader;

    protected SpinIntake spinIntake;
    protected ExtendIntake extendIntake;
    protected RetractIntake retractIntake ;
    protected MoveIntakeUp moveIntakeUp ;
    protected PassIntoBucket passIntoBucket;
    protected MoveIntakeDown moveIntakeDown ;
    protected SequentialCommandGroup groupRetractIntake;

    protected LiftHighBasketCommand liftCommand ;
    protected LiftDownCommand liftDownCommand;
    protected ArmHighDunkCommand armCommand ;
    protected ArmDownCommand armDownCommand ;

    protected SequentialCommandGroup dunk ;
    protected SequentialCommandGroup armAndLiftDown;

    @Override
    public void init(){

        setAllianceColor();


        follower = new Follower(hardwareMap);

        followerSubsystem = new FollowerSubsystem(follower);
        followerSubsystem.setTelemetry(telemetry);

        telemetry.update();

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap,allianceColor,telemetry,() ->false,gamepad1);
        spinIntake = new SpinIntake(intake);
        extendIntake = new ExtendIntake(intake);
        retractIntake = new RetractIntake(intake);
        passIntoBucket = new PassIntoBucket(intake);
        moveIntakeUp = new MoveIntakeUp(intake);
        moveIntakeDown = new MoveIntakeDown(intake);

        groupRetractIntake = new SequentialCommandGroup(moveIntakeUp,retractIntake,passIntoBucket);

        LiftSubsystem liftSubsystem = new LiftSubsystem(hardwareMap,telemetry);
        ArmSubsystem armSubsystem = new ArmSubsystem(hardwareMap,telemetry);

        liftCommand = new LiftHighBasketCommand(liftSubsystem);
        liftDownCommand = new LiftDownCommand(liftSubsystem);
        armCommand = new ArmHighDunkCommand(armSubsystem);
        armDownCommand = new ArmDownCommand(armSubsystem);

        dunk = new SequentialCommandGroup(liftCommand,armCommand);
        armAndLiftDown = new SequentialCommandGroup(liftDownCommand,armDownCommand);


        CommandGroupBase.clearGroupedCommands();

        specificInit();
    }

    abstract public void specificInit();

    @Override
    public void loop(){
        CommandScheduler.getInstance().run();
    }

    @Override
    public void stop(){        CommandScheduler.getInstance().reset();}

    abstract public void setAllianceColor();
}
