# -*- coding: utf-8 -*-
"""
Created on Sun Mar  3 10:46:03 2019

@author: Michael
"""
import math

def f(x,y):
    return ((x-2)*(x-2)+(y-2)*(y-2))>1 #outside of circle 1

def g(x,y): #inside of circle 2
    return (x*x+(y-2)*(y-2))<4
    
def h(x,y): #inside of circle 3
    return ((x*x+y*y)<9)

file=open("mpw_dotcount.txt","a")    
stepSize=1.0 #set initia size for stepSize
step=0.0 #set initia step
while(stepSize<=100): #loop until step gets very small.
    stepSize=stepSize*10 #get step size smaller
    step=1/stepSize
    print("Step=",step)
    file.write("Step="+str(step)+"\n")
    x=-3
    sum=0
    dotCount=0
    adder=math.pow(step,2)
    while(x<=3):
        x=x+step
        y=0#start with 0, since circle F does not dip below this. 
        while(y<=3):#only check up to 4, since that's the max
            y=y+step
            if(h(x,y)): #inside circle 1
                if(g(x,y)):#inside circle 2
                    if(f(x,y)):#outside circle 3
                        dotCount=dotCount+1
                        sum=sum+adder
    print("DotCount",dotCount)
    file.write("DotCount="+str(dotCount)+"\n")
    print("Sum=",sum)
    file.write("Sum="+str(sum)+"\n"+"\n")
file.close()
