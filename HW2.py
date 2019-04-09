# -*- coding: utf-8 -*-
"""
Created on Mon Feb 25 10:51:38 2019

@author: Michael
"""

# test_polyfit.py     polyfit in Python, needs numpy
import matplotlib
matplotlib.use('Agg')
from numpy import array
from numpy import polyfit
from numpy import polyval
from numpy import mean
import math
import pylab


from math import log10, floor
def round_sig(x, sig=2):
    return round(x, sig-int(floor(log10(abs(x))))-1)

#def f(x):
#  return 6.0+5.0*x + 4.0*x*x + 3.0*x*x*x + 2.0*x*x*x*x + 1.0*x*x*x*x*x
num=17
g=open("mpw_outputc.txt","a")
g.write("\tN      RMS_Error       Average_Error         Max_Error\n")
#def f(x):
#    return 17.0+16.0*x+15.0*x*x+14.0*x*x*x+13.0*x*x*x*x+12.0*x*x*x*x*x+11.0*x*x*x*x*x*x+10.0*x*x*x*x*x*x*x+9.0*x*x*x*x*x*x*x*x+8.0*x*x*x*x*x*x*x*x*x+7.0*x*x*x*x*x*x*x*x*x*x+6.0*x*x*x*x*x*x*x*x*x*x*x+5.0*x*x*x*x*x*x*x*x*x*x*x*x+4.0*x*x*x*x*x*x*x*x*x*x*x*x*x+3.0*x*x*x*x*x*x*x*x*x*x*x*x*x*x+2.0*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x+1.0*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x*x
  
def f(x):
    return 5.0 + 4.0*x + 3.0*x*x + 2.0*x*x*x + 1.0*x*x*x*x

#def f(x):
#    return 1.0+x+x*x+x*x*x+x*x*x*x

my_list_sumerr = []
my_listb_sumerr = []

my_list_rmserr=[]
my_listb_rmserr=[]

for num in range(3, 18):
    #print ("test_polyfit.py  a x,y,5) ")
    maxError=0
# on known polynomial first
    #print ("fit 5.0 + 4.0*x + 3.0*x*x + 2.0*x*x*x + 1.0*x*x*x*x")

    xx = [0.0 for i in range(num)] #range 5 for 4 powers of x
    yy = [0.0 for i in range(num)] #range 4 for 4 powers of x

    for i in range(num): #range 5 for 4 powers of x
        xx[i] = 0.1*(i+1) #sets x increase of .1
        yy[i] = f(xx[i])  #collects the y values
        #print ("i=")      
        #print (i)
        #print (" ,xx=")
        #print (xx[i])
        #print (" yy=")
        #print (yy[i])

    #print ("p=polyfit(xx,yy,4) for 5 points")
    p=polyfit(xx,yy,num) # 5 input values, 4th order
    #print ("polyfit coefficients")
    #print (p)
    #print ("backwards? largest power first, expected 5, 4, 3, 2, 1")
    #print (" ")
    #print ("polyval values of fit")
    yy1=polyval(p,xx)
    #print (yy1)
    #print ("should be values above")
    #print (" ")
    
    x=array([0.0, 0.1,  0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9]) #time values
    #x=array
    #print ("x=")
    #print  (x)
    y=array([0.0,6.0,15.0,5.0,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,0.0]) #thrust values for each x
    #print ("y=")
    #print (y)
    
    p=polyfit(x,y,num)  #calculates p using x and y
    #print ("polyfit p=polyfit(x,y,5)")
    #print (p)
    
    # polyval
    y1=polyval(p,x)
    #print ("polyval y1=polyval(p,x)")
    #print (y1)
    
    err=abs(y-y1)
    #print ("err=abs(y-y1) an array")
    #print (err)
    
    
    
    sumerr=sum(err)
    print ("Summartion of Errors="+str(sumerr))
    
    
    #"Maximum Error" the largest error in the set.
    #I use "max to collect this. The absolute has already been taken)
    maxError=round_sig(max(err),5)
    
    print ("Max Error"+str(round_sig(maxError,5)))
    
    #"Average Error" the sum of the absolute errors divided by the number in the set.
    # imported numpy.mean to calculate mean of err array
    averageError=round_sig(mean(err),5)
    print("Average Error"+str(round_sig(averageError,5)))
    
    #"RMS Error" the root mean square of the absolute errors.
    #sqrt(sum_over_set(absolute_error^2)/number_in_set)
    rms=0
    #using b to iterate through err array, since x is already being used
    for b in err:
        rms=rms+(b*b) #summation of squares
    
    rmsPrep=rms/num #summation of squares divided by number in set
    rmsFinal=round_sig(math.sqrt(rmsPrep),5) # Final RMS calculation (sqrt())
    print("RMS Error="+str(round_sig(rmsFinal, 5)))
    
    
    #print ("generating plot test_polyfit_py.png")
    pylab.plot(x, y)
    pylab.xlabel("time")
    pylab.ylabel("thrust")
    pylab.grid(True)
    pylab.title("thrust data plotting by python")
    pylab.savefig("test_polyfit_py") # writes .png
    pylab.show()
    
    pylab.plot(x, y1)
    pylab.title("the polyfit")
    pylab.savefig("test_polyfit2_py") # writes .png
    pylab.show()
    my_list_sumerr.append(num)
    my_listb_sumerr.append(sumerr)
    
    my_list_rmserr.append(num)
    my_listb_rmserr.append(rmsFinal)
    g.write('\t')
    g.write('N='+str(num))
    g.write('\t')
    g.write('rmserr  '+str(rmsFinal))
    g.write('Average Error  '+str(averageError))
    g.write('maxerr  '+str(maxError))
    g.write('\n')

pylab.plot(my_listb_sumerr, my_list_sumerr)
pylab.xlabel("num")
pylab.ylabel("sumerr")
pylab.grid(True)
pylab.title("Reduction in SumErr with increasing N")
pylab.savefig("test_polyfit_py") # writes .png
pylab.show()

pylab.plot(my_list_rmserr, my_listb_rmserr)
pylab.xlabel("num")
pylab.ylabel("sumerr")
pylab.grid(True)
pylab.title("Reduction in SumErr with increasing N")
pylab.savefig("test_polyfit_py") # writes .png
pylab.show()
g.close()

