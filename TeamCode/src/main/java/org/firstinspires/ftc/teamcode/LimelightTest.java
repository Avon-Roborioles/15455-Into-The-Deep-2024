package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LimelightTest {

    private Limelight3A limelight;

    public LimelightTest(HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class,"Limelight");
    }

    public void run(){

    }
    public Pose getCurPose(){
        return null;
    }
}
