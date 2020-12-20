package de.relluem94.vulcan.toolbox.maths;

public class CollisionMath {

	//
	//
	// This file was build from the Ray Plane Collision tutorial.
	// We added 5 new functions to this math file:  
	//
	// This returns the dot product between 2 vectors
	// float Dot(CVector3 vVector1, CVector3 vVector2);
	//
	// This returns the angle between 2 vectors
	// double AngleBetweenVectors(CVector3 Vector1, CVector3 Vector2);
	//
	// This returns an intersection point of a polygon and a line (assuming intersects the plane)
	// CVector3 IntersectionPoint(CVector3 vNormal, CVector3 vLine[], double distance);
	//
	// This returns true if the intersection point is inside of the polygon
	// bool InsidePolygon(CVector3 vIntersection, CVector3 Poly[], long verticeCount);
	//
	// Use this function to test collision between a line and polygon
	// bool IntersectedPolygon(CVector3 vPoly[], CVector3 vLine[], int verticeCount);
	//
	// These will enable to check if we internet not just the plane of a polygon,
	// but the actual polygon itself.  Once the line is outside the permiter, it will fail
	// on a collision test.
	//
	//
	/////// * /////////// * /////////// * NEW * /////// * /////////// * /////////// *

	public final static double PI 		= 3.1415926535897932;
	public final static double TWO_PI 	= 6.2831853071795864;
	
		
	Vector3f vNormal;
	//public Fl originDistance;
	
	public CollisionMath(){
		
	}
	
	//////////////////////////////CLOSEST POINT ON LINE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This returns the point on the line vA_vB that is closest to the point vPoint
	/////
	////////////////////////////// CLOSEST POINT ON LINE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	
	public Vector3f closestPointOnLine(Vector3f vA, Vector3f vB, Vector3f point)
	{
		// Create the vector from end point vA to our point vPoint.
		Vector3f vectorVAtoPoint = VectorMath.subtract(point, vA);
	
		// Create a normalized direction vector from end point vA to end point vB
	    Vector3f vectorVAtoVB = VectorMath.subtract(vB, vA);
	    VectorMath.normalize(vectorVAtoVB);
	
		// Use the distance formula to find the distance of the line segment (or magnitude)
	    float d = VectorMath.distance(vA, vB);
	
		// Using the dot product, we project the vectorVAtoPoint onto the vector vectorVAtoVB.
		// This essentially gives us the distance from our projected vector from vA.
	    float t = VectorMath.getDotProduct(vectorVAtoVB, vectorVAtoPoint);
	
		// If our projected distance from vA, "t", is less than or equal to 0, it must
		// be closest to the end point vA.  We want to return this end point.
	    if (t <= 0) 
			return vA;
	
		// If our projected distance from vA, "t", is greater than or equal to the magnitude
		// or distance of the line segment, it must be closest to the end point vB.  So, return vB.
	    if (t >= d) 
			return vB;
	 
		// Here we create a vector that is of length t and in the direction of vectorVAtoVB
	    Vector3f vectorT = VectorMath.multiply(vectorVAtoVB, t);
	
		// To find the closest point on the line segment, we just add vectorT to the original
		// end point vA.  
	    Vector3f closestPoint = VectorMath.add(vA, vectorT);
	
		// Return the closest point on the line segment
		return closestPoint;
	}

	
	/////////////////////////////////// PLANE DISTANCE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This returns the distance between a plane and the origin
	/////
	/////////////////////////////////// PLANE DISTANCE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
										
	private float planeDistance(Vector3f normal, Vector3f point)
	{	
		float distance = 0;		// This variable holds the distance from the plane to he origin

		// Use the plane equation to find the distance (Ax + By + Cz + D = 0)  We want to find D.
		// So, we come up with D = -(Ax + By + Cz)
		// Basically, the negated dot product of the normal of the plane and the point. 
		distance = - VectorMath.getDotProduct(normal, point);

		return distance;									// Return the distance
	}




	/////// * /////////// * /////////// * NEW * /////// * /////////// * /////////// *

	// Since the last tutorial, we added 2 more parameters for the normal and the distance
	// from the origin.  This is so we don't have to recalculate it 3 times in our IntersectionPoint() 
	// IntersectedPolygon() functions.  We would probably make 2 different functions for
	// this so we have the choice of getting the normal and distance back, or not.
	// I also changed the vTriangle to "vPoly" because it isn't always a triangle.
	// The code doesn't change, it's just more correct (though we only need 3 points anyway).
	// For C programmers, the '&' is called a reference and is the same concept as the '*' for addressing.


