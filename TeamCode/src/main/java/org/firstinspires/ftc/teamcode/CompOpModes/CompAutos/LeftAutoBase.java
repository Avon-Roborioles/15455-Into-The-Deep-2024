package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.PedroPathAutoCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;



abstract public class LeftAutoBase extends AutoBaseRoutine{




    @Override
    public void specificInit(){
        Point blueGoal = new Point(new Pose(26.8,-4.5));
        Point rightWhiteSpike = new Point(new Pose(19,-17.1,Math.toRadians(270)));
        Point middleWhiteSpike = new Point(new Pose(26.87,-18));
        Point leftWhiteSpike = new Point(new Pose(22.5,-33.3));
        Point submersibleStart = new Point(new Pose(-6.8,-53.8));
        Point submersibleEnd = new Point(new Pose(-6.8,-48.8));
        Point submersibleParameterPoint = new Point(new Pose(15.9,-53.4));

        robot.followerSubsystem.getFollower().setPose(new Pose(8,0,3*PI/2));
        //robot.followerSubsystem.getFollower().setMaxPower(.75);

        Path startToGoal =  new Path.PathBuilder(
                new BezierLine(
                        new Point(robot.followerSubsystem.getFollower().getPose()),
                        blueGoal
                )


        )
                .setConstantHeadingInterpolation(5*PI/4)
                //.setPathEndVelocityConstraint(2)
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
                .setConstantHeadingInterpolation(5*PI/4)
//                .setPathEndVelocityConstraint(.5)
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
                .setConstantHeadingInterpolation(5*PI/4)
                //.setPathEndVelocityConstraint(.5)
                .build();
        Path fromGoalToSubmersible = new Path.PathBuilder(
                new BezierCurve(
                        blueGoal,
                        submersibleParameterPoint,
                        submersibleStart
                )
        )
                .setLinearHeadingInterpolation(5*PI/4,PI)
                .build();

        //Makes the path for scanning for samples in the submersible
        Path submersibleToRightSubmersible = new Path.PathBuilder(
                new BezierLine(
                        submersibleStart,
                        submersibleEnd
                )
        )
                .setConstantHeadingInterpolation(PI)
                .build();

        Path fromSubmersibleToGoal = new Path.PathBuilder(
                new BezierCurve(
                        submersibleStart,
                        submersibleParameterPoint,

                        blueGoal
                )
        )
                .setLinearHeadingInterpolation(5*PI/4,PI)
                .setConstantHeadingInterpolation(5*PI/4)
                //.setPathEndVelocityConstraint(.5)
                .build();

        //makes paths into commands
        PedroPathAutoCommand startToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,startToGoal);
        PedroPathAutoCommand fromGoalToRightSpikeCommand = new PedroPathAutoCommand(robot.followerSubsystem, fromGoalToRightSpike);
        PedroPathAutoCommand fromRightSpikeToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem, fromRightSpikeToGoal);
        PedroPathAutoCommand fromGoalToMiddleSpikeCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromGoalToMiddleSpike);
        PedroPathAutoCommand fromMiddleSpikeToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromMiddleSpikeToGoal);
        PedroPathAutoCommand fromGoalToLeftSpikeCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromGoalToLeftSpike);
        PedroPathAutoCommand fromLeftSpikeToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromLeftSpikeToGoal);
        PedroPathAutoCommand fromGoalToSubmersibleCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromGoalToSubmersible);
        PedroPathAutoCommand submersibleToRightSubmersibleCommand = new PedroPathAutoCommand(robot.followerSubsystem,submersibleToRightSubmersible);
        PedroPathAutoCommand fromSubmersibleToGoalCommand = new PedroPathAutoCommand(robot.followerSubsystem,fromSubmersibleToGoal);


        SequentialCommandGroup groupRetractIntake = new SequentialCommandGroup(robot.moveIntakeUp,robot.retractIntake,robot.passIntoBucket);

        //have to clear the grouped commands so that we can reuse the commands for multiple command groups
        CommandGroupBase.clearGroupedCommands();

        //dunks
        SequentialCommandGroup dunkRoutine = new SequentialCommandGroup(
                robot.liftCommand,
                robot.armCommand,
                new WaitCommand(200),
                new ParallelCommandGroup(
                        robot.liftDownCommand,
                        robot.armDownCommand
                )
        );
        CommandGroupBase.clearGroupedCommands();

        SequentialCommandGroup dunk = new SequentialCommandGroup(
                robot.liftCommand,
                robot.armCommand,
                new WaitCommand(200)
        );
        SequentialCommandGroup outtakeDown = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        robot.liftDownCommand,
                        robot.armDownCommand
                )
        );
        CommandGroupBase.clearGroupedCommands();


        //goes from the start to the goal and dunks
        ParallelCommandGroup startGoalDunk = new ParallelCommandGroup(
                startToGoalCommand,
                new SequentialCommandGroup(
                        robot.extendIntakeToClearPos.copy(),
                        new ParallelCommandGroup(
                                dunk,
                                robot.extendIntake.copy()
                        )
                )
        );

        CommandGroupBase.clearGroupedCommands();

        //goes from the goal to the right spike, intakes the sample, and passes the sample into the bucket
        SequentialCommandGroup goalRightSpikeIntakePass = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        fromGoalToRightSpikeCommand,
                        outtakeDown
                ),
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
                        groupRetractIntake,
                        fromRightSpikeToGoalCommand
                ),
                new SequentialCommandGroup(
                        robot.extendIntakeToClearPos.copy(),
                        new ParallelCommandGroup(
                                dunk,
                                robot.extendIntake.copy()
                        )
                )
        );

        CommandGroupBase.clearGroupedCommands();

        //goes to the middle spike from the goal, intakes and passes it in
        SequentialCommandGroup goalMiddleSpikeIntakePass = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        fromGoalToMiddleSpikeCommand,
                        outtakeDown
                ),
