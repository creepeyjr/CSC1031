#!/usr/bin/env python3

'''
Understanding Brief
----

- Four inputs
    - Coords : x, y, z
    - label

- distance_to(self, other)
    - Calculate Euclidean distance between current dot and another dot.
    - Return computed distance.

- add_vector(self, other)
    - VECTOR ADDITION : Add current dot's coords to another dot.
    - Return new Dot3D instance with result coords and label.
    containing both initial labels.
'''

class Dot3D(object):
    def __init__(self, x, y, z, label=None):
        self.x = x
        self.y = y
        self.z = z
        self.label = label

    def distance_to(self, other):
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

    def add_vector(self, other):
        return Dot3D((self.x + other.x), (self.y + other.y), (self.z + other.z), label=f"{self.label}+{other.label}")
dot1 = Dot3D(3, 4, 5, "x1")
dot2 = Dot3D(6, 8, 10, "x2")

distance = dot1.distance_to(dot2)
#print(distance)  # 7.0710678118654755

resultant = dot1.add_vector(dot2)
#print(resultant)  # returns Dot3D(9, 12, 15, label="x1+x2")