package org.firstinspires.ftc.teamcode.FTCLibClasses;


import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.MotorCommandTutorial;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.MotorTutorial;

@TeleOp
public class FTCLibTeleOpTutorial extends OpMode {

    private MotorTutorial motorSubsystem;
    private MotorCommandTutorial motorCommand;
    private GamepadEx gamepadEx1;

    @Override
    public void init(){
        gamepadEx1 =new GamepadEx(gamepad1);
        motorSubsystem = new MotorTutorial(hardwareMap);
        motorCommand = new MotorCommandTutorial(motorSubsystem,gamepadEx1);

        Trigger commandTrigger = new Trigger(() ->{return gamepadEx1.getButton(GamepadKeys.Button.A);});
        commandTrigger.whenActive(motorCommand);
    }

    @Override
    public void loop(){
        gamepadEx1.readButtons();
        CommandScheduler.getInstance().run();
    }

}
