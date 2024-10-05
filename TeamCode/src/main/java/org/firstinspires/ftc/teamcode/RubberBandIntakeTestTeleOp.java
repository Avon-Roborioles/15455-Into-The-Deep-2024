package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;


@TeleOp
public class RubberBandIntakeTestTeleOp extends OpMode {

    private IntakeSubsystem intake;
    private GamepadEx gamepadEx1;

    @Override
    public void init(){
        gamepadEx1 = new GamepadEx(gamepad1);
        intake = new IntakeSubsystem(hardwareMap,AllianceColor.BLUE,telemetry,() -> gamepadEx1.getButton(GamepadKeys.Button.A));

    }

    @Override
    public void loop(){
        IntakeSubsystem.SampleState state = intake.hasCorrectSample();
        telemetry.addLine(intake.getTelemetry());
        telemetry.update();
    }
}
