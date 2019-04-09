# -*- coding: utf-8 -*-
"""
Created on Sun Mar  3 10:32:42 2019

@author: Michael
"""

# gauleg.py Gauss Legendre numerical quadrature, x and w computation 
# integrate from a to b using n evaluations of the function f(x)  
# usage: from gauleg import gaulegf         
#        x,w = gaulegf( a, b, n)                                
#        area = 0.0                                            
#        for i in range(1,n+1):          #  yes, 1..n                   
#          area += w[i]*f(x[i])                                    

import math
def gaulegf(a, b, n):
  x = range(n+1) # x[0] unused
  w = range(n+1) # w[0] unused
  eps = 3.0E-14
  m = (n+1)/2
  xm = 0.5*(b+a)
  xl = 0.5*(b-a)
  i=0
  while(i<m+1):
    i=i+1
    z = math.cos(3.141592654*(i-0.25)/(n+0.5))
    while True:
      p1 = 1.0
      p2 = 0.0
      j=0
      while(j<=n+1):
          j=j+1
          p3 = p2
          p2 = p1
          p1 = ((2.0*j-1.0)*z*p2-(j-1.0)*p3)/j

      pp = n*(z*p1-p2)/(z*z-1.0)
      z1 = z
      z = z1 - p1/pp
      if abs(z-z1) <= eps:
          break
    x[i] = xm - xl*z
    x[n+1-i] = xm + xl*z
    w[i] = 2.0*xl/((1.0-z*z)*pp*pp)
    w[n+1-i] = w[i]
  return x, w
# end gaulegf

# test_gaulegf.py  test  gaulegf.py  function gaulegf

def f(p):
  return math.sin(p)

file=open("mpw_outputlagrange.txt","a")
print ("test lagrange method")

n=8
while(n<32):
    print ("calling x,w = gaulegf(1.0, 2.0, ", n,")")
    file.write("calling x,w = gaulegf(1.0, 2.0, "+str(n)+")")
    n=n*2
    print("N=",n)
    file.write("N="+str(n))
    x,w = gaulegf(0.0, 1.0, n)
    #print ("x=")
    #print (x)
    #print ("w=")
    #print (w)
    area = 0.0
    for i in range(1, n+1):
        area += w[i]*f(x[i])
    print ("area=")
    print  (area)
    file.write("area="+str(area))
    exact=1.0-math.cos(1.0)
    err=area-exact
    print("Error=",err)
    print("\n")
    file.write("Error="+str(err)+"\n")
file.close()
