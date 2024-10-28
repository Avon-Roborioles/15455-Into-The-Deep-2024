package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

public class FollowerSubsystem extends SubsystemBase {

    private Follower follower;

    public FollowerSubsystem(Follower follower){
        this.follower = follower;
    }

    public Follower getFollower(){
        return follower;
    }
}
