# -*- coding: utf-8 -*-
"""
Created on Thu May 16 12:51:43 2019

@author: Michael
"""

from math import exp
from math import sin
from math import pow

def f(x,y):
    z=0.000
    z = exp(sin(50.0*x)) + sin(60.0*exp(y)) + sin(80.0*sin(x)) + sin(sin(70.0*y)) - sin(10.0*(x+y)) + (x*x+y*y)/4.0
    return z

counter=.001
print(str(counter))
x=-1.000
xmin=-1.000
y=-1.000
ymin=-1.000
f_min=0.000
minFinal=-0.00001
while(x<=1.000):
    x=x+counter
    print(x)
    y=-1.000
    #print('in loop x'+str(x))
    while(y<=1.000):
        y=y+counter
        f_min=f(x,y)
        #print(str(f_min))
        if(f_min<minFinal):
            minFinal=f_min
            xmin=x
            ymin=y
print(str(counter))
print('Z Min: '+str(minFinal)+'  X Min: '+str(xmin)+'  Y Min: '+str(ymin))
x_min=xmin
y_min=ymin

z_min=0
x_save=x_min
y_save=y_min
digits=1
#z_min=minFinal
while(digits<16):
    counter=1/(pow(10,digits))
    print('counter'+str(counter))
    digits=digits+1
    x_limit_right=x_save+(counter*10)
    y_limit_top=y_save+(counter*10)
    x_limit_left=x_save-(counter*10)
    y_limit_bot=y_save-(counter*10)
    x=x_limit_left
    while(x<=x_limit_right):
        x=x+counter
        y=y_limit_bot
        while(y<=y_limit_top):
            y=y+counter
            z=f(x,y)
            if(z<z_min):
                z_min=z
                xmin=x
                ymin=y
        x_save=xmin
        y_save=ymin
    print(str(z_min))
    #print('digit: '+str(digits)+'  z_min: '+str(z_min)+' x_min: '+str(xmin)+' y_min: '+str(ymin))
    x=xmin
    y=ymin
    
print("Hello, World!")

#Z Min: -3.117978604484046  X Min: 0.47000000000000103  Y Min: -0.9199999999999999
#x_min=0.47
#y_min=-0.92