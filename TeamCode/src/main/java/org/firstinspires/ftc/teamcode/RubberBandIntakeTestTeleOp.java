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
        IntakeSubsystem.SampleState state = intake.hasCorrectSample();
        switch (state){
            case NO_SAMPLE:
                telemetry.addLine("No Sample");
                break;
            case WRONG_SAMPLE:
                telemetry.addLine("Wrong Sample");
                break;
            case CORRESPONDING_SAMPLE:
                telemetry.addLine("Right Sample");
                break;
            case YELLOW_SAMPLE:
                telemetry.addLine("Yellow Sample");
                break;
        }
        telemetry.addLine(intake.getTelemetry());
        telemetry.update();
    }
}
