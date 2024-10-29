package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;

import java.util.concurrent.TimeUnit;

public class PedroPathAutoCommand extends CommandBase {

    private FollowerSubsystem follower;
    private PathChain pathChain=null;
    private Path path = null;

    private PathType type;
    private Timing.Timer timer;

    private boolean isBusy;

    public PedroPathAutoCommand(FollowerSubsystem follower, Path path){
        type = PathType.PATH;

        this.follower = follower;
        this.path = path;
        addRequirements(follower);
        timer = new Timing.Timer(1500,TimeUnit.MILLISECONDS);
    }

    public PedroPathAutoCommand(FollowerSubsystem follower, PathChain pathChain){
        this.follower = follower;
        this.pathChain = pathChain;
        addRequirements(follower);
    }

    @Override
    public void initialize(){
        switch (type) {
            case PATH:
                follower.getFollower().followPath(path,true);
                break;
            case PATH_CHAIN:
                follower.getFollower().followPath(pathChain,true);
        }
        isBusy = true;
    }
    @Override
    public void execute(){
        follower.getFollower().update();
    }

    @Override
    public boolean isFinished(){
        isBusy = follower.getFollower().isBusy();
        if (!isBusy){
            if (!timer.isTimerOn()) timer.start();
        } else {
            timer.pause();
        }

        if (timer.done()){
            return true;
        }
        return  false;
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
