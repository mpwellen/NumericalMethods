# -*- coding: utf-8 -*-
"""
Created on Mon Mar  4 09:33:02 2019

@author: Michael
"""

import math
# test_passing_function.py
def f(x):
  return math.sin(x)

def trap_int(f, xmin, xmax, nstep): # integrate f(x) from xmin to xmax
  area=(f(xmin)+f(xmax))/2.0
  h = (xmax-xmin)/nstep
  for i in range(1,nstep):
    x = xmin+i*h
    area = area + f(x)

  return area*h # trapezoidal method

file=open("mpw_outputtrapezoidal.txt","a")
file.write("test_passing_function.py running")
file.write("test trapezoidal method")
print ("test_passing_function.py running")
print ("test trapezoidal method")
xmin = 0.0
xmax = 1.0
n = 8
while(n<128):
    n=n*2
    print("Test Trapezoidal Method for N=",n)
    file.write("Test Trapezoidal Method for N="+str(n)+"\n")
    area = trap_int(f, xmin, xmax, n)
    print ("trap_int area under sin(x) from ",xmin," to ",xmax," = ",area,"\n")
    file.write("trap+int area under sin(x) from "+str(xmin)+" to "+str(xmax)+" = "+str(area)+"\n")
    exact=1.0-math.cos(1.0)
    err=area-exact
    print("Error=",err,"\n")
    file.write("Error="+str(err)+"\n"+"\n")
file.close()