	/////////////////////////////////// INTERSECTED PLANE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This checks to see if a line intersects a plane
	/////
	/////////////////////////////////// INTERSECTED PLANE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
												
	private boolean intersectedPlane(Vector3f poly[], Vector3f line[], Vector3f normal, Distance originDistance)
	{
		float distance1=0, distance2=0;					// The distances from the 2 points of the line from the plane
		Vector3f temp = VectorMath.normal(poly);		
		normal.setTo(temp); 							// We need to get the normal of our plane to go any further

		// Let's find the distance our plane is from the origin.  We can find this value
		// from the normal to the plane (polygon) and any point that lies on that plane (Any vertice)
		originDistance.distance = planeDistance(normal, poly[0]);

		// Get the distance from point1 from the plane using: Ax + By + Cz + D = (The distance from the plane)

		distance1 = ((normal.x * line[0].x)  +							// Ax +
			         (normal.y * line[0].y)  +							// Bx +
					 (normal.z * line[0].z)) + originDistance.distance;	// Cz + D
		
		// Get the distance from point2 from the plane using Ax + By + Cz + D = (The distance from the plane)
		
		distance2 = ((normal.x * line[1].x)  +							// Ax +
			         (normal.y * line[1].y)  +							// Bx +
					 (normal.z * line[1].z)) + originDistance.distance;	// Cz + D

		// Now that we have 2 distances from the plane, if we times them together we either
		// get a positive or negative number.  If it's a negative number, that means we collided!
		// This is because the 2 points must be on either side of the plane (IE. -1 * 1 = -1).

		if(distance1 * distance2 >= 0)			// Check to see if both point's distances are both negative or both positive
		   return false;						// Return false if each point has the same sign.  -1 and 1 would mean each point is on either side of the plane.  -1 -2 or 3 4 wouldn't...
						
		return true;							// The line intersected the plane, Return TRUE
	}


	
	
	/////////////////////////////////// INTERSECTION POINT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This returns the intersection point of the line that intersects the plane
	/////
	/////////////////////////////////// INTERSECTION POINT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
												
	private Vector3f intersectionPoint(Vector3f normal, Vector3f line[], double distance)
	{
		Vector3f point = new Vector3f(); 		// Variables to hold the point and the line's direction
		Vector3f lineDir = new Vector3f();
		double numerator = 0.0, denominator = 0.0, dist = 0.0;

		// Here comes the confusing part.  We need to find the 3D point that is actually
		// on the plane.  Here are some steps to do that:
		
		// 1)  First we need to get the vector of our line, Then normalize it so it's a length of 1
		lineDir = VectorMath.subtract(line[1], line[0]);		// Get the Vector of the line
		VectorMath.normalize(lineDir);				// Normalize the lines vector


		// 2) Use the plane equation (distance = Ax + By + Cz + D) to find the distance from one of our points to the plane.
		//    Here I just chose a arbitrary point as the point to find that distance.  You notice we negate that
		//    distance.  We negate the distance because we want to eventually go BACKWARDS from our point to the plane.
		//    By doing this is will basically bring us back to the plane to find our intersection point.
		numerator = - (normal.x * line[0].x +		// Use the plane equation with the normal and the line
					   normal.y * line[0].y +
					   normal.z * line[0].z + distance);

		// 3) If we take the dot product between our line vector and the normal of the polygon,
		//    this will give us the cosine of the angle between the 2 (since they are both normalized - length 1).
		//    We will then divide our Numerator by this value to find the offset towards the plane from our arbitrary point.
		denominator = VectorMath.getDotProduct(normal, lineDir);		// Get the dot product of the line's vector and the normal of the plane
					  
		// Since we are using division, we need to make sure we don't get a divide by zero error
		// If we do get a 0, that means that there are INFINATE points because the the line is
		// on the plane (the normal is perpendicular to the line - (Normal.Vector = 0)).  
		// In this case, we should just return any point on the line.

		if( denominator == 0.0)						// Check so we don't divide by zero
			return line[0];						// Return an arbitrary point on the line

		// We divide the (distance from the point to the plane) by (the dot product)
		// to get the distance (dist) that we need to move from our arbitrary point.  We need
		// to then times this distance (dist) by our line's vector (direction).  When you times
		// a scalar (single number) by a vector you move along that vector.  That is what we are
		// doing.  We are moving from our arbitrary point we chose from the line BACK to the plane
		// along the lines vector.  It seems logical to just get the numerator, which is the distance
		// from the point to the line, and then just move back that much along the line's vector.
		// Well, the distance from the plane means the SHORTEST distance.  What about in the case that
		// the line is almost parallel with the polygon, but doesn't actually intersect it until half
		// way down the line's length.  The distance from the plane is short, but the distance from
		// the actual intersection point is pretty long.  If we divide the distance by the dot product
		// of our line vector and the normal of the plane, we get the correct length.  Cool huh?

		dist = numerator / denominator;				// Divide to get the multiplying (percentage) factor
		
		// Now, like we said above, we times the dist by the vector, then add our arbitrary point.
		// This essentially moves the point along the vector to a certain distance.  This now gives
		// us the intersection point.  Yay!

		point.x = (float) (line[0].x + (lineDir.x * dist));
		point.y = (float) (line[0].y + (lineDir.y * dist));
		point.z = (float) (line[0].z + (lineDir.z * dist));

		return point;								// Return the intersection point
	}


