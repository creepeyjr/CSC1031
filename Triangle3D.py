#!/usr/bin/env python3
'''
Understanding Brief
-------------------

Create a class called Triangle3D.

The class should take three Dot3D objects as parameters, representing the triangle’s vertices.

Implement the following methods:
- calculate_perimeter(self)
    - Compute and return the perimeter of the triangle.
- calculate_area(self)
    - Compute and return the area of the triangle using Heron’s formula.
'''

class Dot3D(object):
    def __init__(self, x, y, z, label=None):
        self.x = x
        self.y = y
        self.z = z
        self.label = label

    def create_edge(self, other):
        # Grab coords of first dot
        x1 = self.x
        y1 = self.y
        z1 = self.z
        #print(f"label: {self.label} x1: {x1} y1: {y1} z1: {z1}")

        # Grab coords of second dot
        x2 = other.x
        y2 = other.y
        z2 = other.z

        return ((x2 - x1)**2 + (y2 - y1)**2 + (z2 - z1)**2)**0.5


class Triangle3D(object):
    def __init__(self, dot1, dot2, dot3):
        self.dot1 = dot1
        self.dot2 = dot2
        self.dot3 = dot3

    def calculate_perimeter(self):
        # Calculate the edges
        e1 = Dot3D.create_edge(dot1, dot2)
        e2 = Dot3D.create_edge(dot2, dot3)
        e3 = Dot3D.create_edge(dot1, dot3)

        #print(f"e1: {e1}")
        #print(f"e2: {e2}")
        #print(f"e3: {e3}")

        # Calculate and return the perimeter of the triangle
        return e1 + e2 + e3

    def calculate_area(self):
        # Calculate the edges
        e1 = Dot3D.create_edge(dot1, dot2)
        e2 = Dot3D.create_edge(dot2, dot3)
        e3 = Dot3D.create_edge(dot1, dot3)

        # Calculate and return the area of the triangle
        s = (e1 + e2 + e3) / 2
        return (s * (s - e1) * (s - e2) * (s - e3))**0.5



# Test Cases
# ----------

dot1 = Dot3D(0, 0, 0)
dot2 = Dot3D(3, 0, 0)
dot3 = Dot3D(0, 4, 0)

triangle = Triangle3D(dot1, dot2, dot3)

# Calculate perimeter
perimeter = triangle.calculate_perimeter()
#print(perimeter)  # Expected: 12.0

# Calculate area
area = triangle.calculate_area()
#print(area)  # Expected: 6.0