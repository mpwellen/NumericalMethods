#Modeling and simulation: Rocket flight, how high?
#In a language of your choice, write the program defined in
#lecture 2. Test your program and
#produce an output file, then submit both files on a GL machine:

#submit cs455 hw1 your-source your-output

#print output as a text file, do not submit  a.out  that is a binary file.

#The graphics class sees the flight that produces this as a moving flight

f=open("mpw_outputb.txt","a")

#Array containing values for Force of Thrust
arr=[0.0,6.0,15.0,5.0,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,4.21794,0.0]

#ship variables
length=0.311
diameter=0.0241
area=.000506
cd=.45
finsArea=0.00496
finsCd=0.01
massEmpty=0.0340
massInitial=0.0242
massFinal=0.0094
Rho=1.293

#thrust curve
impulseTotal=8.82
thrustPeak=14.09
thrustAverage=4.74
timeBurn=1.86

#initial conditions
t=0
s=0
v=0
a=0
F=0
m=0.0340+0.0242
A_main=.000506
A_fin=.00496
g=9.80665
dt=0.1

while(v>=0):
    t=t+.1  #adjust time
    if(t<=1.9): #set thrust
        counter=int(t*10)
        Ft=arr[counter]
    else:
        Ft=0
    Fd_main=cd*Rho*A_main*v*v*.5 #F_Drag
    Fd_fin=finsCd*Rho*A_fin*v*v*.5 #F_Drag
    m=m-(0.0001644*Ft) #calcuate new mass
    Fg=g*m #F_gravity
    Fnet=Ft-Fg-Fd_main-Fd_fin #calcuate net force
    a=Fnet/m #Calculate Acceleration
    v=v+a*dt #calculate velocity
    s=s+v*dt  #calculate height
    #print results
    print('Time',t)
    print('Height',s)
    print('Velocity',v)
    print('Acceleration',a)
    print('Force',Fnet)
    print('Mass',m)
    print()
    f.write('\t')
    f.write('Time  '+str(t))
    f.write('Height  '+str(s))
    f.write('Velocity  '+str(v))
    f.write('Acceleration  '+str(a))
    f.write('Force  '+str(Fnet))
    f.write("Mass  "+str(m))
    f.write('\n')
f.close()

