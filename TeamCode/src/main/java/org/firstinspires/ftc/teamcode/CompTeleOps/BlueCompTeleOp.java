package org.firstinspires.ftc.teamcode.CompTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AllianceColor;

@TeleOp(name = "Blue Comp TeleOp",group = "Comp")
public class BlueCompTeleOp extends CompTeleOpTemplate{

    @Override
    public void setAllianceColor(){
        allianceColor = AllianceColor.BLUE;
    }
}
