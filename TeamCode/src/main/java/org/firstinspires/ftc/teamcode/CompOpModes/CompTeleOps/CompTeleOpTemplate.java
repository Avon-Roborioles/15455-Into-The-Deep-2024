package org.firstinspires.ftc.teamcode.CompOpModes.CompTeleOps;

import static java.lang.Math.PI;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.CompOpModes.RobotOpMode;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.CurrentPoseStartPathCommand;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;


public abstract class CompTeleOpTemplate extends RobotOpMode {

    @Override
    public final void createLogic(){
        setAllianceColor();
        robot.followerSubsystem.getFollower().setPose(RobotConfig.GlobalConstants.lastPose);
        //robot.followerSubsystem.getFollower().setPose(new Pose(0,0,3*PI/2));
        SequentialCommandGroup groupRetractIntake = new SequentialCommandGroup(robot.retractIntake.copy(),robot.passIntoBucket.copy());
        CommandGroupBase.clearGroupedCommands();

        robot.verticalAndSpin = new ParallelCommandGroup(
                new SequentialCommandGroup(
                        robot.moveIntakeDown.copy(),
                        new FunctionalCommand(
                                () ->{},
                                () ->{},
                                (Boolean b) ->{},
                                () -> drivePad.getButton(GamepadKeys.Button.RIGHT_BUMPER)|| drivePad.getButton(GamepadKeys.Button.Y)
                        ),
                        robot.moveIntakeUp.copy()
                ),

                new FunctionalCommand(
                        robot.spinIntakeSubsystem::spinWheelsUp,
                        () ->{},
                        (Boolean b) ->{
//                                    robot.spinIntakeSubsystem.stopIntakeWheels();

                        },
                        ()-> true,
                        robot.spinIntakeSubsystem
                )
        );

        SequentialCommandGroup groupExtendIntake = new SequentialCommandGroup(
                robot.extendIntake.copy(),
                new ParallelCommandGroup(
                        robot.moveIntakeDown.copy(),
                        robot.spinIntake.copy()
                        )
        );

        CommandGroupBase.clearGroupedCommands();

        CurrentPoseStartPathCommand fromSubmersibleToBasket = new CurrentPoseStartPathCommand(
                robot.followerSubsystem,
                5* PI/4,
                new Point(new Pose(28.9,-3.5))
        );
        CurrentPoseStartPathCommand fromSubmersibleToLowBasket = new CurrentPoseStartPathCommand(
                robot.followerSubsystem,
                3*PI/4,
                new Point(new Pose(-42,29.4))

        );


        SequentialCommandGroup intakeRoutine = new SequentialCommandGroup(
                robot.extendIntake.copy(),
                robot.verticalAndSpin,
                groupRetractIntake,
                robot.extendIntake.copy()
        );

//        SequentialCommandGroup intakeRoutine = new SequentialCommandGroup(
//                robot.extendIntake,
//                robot.verticalAndSpin,
//                new SelectCommand(
//                        new HashMap<Object, Command>() {{
//                            put(SpinIntakeSubsystem.SampleState.CORRESPONDING_SAMPLE,finishRoutine);
//                            put(SpinIntakeSubsystem.SampleState.YELLOW_SAMPLE,finishRoutine);
//                            put(SpinIntakeSubsystem.SampleState.WRONG_SAMPLE,
//                                    new SequentialCommandGroup(
//                                            new RelativePathCommand(
//                                                    robot.followerSubsystem,
//                                                    new Point(10,0,1),
//                                                    0
//                                                    ),
//                                            robot.passIntoBucket,
//                                            new RelativePathCommand(
//                                                    robot.followerSubsystem,
//                                                    new Point(-10,0,1),
//                                                    0
//                                            )
//
//                                    )
//                            );
//                        }},
//                        robot.spinIntakeSubsystem::hasCorrectSample
//                )
//        );



//        intakeRoutine.interruptOn(() -> drivePad.getButton(GamepadKeys.Button.LEFT_BUMPER));
        CommandGroupBase.clearGroupedCommands();
        SequentialCommandGroup specimenDropRoutine = new SequentialCommandGroup(
                robot.extendIntake.copy(),
                robot.verticalAndSpin
        );
        drivePad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SequentialCommandGroup(
                        robot.extendIntake.copy(),
                        robot.passIntoBucket.copy()
                )
        );
        CommandGroupBase.clearGroupedCommands();

        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(robot.armDownCommand);
        CommandGroupBase.clearGroupedCommands();

        drivePad.getGamepadButton(GamepadKeys.Button.Y).whenActive(specimenDropRoutine);
        CommandGroupBase.clearGroupedCommands();
        Trigger takeOutOfBucket = new Trigger(() -> {return drivePad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>.7;});
        takeOutOfBucket.whenActive(
                new SequentialCommandGroup(
                        robot.retractIntake.copy(),
                        robot.moveIntakeDown.copy(),
                        new FunctionalCommand(
                                robot.spinIntakeSubsystem::spinWheelsUp,
                                () ->{},
                                (Boolean b) ->{
//                                    robot.spinIntakeSubsystem.stopIntakeWheels();

                                },
                                ()-> true,
                                robot.spinIntakeSubsystem
                        ),
                        new WaitCommand(1250),
                        robot.moveIntakeUp.copy(),
                        robot.extendIntake.copy(),
                        robot.passIntoBucket.copy()
                )
        );

        SequentialCommandGroup dunk = new SequentialCommandGroup(
                robot.liftCommand,
                robot.armCommand,

                new WaitCommand(500),
                new InstantCommand(robot.armSubsystem::setBrake),

                new ParallelCommandGroup(
                        robot.liftDownCommand,
                        robot.armDownCommand
                ),
                new WaitCommand(250),
                new InstantCommand(robot.armSubsystem::setFloat)
        );
        Trigger intakeTrigger = new Trigger(()-> drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>.7);
        intakeTrigger.whenActive(intakeRoutine);
        drivePad.getGamepadButton(GamepadKeys.Button.X).and(
                new Trigger(()->{return
                        Math.abs(drivePad.getLeftY())<0.1
                                ||Math.abs(drivePad.getRightY())<.1
                                ||Math.abs(drivePad.getLeftX())<.1;
                }))
                .whenActive(
                new SequentialCommandGroup(
                        fromSubmersibleToBasket,
                        dunk
                )
        );

        CommandGroupBase.clearGroupedCommands();
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(dunk);
        CommandGroupBase.clearGroupedCommands();
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenActive(
                new SequentialCommandGroup(
                        robot.armLowDunkCommand.copy(),
                        new WaitCommand(500),
                        robot.armDownCommand
                )
        );
        CommandGroupBase.clearGroupedCommands();

//        gamepadEx2.getGamepadButton(GamepadKeys.Button.Y).and(
//                new Trigger(()->{return
//                        Math.abs(drivePad.getLeftY())>0.1
//                        ||Math.abs(drivePad.getRightY())>.1
//                        ||Math.abs(drivePad.getLeftX())>.1;
//                })).whenActive(
//                new SequentialCommandGroup(
//                        fromSubmersibleToLowBasket,
//                        robot.armLowDunkCommand.copy(),
//                        new WaitCommand(500),
//                        robot.armDownCommand
//
//                )
//        );
        robot.followerSubsystem.setDefaultCommand(robot.followerTeleOpCommand);
    }

    @Override
    public String getTelemetry(){
        return "Last Pose\n"+RobotConfig.GlobalConstants.lastPose;
    }

    public abstract void setAllianceColor();
}
