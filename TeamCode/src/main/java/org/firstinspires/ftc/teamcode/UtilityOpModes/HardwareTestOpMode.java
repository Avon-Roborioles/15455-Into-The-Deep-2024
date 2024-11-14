package org.firstinspires.ftc.teamcode.UtilityOpModes;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.CompOpModes.RobotOpMode;


@TeleOp(name = "Partial Auto Hardware Test", group = "Utility")
public class HardwareTestOpMode extends RobotOpMode {
    @Override
    public void setAllianceColor(){
        allianceColor = AllianceColor.BLUE;
    }
    @Override
    public void createLogic(){
        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenActive(robot.extendIntake);
        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenActive(robot.retractIntake);


        drivePad.getGamepadButton(GamepadKeys.Button.Y).whileActiveOnce(
                new FunctionalCommand(
                        ()->{},
                        ()->robot.spinIntakeSubsystem.spinWheelsUp(),
                        (Boolean b) ->{},
                        ()-> false,
                        robot.spinIntakeSubsystem
                )
        );
        drivePad.getGamepadButton(GamepadKeys.Button.A).whileActiveOnce(
                new FunctionalCommand(
                        ()->{},
                        ()->robot.spinIntakeSubsystem.spinWheelsDown(),
                        (Boolean b) ->{},
                        ()-> false,
                        robot.spinIntakeSubsystem
                )
        );

        CommandGroupBase.clearGroupedCommands();
        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(robot.moveIntakeUp);
        drivePad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(robot.moveIntakeDown);


        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenActive(robot.liftCommand);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenActive(robot.liftDownCommand);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenActive(robot.armDownCommand);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenActive(robot.armCommand);
    }
}