	/////////////////////////////////// INSIDE POLYGON \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This checks to see if a point is inside the ranges of a polygon
	/////
	/////////////////////////////////// INSIDE POLYGON \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*

	public boolean insidePolygon(Vector3f intersection, Vector3f poly[], int verticeCount)
	{
		final double MATCH_FACTOR = 0.9999;		// Used to cover up the error in floating point
		double angle = 0.0;						// Initialize the angle
		Vector3f vA, vB;						// Create temp vectors
		
		// Just because we intersected the plane, doesn't mean we were anywhere near the polygon.
		// This functions checks our intersection point to make sure it is inside of the polygon.
		// This is another tough function to grasp at first, but let me try and explain.
		// It's a brilliant method really, what it does is create triangles within the polygon
		// from the intersection point.  It then adds up the inner angle of each of those triangles.
		// If the angles together add up to 360 degrees (or 2 * PI in radians) then we are inside!
		// If the angle is under that value, we must be outside of polygon.  To further
		// understand why this works, take a pencil and draw a perfect triangle.  Draw a dot in
		// the middle of the triangle.  Now, from that dot, draw a line to each of the vertices.
		// Now, we have 3 triangles within that triangle right?  Now, we know that if we add up
		// all of the angles in a triangle we get 360 right?  Well, that is kinda what we are doing,
		// but the inverse of that.  Say your triangle is an isosceles triangle, so add up the angles
		// and you will get 360 degree angles.  90 + 90 + 90 is 360.

		for (int i = 0; i < verticeCount; i++)		// Go in a circle to each vertex and get the angle between
		{	
			vA = VectorMath.subtract(poly[i], intersection);	// Subtract the intersection point from the current vertex
													// Subtract the point from the next vertex
			vB = VectorMath.subtract(poly[(int)((i + 1) % verticeCount)], intersection);
													
			angle += VectorMath.angleBetweenVectors(vA, vB);	// Find the angle between the 2 vectors and add them all up as we go along
		}

		// Now that we have the total angles added up, we need to check if they add up to 360 degrees.
		// Since we are using the dot product, we are working in radians, so we check if the angles
		// equals 2*PI.  We defined PI in 3DMath.h.  You will notice that we use a MATCH_FACTOR
		// in conjunction with our desired degree.  This is because of the inaccuracy when working
		// with floating point numbers.  It usually won't always be perfectly 2 * PI, so we need
		// to use a little twiddling.  I use .9999, but you can change this to fit your own desired accuracy.
													
		if(angle >= (MATCH_FACTOR * (TWO_PI)) )	// If the angle is greater than 2 PI, (360 degrees)
			return true;							// The point is inside of the polygon
			
		return false;								// If you get here, it obviously wasn't inside the polygon, so Return FALSE
	}


	/////////////////////////////////// INTERSECTED POLYGON \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This checks if a line is intersecting a polygon
	/////
	/////////////////////////////////// INTERSECTED POLYGON \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*

	public boolean intersectedPolygon(Vector3f poly[], Vector3f line[], int verticeCount, Vector3f intersectionPoint)
	{
		
		Distance originDistance = new Distance();
		
		// First we check to see if our line intersected the plane.  If this isn't true
		// there is no need to go on, so return false immediately.
		// We pass in address of vNormal and originDistance so we only calculate it once
		vNormal = new Vector3f();
										 // Reference   // Reference
		if(!intersectedPlane(poly, line, vNormal, originDistance))
			return false;

		// Now that we have our normal and distance passed back from IntersectedPlane(), 
		// we can use it to calculate the intersection point.  The intersection point
		// is the point that actually is ON the plane.  It is between the line.  We need
		// this point test next, if we are inside the polygon.  To get the I-Point, we
		// give our function the normal of the plan, the points of the line, and the originDistance.

		intersectionPoint.setTo(intersectionPoint(vNormal, line, originDistance.distance));

		// Now that we have the intersection point, we need to test if it's inside the polygon.
		// To do this, we pass in :
		// (our intersection point, the polygon, and the number of vertices our polygon has)

		if(insidePolygon(intersectionPoint, poly, verticeCount))
			return true;							// We collided!	  Return success


		// If we get here, we must have NOT collided

		return false;								// There was no collision, so return false
	}

