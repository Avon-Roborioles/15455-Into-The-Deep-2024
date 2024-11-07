package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants;

public class FollowerSubsystem extends SubsystemBase {

    private Follower follower;
    private Telemetry telemetry;

    public FollowerSubsystem(Follower follower){
        this.follower = follower;
    }

    public void setTelemetry(Telemetry telemetry){
        this.telemetry = telemetry;

    }

    public Follower getFollower(){
        return follower;
    }

    @Override
    public void periodic(){
//        telemetry.addData("X",follower.getPose().getX());
//        telemetry.addData("Y",follower.getPose().getY());
    }
}
