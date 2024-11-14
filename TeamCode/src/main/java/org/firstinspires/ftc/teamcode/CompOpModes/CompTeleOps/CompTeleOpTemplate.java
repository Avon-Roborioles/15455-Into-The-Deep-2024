package org.firstinspires.ftc.teamcode.CompOpModes.CompTeleOps;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.CompOpModes.RobotOpMode;

public abstract class CompTeleOpTemplate extends RobotOpMode {

    @Override
    public final void createLogic(){
        setAllianceColor();

        SequentialCommandGroup groupRetractIntake = new SequentialCommandGroup(robot.moveIntakeUp,robot.retractIntake,robot.passIntoBucket);
        SequentialCommandGroup groupExtendIntake = new SequentialCommandGroup(
                robot.extendIntake,
                new ParallelCommandGroup(
                        robot.moveIntakeDown,
                        robot.spinIntake
                        )
        );

        CommandGroupBase.clearGroupedCommands();

        SequentialCommandGroup intakeRoutine = new SequentialCommandGroup(
                groupExtendIntake,
                groupRetractIntake,
                robot.extendIntake
        );

        SequentialCommandGroup dunk = new SequentialCommandGroup(robot.liftCommand,robot.armCommand,robot.liftDownCommand,robot.armDownCommand);

        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(intakeRoutine);
        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(groupRetractIntake);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(dunk);

        boolean isPedroPathingDrive = true;
        if (isPedroPathingDrive){
            robot.followerSubsystem.setDefaultCommand(robot.followerTeleOpCommand);

        } else {
            robot.driveSubsystem.setDefaultCommand(robot.driveCommand);
        }
    }

    @Override
    public String getTelemetry(){
        return "Drive Telemetry\n"+robot.driveSubsystem.getTelemetry();
    }

    public abstract void setAllianceColor();
}
