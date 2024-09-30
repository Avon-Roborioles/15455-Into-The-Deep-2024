package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Test;

@TeleOp
public class ServoTestOpMode extends LinearOpMode {

    private Test servoControl;

    @Override
    public void runOpMode() throws InterruptedException{
        servoControl = new Test(hardwareMap);
        waitForStart();
        while(opModeIsActive()&&!isStopRequested()){
            if (gamepad1.x){
                servoControl.setBigRange();
            } else if(gamepad1.b){
                servoControl.setSmallRange();
            }

            double servoPos = servoControl.getServoPos();
            double newServoPos = servoPos+gamepad1.right_stick_y*.001;

            if(newServoPos<0){
                newServoPos = 0;
            } else if (newServoPos >1){
                newServoPos =1;
            }

            servoControl.setServoPos(newServoPos);

        }
    }
}
