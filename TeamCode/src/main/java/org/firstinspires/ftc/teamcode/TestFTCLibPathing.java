package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;

@TeleOp
public class TestFTCLibPathing extends OpMode {

    private DriveSubsystem driveSubsystem;
    private GamepadEx gamepad1Ex;


    @Override
    public void init() {
        IMU imu = hardwareMap.get(IMU.class,"imu");
        gamepad1Ex = new GamepadEx(gamepad1);

        driveSubsystem = new DriveSubsystem(hardwareMap,gamepad1Ex,imu);

        Trigger triggerX = new Trigger(() ->gamepad1Ex.getButton(GamepadKeys.Button.X));

        triggerX.whenActive(
                () -> driveSubsystem.doSubmersibleToBasket()
        );

        Trigger triggerY = new Trigger(() -> gamepad1Ex.getButton(GamepadKeys.Button.Y));
        //triggerY.whenActive(driveSubsystem::doTeleOp);
        driveSubsystem.setTelemetry(telemetry);

    }

    @Override
    public void loop(){
        gamepad1Ex.readButtons();
        CommandScheduler.getInstance().run();
        telemetry.update();
    }
}
