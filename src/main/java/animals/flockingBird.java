/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import geometry.CartesianCoordinate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import scene.obstacle;

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

    public flockingBird(double xLoc, double yLoc, double newcohesion, double newalignment, double newseparation) {
        name = "flocking bird";
        int temp_speed = 0;
        while (temp_speed < 50) {
            temp_speed = r.nextInt(200);
        }
        speed = temp_speed;
        target_angle = 0;
        direction_angle = 0;
        angular_velocity = 8;
        position = new CartesianCoordinate(xLoc, yLoc);
        view_radius = 100;
        local_centre = new CartesianCoordinate(xLoc, yLoc);
        bouncing = 0;
        try {
            image = ImageIO.read(new File("flock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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


    private void line_of_sight(List<flockingBird> flock) {
        animals_in_range = new ArrayList<>();
        for (flockingBird bird : flock) {

            if (true) {
                double distance = CartesianCoordinate.dist_between(position, bird.get_position());
                if (distance > view_radius) {
                    continue;
                }
                animals_in_range.add(bird);
                //}
            }
        }
    }

    private double avoid(double AverageX, double AverageY) {
        double ret_ang = ((((CartesianCoordinate.angle_between(position, new CartesianCoordinate(AverageX, AverageY)))) - Math.PI)) * separation;
        return ret_ang;
    }

    private double cohesion(double AverageX, double AverageY) {
        double ret_ang = ((CartesianCoordinate.angle_between(position, new CartesianCoordinate(AverageX, AverageY))) * cohesion);

        return ret_ang;
    }

    private double alignment(double average_angle) {
        double ret_ang = (average_angle) * alignment;

        return ret_ang;
    }

    private void seek_angle() {
        /*
        double rad_dist = angular_velocity / 60, temp_angleA = direction_angle, temp_angleB;
        int count, sign = 1;
        int[] radial_count = new int[2];
        System.out.printf("Percent %f\n", direction_angle/target_angle);
        if (direction_angle/target_angle < 0.9 | direction_angle/target_angle > 1.1 | target_angle == 0) {
           //System.out.printf("Seeking\n");
            for (int i = 0; i < 2; i++) {
                temp_angleA = direction_angle; 
                count = 0;
                while (count <= 360) {
                    temp_angleB = temp_angleA;
                    temp_angleA += sign * 0.2;
                    if (temp_angleA > Math.PI) {
                        temp_angleA = temp_angleA - (2 * Math.PI);
                    } else if (temp_angleA < -Math.PI) {
                        temp_angleA = temp_angleA + (2 * Math.PI);
                    }
                    if ((temp_angleA > target_angle && temp_angleB < target_angle) | (temp_angleA < target_angle && temp_angleB > target_angle)) {
                        radial_count[i] = count;
                        count = 361;
                        
                    }
                    count++;
                }
                sign = sign * (-1);
            }
            System.out.printf("Seekd %d %d\n", radial_count[0], radial_count[1]);
            sign = (radial_count[0] > radial_count[1]) ? -1 : 1;
            System.out.printf("Sign: %d\n",sign);
            temp_angleA = direction_angle + (rad_dist * sign);
            if (temp_angleA > Math.PI) {
                temp_angleA -= Math.PI * 2;
            } else if (temp_angleA < -Math.PI) {
                temp_angleA += Math.PI * 2;
            }

        }*/
        set_direction_angle(target_angle);
        set_position(Get_Actual_Position());
    }

    public void navigate(List<flockingBird> flock, double canvas_X, double canvas_Y, List<obstacle> obstacles) {
        wrap(canvas_X, canvas_Y);
        line_of_sight(flock);
        double averageX = 0;
        double averageY = 0;
        double averageAng = 0;
        for (flockingBird bird : animals_in_range) {
            averageX += bird.get_position().getX();
            averageY += bird.get_position().getY();
            averageAng += bird.get_target_angle();
        }

        averageX = averageX / (animals_in_range.size());
        averageY = averageY / (animals_in_range.size());
        averageAng = averageAng / (animals_in_range.size());
        set_local_COM(new CartesianCoordinate(averageX, averageY));
        if (!bounce(obstacles)) {
            set_target_angle(cohesion(averageX, averageY) + alignment(averageAng) + avoid(averageX, averageY));
            //set_target_angle(cohesion(averageX, averageY));
            seek_angle();
            //set_direction_angle(target_angle);
            //CartesianCoordinate new_pos = Get_Actual_Position();
            //set_position(new_pos);
            //System.out.printf("Cohesion angle %f Dir angle %f\n", cohesion(averageX, averageY), direction_angle);
        }
    }

    public double get_cohesion() {
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

    public double get_alignment() {
        return alignment;
    }

}
