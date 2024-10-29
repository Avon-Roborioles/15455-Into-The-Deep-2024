package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import androidx.annotation.NonNull;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.MathFunctions;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;


public class AprilTagReader {


    private AprilTagProcessor processor;
    private VisionPortal vportal;

    private Telemetry telemetry;




    public AprilTagReader(AprilTagProcessor processor, Telemetry telemetry){
        this.processor = processor;
        this.telemetry = telemetry;
    }



//This shows the position relative to the APRIL TAG'S COORDINATE AXES, NOT THE CAMERA'S

    /**
     * Takes in a detection and returns the coordinates values WITH RESPECT TO THE APRIL TAG'S COORD SYSTEM
     * @param detection The April Tag Detection
     * @return Returns a Twist2d with the x, y, and heading with respect to the april tag's coordinate system.
     */
    private Pose findCoords(@NonNull AprilTagDetection detection) {
        double rawX = detection.ftcPose.x;
        double rawY = detection.ftcPose.y;
        //double height = detection.ftcPose.z;

        rawX=DistanceUnit.INCH.fromUnit(detection.metadata.distanceUnit,rawX);
        rawY=DistanceUnit.INCH.fromUnit(detection.metadata.distanceUnit,rawY);



        double roll =-detection.ftcPose.roll;

        double xReading = rawX;
        double yReading = cos(roll)*rawY;



        //This is in degrees
        //YAW IS THE ROTATION OF THE APRIL TAG!!! NOT THE CAMERA!!!
        double yaw = detection.ftcPose.yaw;
        double botX,botY,heading,xXCoord,yYCoord,xYCoord,yXCoord;
        double xAngle;
        if (xReading > 0) {
            xAngle = yaw + 180;
        } else {
            xAngle = yaw;
        }

        double yAngle;
        double yaw2 = yaw + 90;
        if (yReading > 0) {
            yAngle = yaw2 - 180;
        } else {
            yAngle = yaw2;
        }
        //first letter means the reading and the second letter means the contribution to that output
        xXCoord = cos(toRadians(xAngle)) * xReading;
        xYCoord = sin(toRadians(xAngle)) * xReading;
        yXCoord = cos(toRadians(yAngle)) * yReading;
        yYCoord = sin(toRadians(yAngle)) * yReading;

        heading = 90-yaw+AprilTag.getAprilTag(detection.id).heading;

        botX = xXCoord + yXCoord;
        botY = xYCoord + yYCoord;

        AprilTag tag = AprilTag.getAprilTag(detection.id);
        Pose poseToRet = new Pose(
                botY+tag.yPos,
                botX+tag.xPos,
                MathFunctions.normalizeAngle(Math.toRadians(heading+tag.heading)%360)
        );
        boolean telcond = telemetry == null;
        if (!telcond) {

            telemetry.addLine("Bot X: "+ botX +"\nBot Y: "+botY+"\nHeading: "+ yaw+ "\nUnit: "+ detection.metadata.distanceUnit);
        }
        return poseToRet;
    }

    public Pose readTag() throws NoAprilTagFoundException{
        ArrayList<AprilTagDetection> detections;
        detections=processor.getDetections();
        Pose coords = null;

//            if (detections != null ) {
        for (AprilTagDetection detection : detections) {
            if (detection.metadata != null) {
                coords = findCoords(detections.get(0));
                telemetry.addLine("======================");
                telemetry.addData("X", coords.getX());
                telemetry.addData("Y", coords.getY());
                telemetry.addData("Heading", coords.getHeading());
                telemetry.addData("ID", detection.id);
                telemetry.addData("Unit", detection.metadata.distanceUnit);
                telemetry.update();
                break;
            }
        }
        if (coords == null){
            throw new NoAprilTagFoundException();
        }
        return coords;
    }

//    public Twist2dDual<Time> readTag() throws NoAprilTagFoundException{
//
//
//        ArrayList<AprilTagDetection> detections;
//        detections=processor.getDetections();
//        Twist2d coords = null;
//
////            if (detections != null ) {
//        for (AprilTagDetection detection : detections) {
//            if (detection.metadata != null) {
//                coords = findCoords(detections.get(0));
//                telemetry.addLine("======================");
//                telemetry.addData("X", coords.line.x);
//                telemetry.addData("Y", coords.line.y);
//                telemetry.addData("Heading", coords.angle);
//                telemetry.addData("ID", detection.id);
//                telemetry.addData("Unit", detection.metadata.distanceUnit);
//                telemetry.update();
//                break;
//            }
//        }
//        //}
//
//        if (coords == null){
//            throw new NoAprilTagFoundException();
//        }
//        Vector2d vector =coords.line;
//        double heading =coords.angle;
//
//        return new Twist2dDual<>(
//                new Vector2dDual<>(
//                        new DualNum<>(new double[]{vector.y,0}),
//                        new DualNum<>(new double[]{vector.x,0})
//                ),
//                new DualNum<>(new double[]{heading,0}
//                )
//        );
//    }



    public enum AprilTag {

        ELEVEN(11,0,0,144- (double) 144 /5,0),
        TWELVE(12,0,72,144,3*PI/2),
        THIRTEEN(13,0,144,144- (double) 144 /5,3*PI/2),
        FOURTEEN(14,0,144, (double) 144 /5,PI),
        FIFTEEN(15,0,0,72,PI/2),
        SIXTEEN(16,0, (double) 144 /5,0,0);

        final double id, roll, xPos, yPos, heading;

        AprilTag(double id,double roll,double xPos,double yPos,double heading){
            this.id=id;
            this.roll=roll;
            this.xPos=xPos;
            this.yPos=yPos;
            this.heading=heading;
        }

        public static AprilTag getAprilTag(int tag){
            switch (tag){
                case 11:
                    return ELEVEN;
                case 12:
                    return TWELVE;
                case 13:
                    return THIRTEEN;
                case 14:
                    return FOURTEEN;
                case 15:
                    return FIFTEEN;
                case 16:
                    return SIXTEEN;
                default:
                    return null;
            }
        }
    }

}