	///////////////////////////////// CLASSIFY SPHERE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This tells if a sphere is BEHIND, in FRONT, or INTERSECTS a plane, also it's distance
	/////
	///////////////////////////////// CLASSIFY SPHERE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	
	public int classifySphere(Vector3f center, Vector3f normal, Vector3f point, float radius, Distance distance)
	{
		// First we need to find the distance our polygon plane is from the origin.
		float d = planeDistance(normal, point);
	
		// Here we use the famous distance formula to find the distance the center point
		// of the sphere is from the polygon's plane.  
		distance.distance = (normal.x * center.x + normal.y * center.y + normal.z * center.z + d);
	
		// Now we query the information just gathered.  Here is how Sphere Plane Collision works:
		// If the distance the center is from the plane is less than the radius of the sphere,
		// we know that it must be intersecting the plane.  We take the absolute value of the
		// distance when we do this check because once the center of the sphere goes behind
		// the polygon, the distance turns into negative numbers (with 0 being that the center
		// is exactly on the plane).  What do I mean when I say "behind" the polygon?  How do
		// we know which side is the front or back side?  Well, the side with the normal pointing
		// out from it is the front side, the other side is the back side.  This is all dependant
		// on the direction the vertices stored.  I recommend drawing them counter-clockwise.
		// if you go clockwise the normal with then point the opposite way and will screw up
		// everything.
		// So, if we want to find if the sphere is in front of the plane, we just make sure
		// the distance is greater than or equal to the radius.  let's say we have a radius
		// of 5, and the distance the center is from the plane is 6; it's obvious that the
		// sphere is 1 unit away from the plane.
		// If the sphere isn't intersecting or in front of the plane, it HAS to be BEHIND it.
	
		// If the absolute value of the distance we just found is less than the radius, 
		// the sphere intersected the plane.
		if(Math.abs(distance.distance) < radius)
			return Distance.INTERSECTS;
		// Else, if the distance is greater than or equal to the radius, the sphere is
		// completely in FRONT of the plane.
		else if(distance.distance >= radius)
			return Distance.FRONT;
		
		// If the sphere isn't intersecting or in FRONT of the plane, it must be BEHIND
		return Distance.BEHIND;
	}


	///////////////////////////////// EDGE SPHERE COLLSIION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This returns true if the sphere is intersecting any of the edges of the polygon
	/////
	///////////////////////////////// EDGE SPHERE COLLSIION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	
	public boolean edgeSphereCollision(Vector3f center, Vector3f polygon[], int vertexCount, float radius)
	{
		Vector3f point = new Vector3f();
	
		// This function takes in the sphere's center, the polygon's vertices, the vertex count
		// and the radius of the sphere.  We will return true from this function if the sphere
		// is intersecting any of the edges of the polygon.  
	
		// Go through all of the vertices in the polygon
		for(int i = 0; i < vertexCount; i++)
		{
			// This returns the closest point on the current edge to the center of the sphere.
			point.setTo(closestPointOnLine(polygon[i], polygon[(i + 1) % vertexCount], center));
			
			// Now, we want to calculate the distance between the closest point and the center
			float distance = VectorMath.distance(point, center);
	
			// If the distance is less than the radius, there must be a collision so return true
			if(distance < radius)
				return true;
		}
	
		// The was no intersection of the sphere and the edges of the polygon
		return false;
	}
	
	
	////////////////////////////// SPHERE POLYGON COLLISION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This returns true if our sphere collides with the polygon passed in
	/////
	////////////////////////////// SPHERE POLYGON COLLISION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	
	public boolean spherePolygonCollision(Vector3f polygon[], Vector3f center, int vertexCount, float radius)
	{
		// 1) STEP ONE - Finding the sphere's classification
		
		// Let's use our Normal() function to return us the normal to this polygon
		Vector3f normal = VectorMath.normal(polygon);
	
		// This will store the distance our sphere is from the plane
		Distance distance = new Distance();
	
		// This is where we determine if the sphere is in FRONT, BEHIND, or INTERSECTS the plane
		int classification = classifySphere(center, normal, polygon[0], radius, distance);
	
		// If the sphere intersects the polygon's plane, then we need to check further
		if(classification == Distance.INTERSECTS) 
		{
			// 2) STEP TWO - Finding the pseudo intersection point on the plane
	
			// Now we want to project the sphere's center onto the polygon's plane
			Vector3f offset = VectorMath.multiply(normal, distance.distance);
	
			// Once we have the offset to the plane, we just subtract it from the center
			// of the sphere.  "vPosition" now a point that lies on the plane of the polygon.
			Vector3f position = VectorMath.subtract(center,  offset);
	
			// 3) STEP THREE - Check if the intersection point is inside the polygons perimeter
	
			// If the intersection point is inside the perimeter of the polygon, it returns true.
			// We pass in the intersection point, the list of vertices and vertex count of the poly.
			if(insidePolygon(position, polygon, 3))
				return true;	// We collided!
			else
			{
				// 4) STEP FOUR - Check the sphere intersects any of the polygon's edges
	
				// If we get here, we didn't find an intersection point in the perimeter.
				// We now need to check collision against the edges of the polygon.
				if(edgeSphereCollision(center, polygon, vertexCount, radius))
				{
					return true;	// We collided!
				}
			}
		}
	
		// If we get here, there is obviously no collision
		return false;
	}


