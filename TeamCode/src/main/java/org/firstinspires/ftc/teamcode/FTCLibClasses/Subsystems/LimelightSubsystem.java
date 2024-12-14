package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.NoSampleFoundException;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

import java.util.List;

public class LimelightSubsystem extends SubsystemBase {

    private Limelight3A limelight3A;
    private double distToGround = 1;
    private double degreeAngleToGround = 45;


    //TODO: Fill This pose in with the right coordinates relative to the BOT not the camera
    private Pose crossHairPoseOnGround = new Pose();
    private Supplier<Pose> robotPose;


    public LimelightSubsystem(HardwareMap hMap, Supplier<Pose> poseSupplier){
        limelight3A = hMap.get(Limelight3A.class,"limelight");
        limelight3A.setPollRateHz(100);
        limelight3A.start();
        this.robotPose = poseSupplier;
    }

    public Pose processResults() throws NoSampleFoundException {
        LLResult result = limelight3A.getLatestResult();
        double xDist,yDist;
        List<LLResultTypes.ColorResult> colorResults = limelight3A.getLatestResult().getColorResults();

        Pose poseToRet=null;

        for (LLResultTypes.ColorResult colorResult:colorResults){
            Pose3D pose3D = colorResult.getTargetPoseCameraSpace();

            double adjustedCameraY = pose3D.getPosition().y*Math.sin(Math.toRadians(degreeAngleToGround));
            double cameraX = pose3D.getPosition().x;

            //radians
            double heading = robotPose.get().getHeading();

            //Both in pedro pathing axes
            double cameraContributionToGlobalX;


            Pose realPose = new Pose(
                    robotPose.get().getX()+adjustedCameraY,
                    robotPose.get().getY()+cameraX
            );

            if (realPose.getX()<-30){
                break;
            }
            poseToRet = realPose;
        }
        if (poseToRet==null){
            throw new NoSampleFoundException();
        }
        return poseToRet;

//        xDist = distToGround*Math.atan(Math.toRadians(result.getTx()));
//
//        double groundDistYAngle = 180-degreeAngleToGround-result.getTy();
//
//        /* Use Law of Sines:
//        opposite angle of dist to ground and dist to ground
//        ty and y dist
//
//        ydist/sin(ty) = distToGround/sin(groundDistYAngle)
//
//         */
//        yDist = Math.sin(Math.toRadians(result.getTy()))*distToGround/(Math.sin(Math.toRadians(groundDistYAngle)));
//        Pose crossHairRelative = new Pose(xDist,yDist);
//
//        //Switch X and Y and invert the destination Y for Pedro Pathing
//        return new Pose(
//                crossHairRelative.getY()+crossHairPoseOnGround.getY(),
//                -(crossHairRelative.getX()+crossHairPoseOnGround.getX())
//
//        );
    }
}
