package org.firstinspires.ftc.teamcode.CompOpModes.CompTeleOps;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.CompOpModes.RobotOpMode;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive.RelativePathCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.SpinIntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

import java.util.HashMap;

public abstract class CompTeleOpTemplate extends RobotOpMode {

    @Override
    public final void createLogic(){
        setAllianceColor();

        SequentialCommandGroup groupRetractIntake = new SequentialCommandGroup(robot.retractIntake.copy(),robot.passIntoBucket.copy());
        CommandGroupBase.clearGroupedCommands();

        robot.verticalAndSpin = new ParallelCommandGroup(
                new SequentialCommandGroup(
                        robot.moveIntakeDown.copy(),
                        new FunctionalCommand(
                                () ->{},
                                () ->{},
                                (Boolean b) ->{},
                                () ->{return drivePad.getButton(GamepadKeys.Button.RIGHT_BUMPER);}
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

        drivePad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SequentialCommandGroup(
                        robot.extendIntake.copy(),
                        robot.passIntoBucket.copy()
                )
        );
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
                        robot.moveIntakeUp.copy(),
                        robot.extendIntake.copy(),
                        robot.passIntoBucket.copy()
                )
        );

        SequentialCommandGroup dunk = new SequentialCommandGroup(
                robot.liftCommand,
                robot.armCommand,
                new WaitCommand(150),
                robot.liftDownCommand,
                robot.armDownCommand
        );
        Trigger intakeTrigger = new Trigger(()-> drivePad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>.7);
        intakeTrigger.whenActive(intakeRoutine);
//        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(groupRetractIntake);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(dunk);

        robot.followerSubsystem.setDefaultCommand(robot.followerTeleOpCommand);
    }

    @Override
    public String getTelemetry(){
        return "Drive Telemetry\n"+robot.driveSubsystem.getTelemetry();
    }

    public abstract void setAllianceColor();
}