	/////// * /////////// * /////////// * NEW * /////// * /////////// * /////////// *
	
	///////////////////////////////// GET COLLISION OFFSET \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	/////
	/////	This returns the offset to move the center of the sphere off the collided polygon
	/////
	///////////////////////////////// GET COLLISION OFFSET \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*
	
	public Vector3f getCollisionOffset(Vector3f normal, float radius, float distance)
	{
		Vector3f offset = new Vector3f();
	
		// Once we find if a collision has taken place, we need make sure the sphere
		// doesn't move into the wall.  In our app, the position will actually move into
		// the wall, but we check our collision detection before we render the scene, which
		// eliminates the bounce back effect it would cause.  The question is, how do we
		// know which direction to move the sphere back?  In our collision detection, we
		// account for collisions on both sides of the polygon.  Usually, you just need
		// to worry about the side with the normal vector and positive distance.  If 
		// you don't want to back face cull and have 2 sided planes, I check for both sides.
		//
		// Let me explain the math that is going on here.  First, we have the normal to
		// the plane, the radius of the sphere, as well as the distance the center of the
		// sphere is from the plane.  In the case of the sphere colliding in the front of
		// the polygon, we can just subtract the distance from the radius, then multiply
		// that new distance by the normal of the plane.  This projects that leftover
		// distance along the normal vector.  For instance, say we have these values:
		//
		//	vNormal = (1, 0, 0)		radius = 5		distance = 3
		//
		// If we subtract the distance from the radius we get: (5 - 3 = 2)
		// The number 2 tells us that our sphere is over the plane by a distance of 2.
		// So basically, we need to move the sphere back 2 units.  How do we know which
		// direction though?  This part is easy, we have a normal vector that tells us the
		// direction of the plane.  
		// If we multiply the normal by the left over distance we get:  (2, 0, 0)
		// This new offset vectors tells us which direction and how much to move back.
		// We then subtract this offset from the sphere's position, giving is the new
		// position that is lying right on top of the plane.  Ba da bing!
		// If we are colliding from behind the polygon (not usual), we do the opposite
		// signs as seen below:
		
		// If our distance is greater than zero, we are in front of the polygon
		if(distance > 0.001)
		{
			// Find the distance that our sphere is overlapping the plane, then
			// find the direction vector to move our sphere.
			float distanceOver = radius - distance;
			offset.setTo(VectorMath.multiply(normal, distanceOver));
		}
		else // Else colliding from behind the polygon
		{
			// Find the distance that our sphere is overlapping the plane, then
			// find the direction vector to move our sphere.
			float distanceOver = radius + distance;
			offset.setTo(VectorMath.multiply(normal, -(distanceOver)));
		}
	
		// There is one problem with check for collisions behind the polygon, and that
		// is if you are moving really fast and your center goes past the front of the
		// polygon, it will then assume you were colliding from behind and not let
		// you back in.  Most likely you will take out the if / else check, but I
		// figured I would show both ways in case someone didn't want to back face cull.
	
		// Return the offset we need to move back to not be intersecting the polygon.
		return offset;
	}
		
		
}