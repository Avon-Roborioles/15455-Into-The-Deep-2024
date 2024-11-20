package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.LogCatCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.PedroPathAutoCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;



abstract public class LeftAutoBase extends AutoBaseRoutine{




    @Override
    public void specificInit(){
        Point blueGoal = new Point(new Pose(24,-4.5));
        Point rightWhiteSpike = new Point(new Pose(16.6,-22.1,Math.toRadians(270)));
        Point middleWhiteSpike = new Point(new Pose(26.27,-22.24));
        Point leftWhiteSpike = new Point(new Pose(21.29,-33.29));

        robot.followerSubsystem.getFollower().setPose(new Pose(0,0,3*PI/2));

        Path startToGoal =  new Path.PathBuilder(
                new BezierLine(
                        new Point(robot.followerSubsystem.getFollower().getPose()),
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
                .setLinearHeadingInterpolation(5*PI/4,3*PI/2)

                .build();
        Path fromRightSpikeToGoal = new Path.PathBuilder(
                new BezierLine(
                        rightWhiteSpike,
                        blueGoal
                )
        )
                .setConstantHeadingInterpolation(5*PI/4)
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
//                .setConstantHeadingInterpolation(5*PI/4)
                .build();
        Path fromGoalToLeftSpike = new Path.PathBuilder(
                new BezierLine(
                        blueGoal,
                        leftWhiteSpike
                )
        )
                .setLinearHeadingInterpolation(5*PI/4,0)
                .build();

        Path fromLeftSpikeToGoal = new Path.PathBuilder(
                new BezierLine(
                        leftWhiteSpike,
                        blueGoal
                )
        )
                .setLinearHeadingInterpolation(0,5*PI/4)
                .build();


        //makes paths into commands
        PedroPathAutoCommand startToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,startToGoal);
        PedroPathAutoCommand fromGoalToRightSpikeCommand = new PedroPathAutoCommand(robot.followerSubsystem, fromGoalToRightSpike);
        PedroPathAutoCommand fromRightSpikeToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem, fromRightSpikeToGoal);
        PedroPathAutoCommand fromGoalToMiddleSpikeCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromGoalToMiddleSpike);
        PedroPathAutoCommand fromMiddleSpikeToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromMiddleSpikeToGoal);
        PedroPathAutoCommand fromGoalToLeftSpikeCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromGoalToLeftSpike);
        PedroPathAutoCommand fromLeftSpikeToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromLeftSpikeToGoal);


        SequentialCommandGroup groupRetractIntake = new SequentialCommandGroup(robot.moveIntakeUp,robot.retractIntake,robot.passIntoBucket);

        //have to clear the grouped commands so that we can reuse the commands for multiple command groups
        CommandGroupBase.clearGroupedCommands();

        //dunks
        SequentialCommandGroup dunkRoutine = new SequentialCommandGroup(
                robot.liftCommand,
                robot.armCommand,
                new WaitCommand(150),
                new ParallelCommandGroup(
                        robot.liftDownCommand,
                        robot.armDownCommand
                )
        );
        CommandGroupBase.clearGroupedCommands();


        //goes from the start to the goal and dunks
        SequentialCommandGroup startGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        startToGoalCommand,
                        robot.extendIntake
                ),
                dunkRoutine
        );

        CommandGroupBase.clearGroupedCommands();

        //goes from the goal to the right spike, intakes the sample, and passes the sample into the bucket
        SequentialCommandGroup goalRightSpikeIntakePass = new SequentialCommandGroup(
                fromGoalToRightSpikeCommand,
//                new ParallelCommandGroup(
//                        robot.moveIntakeDown,
//                        robot.spinIntake
//                ),
                robot.verticalAndSpin

        );

        CommandGroupBase.clearGroupedCommands();


        //goes to the goal from the right spike, extends the intake and dunks
        SequentialCommandGroup rightSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                groupRetractIntake,
                                robot.extendIntake
                        ),
                        fromRightSpikeToGoalCommand
                ),
                dunkRoutine
        );

        CommandGroupBase.clearGroupedCommands();

        //goes to the middle spike from the goal, intakes and passes it in
        SequentialCommandGroup goalMiddleSpikeIntakePass = new SequentialCommandGroup(
                fromGoalToMiddleSpikeCommand,
//                new ParallelCommandGroup(
//                        robot.moveIntakeDown,
//                        robot.spinIntake
//                ),
                robot.verticalAndSpin,
                groupRetractIntake
        );
        CommandGroupBase.clearGroupedCommands();

        //goes from the middle spike to the middle spike and dunks
        SequentialCommandGroup middleSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                groupRetractIntake,
                                robot.extendIntake
                        ),
                        fromMiddleSpikeToGoalCommand
                ),
                dunkRoutine
        );
        CommandGroupBase.clearGroupedCommands();

        SequentialCommandGroup goalLeftSpikeIntakePass = new SequentialCommandGroup(
                fromGoalToLeftSpikeCommand,
                new ParallelCommandGroup(
                        robot.moveIntakeDown,
                        robot.spinIntake
                ),
//                robot.verticalAndSpin,
                groupRetractIntake
        );

        CommandGroupBase.clearGroupedCommands();

        //goes from the middle spike to the middle spike and dunks
        SequentialCommandGroup leftSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                groupRetractIntake,
                                robot.extendIntake
                        ),
                        fromLeftSpikeToGoalCommand
                ),
                dunkRoutine
        );

        //puts all commands together
        SequentialCommandGroup autoRoutine = new SequentialCommandGroup(
                startGoalDunk,
                goalRightSpikeIntakePass,
                rightSpikeGoalDunk,
                goalMiddleSpikeIntakePass,
                middleSpikeGoalDunk,
                goalLeftSpikeIntakePass,
                leftSpikeGoalDunk
        );

        autoRoutine.schedule();

    }
}
