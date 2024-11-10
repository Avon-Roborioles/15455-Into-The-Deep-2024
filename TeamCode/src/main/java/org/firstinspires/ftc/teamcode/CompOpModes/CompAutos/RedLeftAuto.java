package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.ProxyScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.PedroPathAutoCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;


@Autonomous
public class RedLeftAuto extends AutoBaseRoutine{

    @Override
    public void setAllianceColor(){
        allianceColor = AllianceColor.RED;
    }


    @Override
    public void specificInit(){
        Point blueGoal = new Point(new Pose(26.54,-2.5));
        Point rightWhiteSpike = new Point(new Pose(15,-16.8,Math.toRadians(270)));
        Point middleWhiteSpike = new Point(new Pose(16,-26.24));
        Point leftWhiteSpike = new Point(new Pose(18.29,-33.29));

        Pose startPose;

        followerSubsystem.getFollower().setPose(new Pose(0,0,3*PI/2));

        Path startToGoal =  new Path.PathBuilder(
                new BezierLine(
                        new Point(followerSubsystem.getFollower().getPose()),
                        blueGoal
                )


        )
                .setConstantHeadingInterpolation(5*PI/4)
                .build();
        Path fromGoalToRightSpike = new Path.PathBuilder(
                new BezierLine(
                        blueGoal,
                        rightWhiteSpike
                )
        )
                .setConstantHeadingInterpolation(3*PI/2)
                .build();
        Path fromRightSpikeToGoal = new Path.PathBuilder(
                new BezierLine(
                        rightWhiteSpike,
                        blueGoal
                )
        )
                .setLinearHeadingInterpolation(3*PI/2,5*PI/4)
                .setConstantHeadingInterpolation(5*PI/4)
                .build();
        Path fromGoalToMiddleSpike = new Path.PathBuilder(
                new BezierLine(
                        blueGoal,
                        middleWhiteSpike
                )
        )
                .setLinearHeadingInterpolation(5*PI/4,3*PI/2)
                .setConstantHeadingInterpolation(3*PI/2)
                .build();

        Path fromMiddleSpikeToGoal = new Path.PathBuilder(
                new BezierLine(
                        middleWhiteSpike,
                        blueGoal
                )
        )
                .setLinearHeadingInterpolation(3*PI/2,5*PI/4)
                .setConstantHeadingInterpolation(5*PI/4)
                .build();



        //makes paths into commands
        PedroPathAutoCommand startToGoalCommand = new PedroPathAutoCommand(followerSubsystem,startToGoal);
        PedroPathAutoCommand fromGoalToRightSpikeCommand = new PedroPathAutoCommand(followerSubsystem, fromGoalToRightSpike);
        PedroPathAutoCommand fromRightSpikeToGoalCommand = new PedroPathAutoCommand(followerSubsystem, fromRightSpikeToGoal);
        PedroPathAutoCommand fromGoalToMiddleSpikeCommand = new PedroPathAutoCommand(followerSubsystem,fromGoalToMiddleSpike);
        PedroPathAutoCommand fromMiddleSpikeToGoalCommand = new PedroPathAutoCommand(followerSubsystem,fromMiddleSpikeToGoal);


        //have to clear the grouped commands so that we can reuse the commands for multiple command groups
        CommandGroupBase.clearGroupedCommands();

        //dunks
        SequentialCommandGroup dunkRoutine = new SequentialCommandGroup(
                dunk,
                armAndLiftDown
        );
        CommandGroupBase.clearGroupedCommands();


        //goes from the start to the goal and dunks
        SequentialCommandGroup startGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        startToGoalCommand,
                        extendIntake
                ),
                dunkRoutine
        );

        CommandGroupBase.clearGroupedCommands();

        //goes from the goal to the right spike, intakes the sample, and passes the sample into the bucket
        SequentialCommandGroup goalRightSpikeIntakePass = new SequentialCommandGroup(
                fromGoalToRightSpikeCommand,
                new ParallelCommandGroup(
                        moveIntakeDown,
                        new ProxyScheduleCommand(spinIntake)
                ),
                groupRetractIntake
        );

        CommandGroupBase.clearGroupedCommands();


        //goes to the goal from the right spike, extends the intake and dunks
        SequentialCommandGroup rightSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        extendIntake,
                        fromRightSpikeToGoalCommand
                ),
                dunkRoutine
        );

        CommandGroupBase.clearGroupedCommands();

        //goes to the middle spike from the goal, intakes and passes it in
        SequentialCommandGroup goalMiddleSpikeIntakePass = new SequentialCommandGroup(
                fromGoalToMiddleSpikeCommand,
                new ParallelCommandGroup(
                        moveIntakeDown,
                        new ProxyScheduleCommand(spinIntake)
                ),
                groupRetractIntake
        );
        CommandGroupBase.clearGroupedCommands();

        //goes from the middle spike to the middle spike and dunks
        SequentialCommandGroup middleSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        extendIntake,
                        fromMiddleSpikeToGoalCommand
                ),
                dunkRoutine
        );

        //puts all commands together
        SequentialCommandGroup autoRoutine = new SequentialCommandGroup(
                startGoalDunk,
                goalRightSpikeIntakePass,
                rightSpikeGoalDunk,
                goalMiddleSpikeIntakePass,
                middleSpikeGoalDunk
        );

        autoRoutine.schedule();

    }
}