//                new ParallelCommandGroup(
//                        robot.moveIntakeDown,
//                        robot.spinIntake
//                ),
                robot.verticalAndSpin
        );
        CommandGroupBase.clearGroupedCommands();

        //goes from the middle spike to the middle spike and dunks
        SequentialCommandGroup middleSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        groupRetractIntake,
                        fromMiddleSpikeToGoalCommand
                ),
                new SequentialCommandGroup(
                        robot.extendIntakeToClearPos.copy(),
                        new ParallelCommandGroup(
                                dunk,
                                robot.extendIntake.copy()
                        )
                )
        );
        CommandGroupBase.clearGroupedCommands();

        SequentialCommandGroup goalLeftSpikeIntakePass = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        fromGoalToLeftSpikeCommand,
                        outtakeDown
                ),
//                new ParallelCommandGroup(
//                        robot.moveIntakeDown,
//                        robot.spinIntake
//                ),
                robot.verticalAndSpin
                //groupRetractIntake
        );

        CommandGroupBase.clearGroupedCommands();

        //goes from the middle spike to the middle spike and dunks
        SequentialCommandGroup leftSpikeGoalDunk = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        groupRetractIntake,
                        fromLeftSpikeToGoalCommand
                ),
                new SequentialCommandGroup(
                        robot.extendIntakeToClearPos.copy(),
                        new ParallelCommandGroup(
                                dunk,
                                robot.extendIntake.copy()
                        )
                )
        );

        //scan for samples in submersible

        SequentialCommandGroup scanForSamples = new SequentialCommandGroup(
                new InstantCommand(() ->robot.followerSubsystem.getFollower().setMaxPower(.2)),
//                new ParallelCommandGroup(
//                        submersibleToRightSubmersibleCommand,
//                        new FunctionalCommand(
//                                () ->{},
//                                () ->{},
//                                (Boolean b)->{},
//                                () -> robot.spinIntakeSubsystem.hasCorrectSample()
//                        )
//                ),
                robot.verticalAndSpin,
                new InstantCommand(() ->robot.followerSubsystem.getFollower().setMaxPower(1))
        );
        CommandGroupBase.clearGroupedCommands();

        //back to submersible and score
        SequentialCommandGroup submersibleToBasketAndScore = new SequentialCommandGroup(
                new ParallelCommandGroup(
                        groupRetractIntake,
                        fromSubmersibleToGoalCommand
                ),
                new SequentialCommandGroup(
                        robot.extendIntakeToClearPos.copy(),
                        new ParallelCommandGroup(
                                dunkRoutine,
                                robot.extendIntake.copy()
                        )
                )
        );

        //puts all commands together
        SequentialCommandGroup autoRoutine = new SequentialCommandGroup(
                new InstantCommand(()->robot.followerSubsystem.getFollower().setMaxPower(.75)),
                startGoalDunk,
                new InstantCommand(()->robot.followerSubsystem.getFollower().setMaxPower(1)),
                goalRightSpikeIntakePass,
                rightSpikeGoalDunk,
                goalMiddleSpikeIntakePass,
                middleSpikeGoalDunk,
                goalLeftSpikeIntakePass,
                leftSpikeGoalDunk,
                new ParallelCommandGroup(
                        fromGoalToSubmersibleCommand,
                        outtakeDown
                ),
                scanForSamples,
                submersibleToBasketAndScore
        );

        autoRoutine.schedule();

    }
}
