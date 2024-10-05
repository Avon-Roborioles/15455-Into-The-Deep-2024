package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class MotorTuneTeleOp extends OpMode {

    private MotorTune motorTune;
    
    @Override
    public void init(){
        motorTune = new MotorTune(hardwareMap,"extendMotor",gamepad1,telemetry);
    }
}
