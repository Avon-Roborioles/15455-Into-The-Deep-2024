package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.PathBuilder;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ProxyScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeDown;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeUp;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.PassIntoBucket;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.PedroPathCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChainBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.vision.VisionPortal;


abstract class AutoBaseRoutine extends OpMode {

    private Follower follower;
    private FollowerSubsystem followerSubsystem;
    protected AllianceColor allianceColor;

    @Override
    public void init(){
        setAllianceColor();
        follower = new Follower(hardwareMap);
        followerSubsystem =new FollowerSubsystem(follower);
        follower.setPose(
                new Pose(0,0,Math.toRadians(90))
        );
        Path goRight =  new Path.PathBuilder(
                new BezierLine(
                         new Point(follower.getPose()),
                         new Point(
                                 new Pose(13.9,17.17,Math.toRadians(90))
                         )
                )

        )
                .setConstantHeadingInterpolation(PI/2)
                .build();
//        new Path(
//                new BezierLine(
//                        new Point(follower.getPose()),
//                        new Point(
//                                new Pose(13.9,17.17,Math.toRadians(90))
//                        )
//                )
//        );
        goRight.setConstantHeadingInterpolation(PI/2);

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap,allianceColor,telemetry,() ->false,gamepad1);
        SpinIntake spinIntake = new SpinIntake(intake);
        ExtendIntake extendIntake = new ExtendIntake(intake);
        RetractIntake retractIntake = new RetractIntake(intake);
        PassIntoBucket passIntoBucket = new PassIntoBucket(intake);
        MoveIntakeUp moveIntakeUp = new MoveIntakeUp(intake);
        MoveIntakeDown moveIntakeDown = new MoveIntakeDown(intake);
        SequentialCommandGroup groupExtendIntake = new SequentialCommandGroup(
                extendIntake,
                new ParallelCommandGroup(
                        moveIntakeDown,
                        new ProxyScheduleCommand(spinIntake)
                )
        );



        PedroPathCommand goRightCommand = new PedroPathCommand(followerSubsystem,goRight);
        ParallelCommandGroup moveAndIntake = new ParallelCommandGroup(
                goRightCommand,
                groupExtendIntake
        );
        moveAndIntake.schedule();
    }
    @Override
    public void loop(){
        CommandScheduler.getInstance().run();
    }

    abstract public void setAllianceColor();
}
