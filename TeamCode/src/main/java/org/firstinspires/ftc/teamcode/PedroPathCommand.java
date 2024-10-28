package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;

public class PedroPathCommand extends CommandBase {

    private FollowerSubsystem follower;
    private PathChain pathChain=null;
    private Path path = null;

    private PathType type;

    public PedroPathCommand(FollowerSubsystem follower, Path path){
        type = PathType.PATH;

        this.follower = follower;
        this.path = path;
        addRequirements(follower);
    }

    public PedroPathCommand(FollowerSubsystem follower,PathChain pathChain){
        this.follower = follower;
        this.pathChain = pathChain;
        addRequirements(follower);
    }

    @Override
    public void initialize(){
        switch (type) {
            case PATH:
                follower.getFollower().followPath(path);
                break;
            case PATH_CHAIN:
                follower.getFollower().followPath(pathChain);
        }
    }
    @Override
    public void execute(){
        follower.getFollower().update();
    }

    @Override
    public boolean isFinished(){
        return !follower.getFollower().isBusy();
    }

    @Override
    public void end(boolean b){
        follower.getFollower().breakFollowing();
    }
    enum PathType {
        PATH_CHAIN,
        PATH
    }

}
