/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import drawing.Canvas;

/**
 *
 * @author david
 * defines an interface for the objects being drawn to the scene
 * draw will draw the objects,
 * update will update their current state.
 */
public interface scene_object {
    public void draw(Canvas canvas);
    public void update(Canvas canvas);
}
