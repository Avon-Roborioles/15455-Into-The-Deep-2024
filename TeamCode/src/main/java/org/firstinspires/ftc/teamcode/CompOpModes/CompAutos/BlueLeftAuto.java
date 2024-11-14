package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.PedroPathAutoCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;


@Autonomous
public class BlueLeftAuto extends AutoBaseRoutine{

    @Override
    public void setAllianceColor(){
        allianceColor = AllianceColor.BLUE;
    }


    @Override
    public void specificInit(){
        Point blueGoal = new Point(new Pose(26.54,-2.5));
        Point rightWhiteSpike = new Point(new Pose(15,-16.8,Math.toRadians(270)));
        Point middleWhiteSpike = new Point(new Pose(16,26.24));
        Point leftWhiteSpike = new Point(new Pose(18.29,-33.29));

        Pose startPose;

        followerSubsystem.getFollower().setPose(new Pose(0,0,3*PI/2));


        Path startToGoal =  new Path.PathBuilder(
                new BezierLine(
                        new Point(followerSubsystem.getFollower().getPose()),
                        blueGoal
                )

        )
                .setLinearHeadingInterpolation(3*PI/2,5*PI/4)
                .build();
        Path fromGoalToRightSpike = new Path.PathBuilder(
                new BezierLine(
                        blueGoal,
                        rightWhiteSpike
                )
        )
                .setLinearHeadingInterpolation(5*PI/4,3*PI/2)
                .build();
        Path fromRightSpikeToGoal = new Path.PathBuilder(
                new BezierLine(
                        rightWhiteSpike,
                        blueGoal
                )
        )
                .setLinearHeadingInterpolation(3*PI/2,5*PI/4)
                .build();
        Path fromGoalToMiddleSpike = new Path.PathBuilder(
                new BezierLine(
                        blueGoal,
                        middleWhiteSpike
                )
        )
                .setLinearHeadingInterpolation(5*PI/4,3*PI/2)
                .build();

        Path fromMiddleSpikeToGoal = new Path.PathBuilder(
                new BezierLine(
                        middleWhiteSpike,
                        blueGoal
                )
        )
                .setLinearHeadingInterpolation(3*PI/2,5*PI/4)
                .build();


        followerSubsystem.setTelemetry(telemetry);
        PedroPathAutoCommand startToGoalCommand = new PedroPathAutoCommand(followerSubsystem,startToGoal);
        PedroPathAutoCommand fromGoalToRightSpikeCommand = new PedroPathAutoCommand(followerSubsystem, fromGoalToRightSpike);
        PedroPathAutoCommand fromRightSpikeToGoalCommand = new PedroPathAutoCommand(followerSubsystem, fromRightSpikeToGoal);
        PedroPathAutoCommand fromGoalToMiddleSpikeCommand = new PedroPathAutoCommand(followerSubsystem,fromGoalToMiddleSpike);
        PedroPathAutoCommand fromMiddleSpikeToGoalCommand = new PedroPathAutoCommand(followerSubsystem,fromMiddleSpikeToGoal);
        SequentialCommandGroup moveAndIntake = new SequentialCommandGroup(
                startToGoalCommand,
                extendIntake,
                dunk,
                armAndLiftDown,
                groupRetractIntake,
                fromGoalToRightSpikeCommand,
                extendIntake,
                moveIntakeDown,
                spinIntake,
                groupRetractIntake,
                fromRightSpikeToGoalCommand,
                extendIntake,
                dunk,
                armAndLiftDown,
                groupRetractIntake,
                fromGoalToMiddleSpikeCommand,
                moveIntakeDown,
                groupRetractIntake,
                fromMiddleSpikeToGoalCommand,
                extendIntake,
                dunk,
                armAndLiftDown,
                groupRetractIntake
        );

        moveAndIntake.schedule();

    }
}
