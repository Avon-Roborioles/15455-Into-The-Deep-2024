package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class MotorTuneTeleOp extends OpMode {

    private MotorTune motorTune;


    @Override
    public void init(){

        motorTune = new MotorTune(hardwareMap,RobotConfig.IntakeConstants.verticalServoName,gamepad1,telemetry,()->0);
    }

    @Override
    public void loop(){
        motorTune.run();
        telemetry.update();
    }


}
