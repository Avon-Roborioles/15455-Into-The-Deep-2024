package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MotorTune;
import org.firstinspires.ftc.teamcode.RobotConfig;


@TeleOp
public class MotorTuneTeleOp extends OpMode {

    private MotorTune motorTune;


    @Override
    public void init(){

        motorTune = new MotorTune(hardwareMap, RobotConfig.IntakeConstants.verticalServoName,gamepad1,telemetry,()->0);
    }

    @Override
    public void loop(){
        motorTune.run();
        telemetry.update();
    }


}
