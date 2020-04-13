/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import geometry.CartesianCoordinate;
import geometry.LineSegment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author david
 */
public final class flockingBird extends animal {

    double cohesion;
    /* Cohesion of bird. It is between 1 and 0, 1 being full cohesion */
    double alignment;
    /* Contains the alignment of the bird */
    double separation;

    List<flockingBird> animals_in_range;

    /* Separation of bird. It is between 1 and 0, 1 being full separation */
    public flockingBird(double xLoc, double yLoc, double newcohesion, double newalignment, double newseparation) {
        name = "flocking bird";
        int temp_speed = 0;
        while (temp_speed < 5) {
            temp_speed = r.nextInt(100);
        }
        speed = temp_speed;
        angle = 0; //-Math.PI/2; //Default face upwards
        angular_velocity = 0;
        position = new CartesianCoordinate(xLoc, yLoc);
        view_angle = Math.PI;
        view_radius = 100;
        try{
        image = ImageIO.read(new File("flock.png"));
        }catch(IOException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();
        }        
        set_cohesion(newcohesion);
        set_alignment(newalignment);
        set_separation(newseparation);
    }

    public void set_cohesion(double ammount) {
        if (ammount <= 1) {
            cohesion = ammount;
        } else if (ammount > 1) {
            System.out.println("Hey, so your Cohesion should be between 0 and 1, yours is >1. Would you check it please? I've set it to 1 for now.");
            cohesion = 1;
        } else if (ammount < -1) {
            System.out.println("Hey, so your Cohesion should be between 0 and 1, yours is <0. Would you check it please? I've set it to 0 for now.");
            cohesion = 0;
        }
    }

    public void set_separation(double ammount) {
        if (ammount <= 1) {
            separation = ammount;
        } else if (ammount > 1) {
            System.out.println("Hey, so your separation should be between 0 and 1, yours is >1. Would you check it please? I've set it to 1 for now.");
            separation = 1;
        } else if (ammount < -1) {
            System.out.println("Hey, so your separation should be between 0 and 1, yours is <0. Would you check it please? I've set it to 0 for now.");
            separation = 0;
        }
    }

    private void wrap(double canvas_X, double canvas_Y) {
        //Code to wrap animal to other side of window
        if (position.getX() > canvas_X) {
            set_position(new CartesianCoordinate(0, position.getY()));
        } else if (position.getX() < 0) {
            set_position(new CartesianCoordinate(canvas_X, position.getY()));
        }
        if (position.getY() > canvas_Y) {
            set_position(new CartesianCoordinate(position.getX(), 0));
        } else if (position.getY() < 0) {
            set_position(new CartesianCoordinate(position.getX(), canvas_Y));
        }
    }

    private void line_of_sight(List<flockingBird> flock) {
        animals_in_range = new ArrayList<>();
        for (flockingBird bird : flock) {
            
            if (bird != this) {
                double distance = CartesianCoordinate.dist_between(position, bird.get_position());
                if (distance <= 0 || distance > view_radius) {
                    continue;
                }
                double angle_between = CartesianCoordinate.angle_between(bird.get_position(), this.position);
                System.out.printf("between: %f facing %f \n", angle_between, angle);
                if (angle_between > ((view_angle / 2) - angle) || angle_between < ((view_angle / 2) + angle)) {
                    animals_in_range.add(bird);
                }
            }
        }
    }

    private double avoid() {
        double desiredSeparation = 25;
        double newAngle = 0;
        int count = 0;
        for (flockingBird bird : animals_in_range) {

            double distance = CartesianCoordinate.dist_between(position, bird.get_position());
            if ((distance > 0) && (distance < desiredSeparation)) {
                newAngle += CartesianCoordinate.angle_between(position, bird.get_position()) / distance;
                count++;
            }
        }
        if (count > 0) {
            newAngle /= count;
            return newAngle;
        } else {
            return 0;
        }

    }

    public void navigate(List<flockingBird> flock, double canvas_X, double canvas_Y) {
        /*wrap(canvas_X, canvas_Y);
        line_of_sight(flock);
        double averageX = 0;
        double averageY = 0;
        double averageAng = 0;
        double newAng;
        for (flockingBird bird : animals_in_range) {

            averageX += bird.get_position().getX();
            averageY += bird.get_position().getY();
            averageAng += bird.get_angle();
            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());

            //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
            averageX = averageX / (animals_in_range.size());
            averageY = averageY / (animals_in_range.size());
            averageAng = averageAng / (animals_in_range.size());
            set_local_COM(new CartesianCoordinate(averageX, averageY));
            //newAng = Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX) * member.cohesion();
            newAng = CartesianCoordinate.angle_between(position, new CartesianCoordinate(averageX, averageY)) * cohesion;
            newAng = newAng + ((averageAng - newAng) * alignment); //Sum of centre of mass angle, and average angle*alignment factor.
            //System.out.printf("newangle %f \n", newAng);
            set_angle(newAng + avoid()); //If alignment == 1, then the angle here will equal the average angle.

        }
        CartesianCoordinate new_pos = Get_New_Position();
        set_position(new_pos);*/
        set_angle(angle+0.01);
        CartesianCoordinate new_pos = Get_New_Position();
        set_position(new_pos);
    }

    public double cohesion() {
        return cohesion;
    }

    public void set_alignment(double ammount) {
        if (ammount <= 1) {
            alignment = ammount;
        } else if (ammount > 1) {
            System.out.println("Hey, so your Alignment should be between -1 and 1, yours is >1. Would you check it please? I've set it to 1 for now.");
            alignment = 1;
        } else if (ammount < 0) {
            System.out.println("Hey, so your Alignment should be between 0 and 1, yours is <-1. Would you check it please? I've set it to 0 for now.");
            alignment = 0;
        }
    }

    public double alignment() {
        return alignment;
    }

}
