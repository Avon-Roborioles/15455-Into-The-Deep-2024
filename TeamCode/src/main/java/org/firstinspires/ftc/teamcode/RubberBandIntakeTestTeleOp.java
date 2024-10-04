package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
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
        intake = new IntakeSubsystem(hardwareMap,AllianceColor.BLUE);

    }

    @Override
    public void loop(){
        IntakeSubsystem.PixelState state = intake.hasCorrectSample();
        switch (state){
            case NO_PIXEL:
                telemetry.addLine("No Pixel");
                break;
            case WRONG_PIXEL:
                telemetry.addLine("Wrong Pixel");
                break;
            case CORRECT_PIXEL:
                telemetry.addLine("Right Pixel");
                break;
        }
        telemetry.addLine(intake.getTelemetry());
        telemetry.update();
    }
}
