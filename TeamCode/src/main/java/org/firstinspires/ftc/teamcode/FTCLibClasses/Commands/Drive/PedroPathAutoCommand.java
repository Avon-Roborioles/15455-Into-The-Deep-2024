package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
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
    private boolean doRoughlyEquals=false;

    Pose lastPose;


    public PedroPathAutoCommand(FollowerSubsystem follower, Path path){
        type = PathType.PATH;

        this.follower = follower;
        this.path = path;
        addRequirements(follower);
        timer = new Timing.Timer(250,TimeUnit.MILLISECONDS);
        lastPose = follower.getFollower().getPose();

    }
    public PedroPathAutoCommand(FollowerSubsystem follower, Path path,boolean doRoughlyEquals){
        type = PathType.PATH;

        this.follower = follower;
        this.path = path;
        addRequirements(follower);
        timer = new Timing.Timer(200,TimeUnit.MILLISECONDS);
        lastPose = follower.getFollower().getPose();
        this.doRoughlyEquals = doRoughlyEquals;

    }
    public PedroPathAutoCommand(FollowerSubsystem follower, Path path, int timeout, TimeUnit unit){
        type = PathType.PATH;

        this.follower = follower;
        this.path = path;
        addRequirements(follower);
        timer = new Timing.Timer(timeout,unit);
        lastPose = follower.getFollower().getPose();

    }

    public PedroPathAutoCommand(FollowerSubsystem follower, PathChain pathChain){
        this.follower = follower;
        this.pathChain = pathChain;
        addRequirements(follower);
        lastPose = follower.getFollower().getPose();

    }

    @Override
    public void initialize(){
        follower.getFollower().breakFollowing();
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
        if (!doRoughlyEquals) {
            isBusy = follower.getFollower().isBusy();
            if (!isBusy){
                if (!timer.isTimerOn()) timer.start();

            } else {
                timer.pause();
            }

            return timer.done();
        } else {
            if (lastPose.roughlyEquals(follower.getFollower().getPose(),.01)){

                return  true;
            }
            lastPose = follower.getFollower().getPose();

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
